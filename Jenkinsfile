pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
    stages{
        stage('Checkout') {
            steps {
                git 'https://github.com/vyjorg/LPDM-User'
            }
        }
        stage('Tests') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
                failure {
                    error 'The tests failed'
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh "docker stop LPDM-UserMS || true && docker rm LPDM-UserMS || true"
                sh "docker pull vyjorg/lpdm-user:latest"
                sh "docker run -d --name LPDM-UserMS -p 28083:28083 --restart always --memory-swappiness=0 vyjorg/lpdm-user:latest"
            }
        }
    }
}