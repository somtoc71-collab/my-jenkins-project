pipeline {
    agent any

    environment {
        DOCKER_USER      = 'bamzy14'
        IMAGE_NAME       = 'my-repo'
        IMAGE_TAG        = "${BUILD_NUMBER}"
        DOCKER_HUB_CRED  = 'docker-hub-credential' 
        CONTAINER_NAME   = 'my-running-node-app'
    }
 def gv
    stages {
        stage('initialize') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }    
        stage('increment version') {
            steps {
                script {
                    gv.incremrentVersion()
                }
            }
        }
        stage('Checkout') {
            steps {
                script {
                   gv.checkout()
                }
            
            }
        }

        stage('Build') {
            steps {
                script {
                    gv.build()
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    gv.test()
                }
            }
        }

        stage('push & deploy') {
            steps {
                script {
                    gv.pushAndDeploy()
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check build logs for details.'
        }
        always {
            echo 'Cleaning up dangling images...'
            sh 'docker image prune -f || true'
        }
    }
}
