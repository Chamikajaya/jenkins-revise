def incrementVersion() {

    echo 'Incrementing version'

    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit'
    // versions:commit will commit the changes to the pom.xml so that we can read the updated version in the next step
    // ! Note that the version will be updated in the pom.xml file and it will not be commited automatically to the git repository, rather the changes will be local to the Jenkins workspace. So we need to do a version bump 😊

    def matcher = readFile('pom.xml') =~ /<artifactId>demo<\/artifactId>\s*<version>(.+)<\/version>/

    def latestVersion = matcher[0][1]  // get the first match

    env.IMAGE_NAME = "$latestVersion-$BUILD_NUMBER"
    // Returning the updated version with the current Jenkins build number

}


def buildJar() {

    echo "Building the jar"

    sh "mvn clean package"

}


def buildDockerImage() {

    echo "Building Docker Image"

    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {

        sh "docker build -t chamikajay/jenkins-java-simple-app:$IMAGE_NAME ."
        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
        sh "docker push chamikajay/jenkins-java-simple-app:$IMAGE_NAME"

    }
}


def deployApp() {

    echo "Deploying app"

}


def versionUpdate() {

    echo "Committing the version update"

    sshagent(['jenkins-github-ssh']) {

        sh 'git config --global user.email jenkins-ec2@jenkins.com'
        sh 'git config  --global user.name jenkins-ec2'


        sh 'git status'
        sh 'git branch'
        sh 'git config --list'

        sh 'git remote set-url origin git@github.com:Chamikajaya/jenkins-revise.git'

        sh 'git add .'
        sh 'git commit -m "version update from Jenkins"'
        sh 'git push origin HEAD:main'

    }
}

return this