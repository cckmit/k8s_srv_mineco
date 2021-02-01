node {
    //BEGIN Project dependent vars
    def projectName = 'commons-project'
    echo 'projectName = ' + projectName

	def branchName = 'refs/heads/develop'
    echo 'branchName = ' + branchName
	
    def gitRepoName = projectName + '.git'
    echo 'gitRepoName = ' + gitRepoName

    //BEGIN System tools
    env.JAVA_HOME = tool name: 'JDK_8_Default', type: 'hudson.model.JDK'
    echo 'JAVA_HOME = ' + env.JAVA_HOME

    def mvnHome = tool name: 'Default', type: 'hudson.tasks.Maven$MavenInstallation'
    echo 'mvnHome = ' + mvnHome

    def sonar = tool name: 'SonarQube 2.6.1', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    echo 'sonar = ' + sonar
    //END System tools
    
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

    //END Internal vars


    try{
        stage ('build') {
            checkout([$class: 'GitSCM', branches: [[name: branchName]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '29d0ef2e-2449-474a-9b2c-cbe9d5c92e6f', url: eGoverisProjectGitUrl ]]])
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
            //first of all we are going to deploy the jars on nexus to ensure the consistency with all the modules and projects
                sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' deploy -U -Dmaven.test.skip=true'
                //sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' tomcat7:deploy-only'
        }

    } catch (any) {
        currentBuild.result = 'FAILURE'
        throw any //rethrow exception to prevent the build from proceeding
    } finally {
        step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'guillermo.gefaell.valcarce@everis.com angel.pedro.perez.izquierdo@everis.com', sendToIndividuals: true])
    }
}