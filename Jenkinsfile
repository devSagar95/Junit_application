pipeline {
    agent any

    environment {
        DB_USER = 'root'
        DB_PASS = 'Sagar@95' //MY_SQL_DB_sagar
    }

        stages {
                stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/devSagar95/Junit_application.git'
            }
        }

        stage('Build and Test') {
            tools {
                maven 'Maven_3.8.8' // the same name you gave in Global Tool Config
            }

            steps {
                echo 'Running Maven build and unit tests...'
                sh 'mvn clean test'
            }
        }

        stage('Insert Test Results to Database') {
           tools {
                maven 'Maven_3.8.8' // the same name you gave in Global Tool Config
            }
          
            steps {
                echo 'Uploading test results into MySQL database...'
                sh 'mvn compile exec:java -Dexec.mainClass="com.example.TestResultUploader"'
            }
        }

        stage('Health Report') {
            steps {
                echo 'Check Grafana Dashboard for real-time unit test health!'
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution finished.'
        }
    }
}
