pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Clone your repository or perform any necessary setup steps

                // Build the Docker image
                sh 'docker build -t tanhaoke/estore-product:latest .'
            }
        }

        stage('Push') {
            steps {
                // Log in to Docker Hub
                sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                

                // Push the Docker image to Docker Hub
                sh 'docker push tanhaoke/estore-product:latest'
            }
        }
    }
}