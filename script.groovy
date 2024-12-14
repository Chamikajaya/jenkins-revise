def buildJar() {
    echo "Building app"
//    sh "mvn package"
}

def buildDockerImage() {
    echo "Building Docker Image"
//    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
//        sh 'docker build -t chamikajay/jenkins-java-simple-app:1.0.0 .'
//        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
//        sh 'docker push chamikajay/jenkins-java-simple-app:1.0.0'
//    }
}

def deployApp() {
    echo "Deploying app"
}

return this