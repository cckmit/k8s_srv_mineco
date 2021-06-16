node {
    //BEGIN Project dependent vars
    def projectName = 'dashboard-web'
    echo 'projectName = ' + projectName

    def gitRepoName = projectName + '.git'
    echo 'gitRepoName = ' + gitRepoName
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
    def eGoverisServerDesarrolloIP = '7.214.8.27'
    echo 'eGoverisServerDesarrolloIP = ' + eGoverisServerDesarrolloIP

    def identity_file = env.JENKINS_RESOURCES + '/eGoveris/security/identities/desa.egoveris.com.private'
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
    
    def pluginsDeployPath = '/opt/versions/PluginManager/ee/deploy'
    echo 'pluginsDeployPath = ' + pluginsDeployPath
    //END Internal vars


    try{
        stage ('build') {
            checkout([$class: 'GitSCM', branches: [[name: 'refs/heads/develop']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '29d0ef2e-2449-474a-9b2c-cbe9d5c92e6f', url: eGoverisProjectGitUrl ]]])
            //gitlabCommitStatus('build') {
                sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' clean install -U -DskipTests=true'
            //}
        }
    
        stage ('sonar') {
            //gitlabCommitStatus('sonar') {
                sh sonar + '/bin/sonar-scanner -Dsonar.host.url=' + sonarHostUrl + ' -Dproject.settings=' + pathToSonarProperties 
            //}
        }

        stage ('deploy') {
            //gitlabCommitStatus('deploy') {
                echo 'deploying previously generated jars to nexus'
                sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' deploy -DskipTests=true'
            //}
        }

    } catch (any) {
        currentBuild.result = 'FAILURE'
        throw any //rethrow exception to prevent the build from proceeding
    } finally {
        step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'guillermo.gefaell.valcarce@everis.com angel.pedro.perez.izquierdo@everis.com', sendToIndividuals: true])
    }
}