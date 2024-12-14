def gv

pipeline {

    agent any

    tools {
        maven 'mvn 3.9.9'
    }

     parameters {
            string(name: 'NAME', defaultValue: 'Chamika Jayasinghe', description: 'Executor name')
            booleanParam(name: 'Should_Run_Tests', defaultValue: true, description: 'Should run tests')
     }

     stages {

        stage("INIT") {
            steps {

                //  Inside the script block we can write any groovy code
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
                    gv.buildApp()
                }
            }
        }

        stage("TEST") {

            when {
                expression {
                    params.Should_Run_Tests
                }
            }

            // Only run this stage if the above condition is true
            steps {
                script {
                    gv.testApp()
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



}