pipeline {
    agent any
    tools { 
      maven 'maven' 
      nodejs "nodejs"
    }
    stages {
        stage('Pull Code') {
            steps {
                // git branch: 'PR_BRANCH_NAME', c, url: 'https://github.com/your/repository.git'
                git branch: 'main',credentialsId: 'YOUR_CREDENTIALS_ID', url: 'https://github.com/tanhaok/eStore.git'
            }
        }
        
        stage('Verify - Java Base Components') {
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
        stage('Verify - Frontend Components') {
            steps {
                script {
                    def folders = ['managements']
                    for (def folder in folders) {
                        echo "Verifying ${folder}..."
                        sh "npm install --prefix ${folder}"
                        sh "npm run lint && npm run format --prefix ${folder}"
                    }
                }
            }
        }
    }
}