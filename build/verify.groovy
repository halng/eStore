pipeline {
    agent {
        label 'agent'
    }
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
                        echo "\n\n\nVerifying ${folder}...\n\n\n"
                        sh "mvn clean -f ${folder}"
                        sh "mvn verify -f ${folder}"
                    }
                }
            }
        }
        stage('Verify - Frontend Components') {
            steps {
                script {
                    def folders = ['management']
                    for (def folder in folders) {
                        echo "\n\n\nVerifying ${folder}...\n\n\n"
                        sh "npm install --prefix ${folder}"
                        sh "npm run lint  --prefix ${folder} && npm run format --prefix ${folder}"
                        echo "Running tests..."
                        sh "npm run test:coverage --prefix ${folder}"
                        sh "npm run e2e:headless --prefix ${folder}"
                        echo "Building..."
                        sh "npm run build --prefix ${folder}"
                    }
                }
            }
        }
    }
}