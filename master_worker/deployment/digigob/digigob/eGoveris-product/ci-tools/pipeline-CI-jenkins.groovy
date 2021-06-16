node {
    //BEGIN Project dependent vars
    def projectName = 'eGoveris-product'
    echo 'projectName = ' + projectName

    def gitRepoName = projectName + '.git'
    echo 'gitRepoName = ' + gitRepoName
    //END Project dependent vars

    //BEGIN System tools
    env.JAVA_HOME = tool name: 'Default', type: 'jdk'
    echo 'JAVA_HOME = ' + env.JAVA_HOME

    def mvnHome = tool name: 'Default', type: 'hudson.tasks.Maven$MavenInstallation'
    echo 'mvnHome = ' + mvnHome
    //END System tools

    //BEGIN Internal vars
    def pathToeGoverisMavenSettings = './ci-tools/maven-settings.xml'
    echo 'pathToeGoverisMavenSettings = ' + pathToeGoverisMavenSettings

    def eGoverisGitBaseUrl = 'http://7.214.8.38/egoveris/'
    echo 'eGoverisGitBaseUrl = ' + eGoverisGitBaseUrl
    
    def eGoverisProjectGitUrl = eGoverisGitBaseUrl + gitRepoName
    echo 'eGoverisProjectGitUrl = ' + eGoverisProjectGitUrl
    //END Internal vars


    try {
        stage ('master-config') {
            checkout([$class: 'GitSCM', branches: [[name: 'refs/heads/develop']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '29d0ef2e-2449-474a-9b2c-cbe9d5c92e6f', url: eGoverisProjectGitUrl ]]])
           // gitlabCommitStatus('master-config') {
                sh mvnHome+'/bin/mvn -s ' + pathToeGoverisMavenSettings + ' clean deploy -U -Dmaven.test.skip=true -f pom-master-config.xml'
           // }
        }
    } catch (any) {
        currentBuild.result = 'FAILURE'
        throw any //rethrow exception to prevent the build from proceeding
    } finally {
        step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'guillermo.gefaell.valcarce@everis.com angel.pedro.perez.izquierdo@everis.com', sendToIndividuals: true])
    }
}