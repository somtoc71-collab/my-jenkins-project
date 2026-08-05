def gv

pipeline {
    agent any

    environment {
        DOCKER_USER      = 'bamzy14'
        IMAGE_NAME       = 'my-repo'
        IMAGE_TAG        = "${BUILD_NUMBER}"
        DOCKER_HUB_CRED  = 'docker-hub-credential' 
        CONTAINER_NAME   = 'my-running-node-app'
    }
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
                    gv.incrementVersion()
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
                     def dockercmd = 'docker run -d -p 3000:3000 bamzy14/my-repo:latest'
                    sshagent(['ec2-server-key']) {
                        sh "ssh -o strictHostkeychecking=no ec2-user@3.92.27.179 ${dockercmd}"
                    }
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
