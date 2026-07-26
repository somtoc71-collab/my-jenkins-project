def checkout() {
    echo 'Pulling code from Git repository...'
    checkout scm
}

def build() {
    echo 'Building Docker image...'
    sh "docker build -t ${env.DOCKER_USER}/${env.IMAGE_NAME}:${env.IMAGE_TAG} -t ${env.DOCKER_USER}/${env.IMAGE_NAME}:latest ."
}

def test() {
    echo 'Testing the application...'
    sh "docker run --rm ${env.DOCKER_USER}/${env.IMAGE_NAME}:${env.IMAGE_TAG} npm test"
}

def pushAndDeploy() {
    echo 'Pushing to Docker Hub and deploying application...'
}
    
    
    withCredentials([usernamePassword(credentialsId: "${env.DOCKER_HUB_CRED}", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh "echo \$PASSWORD | docker login -u \$USERNAME --password-stdin"
        sh "docker push ${env.DOCKER_USER}/${env.IMAGE_NAME}:${env.IMAGE_TAG}"
        sh "docker push ${env.DOCKER_USER}/${env.IMAGE_NAME}:latest"
    }
    
    
    sh "docker stop ${env.CONTAINER_NAME} || true"
    sh "docker rm ${env.CONTAINER_NAME} || true"
    sh "docker run -d --name ${env.CONTAINER_NAME} -p 3000:3000 ${env.DOCKER_USER}/${env.IMAGE_NAME}:${env.IMAGE_TAG}"
}

return this
