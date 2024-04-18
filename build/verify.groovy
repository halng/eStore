pipeline {
    agent any
    tools { 
      maven 'maven' 
    }
    stages {
        stage('Pull Code') {
            steps {
                // git branch: 'PR_BRANCH_NAME', c, url: 'https://github.com/your/repository.git'
                git branch: 'main',credentialsId: 'YOUR_CREDENTIALS_ID', url: 'https://github.com/tanhaok/eStore.git'
            }
        }
        
        stage('Verify') {
            steps {
                script {
                    def folders = ['product', 'auth', 'api', 'email', 'search']
                    for (def folder in folders) {
                        echo "Verifying ${folder}..."
                        sh "mvn clean -f ${folder}"
                        sh "mvn verify -f ${folder}"
                    }
                }
            }
        }
    }
}