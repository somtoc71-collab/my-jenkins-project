def incrementVersion() {
    echo 'incrementing app version..'
    sh 'npm version patch'
}
def checkout() {
                echo 'Pulling code from Git repository...'
            }
def build() {
    sh 'npm version patch --no-git-tag-version --no-commit-hooks'
    def version = sh (script: 'node -p \"require(\'./package.json\').version\"', returnStatus: true).trim()
    env.IMAGE_TAG = version
    echo "New version: ${env.IMAGE_TAG}"
      echo 'Building Docker image...'
                sh "docker build -t ${env.DOCKER_USER}/${env.IMAGE_NAME}:${env.IMAGE_TAG} -t ${env.DOCKER_USER}/${env.IMAGE_NAME}:latest ."
                sh 'npm install'
}
def test() {
    echo 'Testing the application...'
    sh "docker run --rm ${env.DOCKER_USER}/${env.IMAGE_NAME}:${env.IMAGE_TAG} npm test"
}
   def pushAndDeploy() {
    echo 'deploying the application...'
    withCredentials([usernamePassword(credentialsId: "${env.DOCKER_HUB_CRED}", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh "echo \$PASSWORD | docker login -u \$USERNAME --password-stdin"
        sh "docker push ${env.DOCKER_USER}/${env.IMAGE_NAME}:${env.IMAGE_TAG}"
        sh "docker push ${env.DOCKER_USER}/${env.IMAGE_NAME}:latest"
    }
} //

return this