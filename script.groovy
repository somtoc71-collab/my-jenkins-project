def checkout() {
                echo 'Pulling code from Git repository...'
            }
        }
def build() {
      echo 'Building Docker image...'
                sh "docker build -t ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG} -t ${DOCKER_USER}/${IMAGE_NAME}:latest ."
}
def test() {
    echo 'Testing the application...'
    sh "docker run --rm ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG} npm test"
}
def pushAndDeploy() {
    echo 'deploying the application...'
    withCredentials([usernamePassword(credentialsId: "${docker_hub_credential}", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh "echo $PASSWORD " | docker login -u $USERNAME --password-stdin"
        sh "docker push ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}: latest"
    
    }
}

return this