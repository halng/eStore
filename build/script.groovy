pipeline {
    agent any
    
    stages {
        stage('Pull Code') {
            steps {
                // Clone the repository
                git branch: 'PR_BRANCH_NAME', credentialsId: 'YOUR_CREDENTIALS_ID', url: 'https://github.com/your/repository.git'
                
                // Checkout the PR branch
                sh 'git checkout PR_BRANCH_NAME'
                
                // Pull the latest changes
                sh 'git pull origin PR_BRANCH_NAME'
            }
        }
        
        stage('Build Code') {
            steps {
                // Add your build steps here
                // For example, you can use Maven or Gradle to build your code
                sh 'cd product'
                sh 'mvn clean install'
                sh 'mvn verify'
            }
        }
    }
}