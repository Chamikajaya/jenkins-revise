def incrementVersion() {

    echo 'Incrementing version'

    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit'
    // versions:commit will commit the changes to the pom.xml so that we can read the updated version in the next step

    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'  // Reading the updated version from the pom.xml

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

return this