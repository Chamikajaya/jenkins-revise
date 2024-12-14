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

                //  Inside the script block we can write any groovy code
                script {

                    echo "Pipeline initiated by ${params.NAME}"

                    sh 'mvn -version'

                    gv = load "script.groovy"

                }

            }

        }

     }



}