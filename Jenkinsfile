def gv

pipeline {
    agent any

    tools {
        maven 'mvn 3.9.9'
    }

    parameters {
        string(name: 'NAME', defaultValue: 'Chamika Jayasinghe', description: 'Executor name')
    }

    stages {
        stage("INIT") {
            steps {
                script {
                    echo "Pipeline initiated by ${params.NAME}"
                    sh 'mvn -version'
                    gv = load "script.groovy"
                }
            }
        }

        stage("BUILD") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

        stage("DOCKER_IMAGE_BUILD") {
            steps {
                script {
                    gv.buildDockerImage()
                }
            }
        }

        stage("DEPLOY") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished execution"
        }
        success {
            echo "Pipeline executed successfully"
        }
        failure {
            echo "Pipeline executed with failure"
        }
    }
}