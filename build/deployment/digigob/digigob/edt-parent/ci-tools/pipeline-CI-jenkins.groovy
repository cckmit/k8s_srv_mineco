node {
    //BEGIN Project dependent vars
    def projectName = 'edt'
    echo 'projectName = ' + projectName
    
    def branchName = 'refs/heads/develop'
    echo 'branchName = ' + branchName
    def gitRepoName = projectName + '-parent' + '.git'
    echo 'gitRepoName = ' + gitRepoName
    
    def deploymentProjectName = 'edt-web'
    echo 'deploymentProjectName = ' + deploymentProjectName
    
    def serverName = 'server2'
    echo 'serverName = ' + serverName
    
    def serverPath = '/egoveris/' + serverName
    echo 'serverPath = ' + serverPath
    //END Project dependent vars
    //BEGIN System tools
    env.JAVA_HOME = tool name: 'Default', type: 'jdk'
    echo 'JAVA_HOME = ' + env.JAVA_HOME
    def mvnHome = tool name: 'Default', type: 'hudson.tasks.Maven$MavenInstallation'
    echo 'mvnHome = ' + mvnHome
    def sonar = tool name: 'SonarQube 2.6.1', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    echo 'sonar = ' + sonar
    //END System tools
    //BEGIN Internal vars
    def eGoverisServerDesarrolloDNS = 'desa.egoveris.com'
    echo 'eGoverisServerDesarrolloDNS = ' + eGoverisServerDesarrolloDNS
    def identity_file = './ci-tools/' + eGoverisServerDesarrolloDNS + '.private'
    echo 'identity_file = ' + identity_file
    
    def pathToeGoverisMavenSettings = './ci-tools/maven-settings.xml'
    echo 'pathToeGoverisMavenSettings = ' + pathToeGoverisMavenSettings
    def eGoverisGitBaseUrl = 'http://7.214.8.38/egoveris/'
    echo 'eGoverisGitBaseUrl = ' + eGoverisGitBaseUrl
    
    def eGoverisProjectGitUrl = eGoverisGitBaseUrl + gitRepoName
    echo 'eGoverisProjectGitUrl = ' + eGoverisProjectGitUrl
    
    def pathToSonarProperties = './ci-tools/sonar-project-eGov-' + projectName + '.properties'
    echo 'pathToSonarProperties = ' + pathToSonarProperties
    
    def sonarHostUrl = "http://7.214.8.38:9000"
    echo 'sonarHostUrl = ' + sonarHostUrl
    def serverWebApps = serverPath + '/webapps'
    echo 'serverWebApps = ' + serverWebApps
    def warToDeployExtractionDir = serverWebApps + '/' + deploymentProjectName
    echo 'warToDeployExtractionDir = ' + warToDeployExtractionDir
    def warToDeployWithFullPath = warToDeployExtractionDir + '.war'
    echo 'warToDeployWithFullPath = ' + warToDeployWithFullPath
    def serverBinPath = serverPath + '/bin'
    echo 'serverBinPath = ' + serverBinPath
    //END Internal vars
    try {
        stage ('build') {
            checkout([$class: 'GitSCM', branches: [[name: branchName]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '29d0ef2e-2449-474a-9b2c-cbe9d5c92e6f', url: eGoverisProjectGitUrl ]]])
                sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' clean install -U -DskipTests=true'
        }
        stage ('sonar') {
                sh sonar + '/bin/sonar-scanner -Dsonar.host.url=' + sonarHostUrl + ' -Dproject.settings=' + pathToSonarProperties 
        }
        stage ( 'deploy' ) {
                //first of all we are going to deploy the jars on nexus to ensure the consistency with all the modules and projects
                sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' deploy -U -Dmaven.test.skip=true'
                //sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' tomcat7:deploy-only'
                sh 'chmod 600 ' + identity_file
                echo 'removing old ' + deploymentProjectName + '.war from the server ...'
                sh 'ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' root@' + eGoverisServerDesarrolloDNS + ' rm -f ' + warToDeployWithFullPath
                echo 'old '+ deploymentProjectName + '.war removed. Cleaning deployment folder now ... '
                sh 'ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' root@' + eGoverisServerDesarrolloDNS + ' rm -rf ' + warToDeployExtractionDir
                echo 'deployment folder cleaned, copying new ' + deploymentProjectName + '.war to development server ...'
                sh 'scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' ./' + projectName + '-webapp/target/' + deploymentProjectName + '.war root@' + eGoverisServerDesarrolloDNS + ':' + serverWebApps
                echo 'new ' + deploymentProjectName + '.war deployed successfuly.'
        }
        stage ( 'restart' ) {
                sh 'ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' root@' + eGoverisServerDesarrolloDNS + ' ' + serverBinPath + '/shutdown.sh'
                //We wait 5 seconds for the tomcat to stop in a controlled way
                sleep 5
                try{
                    //sh 'ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' root@desa.egoveris.com "kill $(ps -fea | grep java | grep ' + serverName + ' | awk ' + "'{print \$2}'" + ')"'    
                    sh 'scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' ./ci-tools/kill-server.sh root@' + eGoverisServerDesarrolloDNS + ':' + serverBinPath
                    sh 'ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' root@' + eGoverisServerDesarrolloDNS + ' chmod 700 ' + serverBinPath + '/kill-server.sh'
                    sh 'ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -i ' + identity_file + ' root@' + eGoverisServerDesarrolloDNS + ' ' + serverBinPath + '/kill-server.sh ' + serverName
                } catch ( any ) {
                    //The kill command will fail if no pid is found, so we are catching the exception thrown because this command should be launched only if the shutdown fails.
                    echo 'kill command failed probably there was no process runing. EXECUTION continues ...'
                } finally {
                    sh 'ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no  -i ' + identity_file + ' root@' + eGoverisServerDesarrolloDNS + ' ' + serverBinPath + '/startup.sh'
                    //We will wait for the tomcat to start after releasing the control to the next step
                    sleep 35
                }
        }
    } catch (any) {
            currentBuild.result = 'FAILURE'
            throw any //rethrow exception to prevent the build from proceeding
    } finally {
        step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'guillermo.gefaell.valcarce@everis.com angel.pedro.perez.izquierdo@everis.com', sendToIndividuals: true])
    }
    
}
@NonCPS
def url_is_up( deploymentProjectName ) {
    def tomcat_development_dns = '7.214.8.19'
    echo 'tomcat_development_dns -> ' + tomcat_development_dns
    def tomcat_port = '9000'
    echo 'tomcat_port -> ' + tomcat_port
    def url_tomcat_development = 'http://' + tomcat_development_dns + ':' + tomcat_port + '/' + deploymentProjectName
    echo 'url_tomcat_development -> ' + url_tomcat_development
    
    def http_result = ["curl", "-s", "-o", "/dev/null", "-w", "%{http_code}", url_tomcat_development].execute().text
    echo 'http_result = ' + http_result
    if ( http_result == '302' || http_result == '200' ) {
        echo 'Application ' + deploymentProjectName + ' is up and running' 
    } else {
        def errorMessage = 'Application ' + deploymentProjectName + ' is not responding or server is down.'
        echo errorMessage
        throw new Exception( errorMessage ) 
    } 
}