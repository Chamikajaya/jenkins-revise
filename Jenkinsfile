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

                    // Load the script.groovy file
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

            // getting user inputs
            input {
                message "Which env to deploy?"
                ok "Deploy"
                parameters {
                    choice(name: 'ENV_1', choices: ['DEV', 'QA', 'PROD'], description: 'Environment to deploy')
//                     choice(name: 'ENV_2', choices: ['DEV', 'QA', 'PROD'], description: 'Environment to deploy')
                             }
            }

            steps {
                script {
                    gv.deployApp()
                    sh "echo Deploying to ${ENV_1}"  // here no need to use params.choiceName since the params are not global
//                     sh "echo Deploying to ${ENV_2}"
                }
            }
        }

     }



}