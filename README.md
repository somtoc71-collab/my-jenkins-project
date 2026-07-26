
# 🚀 Exercise 1: CI/CD Pipeline for my Node.js Application
## 📌 Project Overview
, I set up a fully automated Continuous Integration and Continuous Deployment (CI/CD) pipeline using Jenkins, Docker, and **GitHub for a Node.js web application.
The pipeline automates the entire lifecycle from pulling the source code to running tests and deploying the application locally inside a Docker container.
## 🛠️ Architecture & Pipeline Stages
 1. Checkout SCM:Pulls the latest application code from the GitHub master branch.
 2. **Build Stage:** Creates a  Docker image containing the Node.js runtime and dependencies with dockerfile
 3. Test Stage:Spawns a temporary container to execute npm test and ensure code quality.
 4. **Deploy Stage:** Stops any previously running container, cleans it up, and launches the updated container on port 3000.

 1. Repository Configuration (VS Code)
>Proof of the Jenkinsfilein vs code, package.json, and package-lock.json configured 



Jenkinsfile containing dynamic Docker user and image tags.*

Valid package.json with cleaned test scripts alongside package-lock.json.*
2. Successful Pipeline Execution
> *Showcasing all Jenkins stages passing successfully (Checkout, Build, Test, Deploy).*
> 
### 3. Container running on Docker
> *Showcasing the app deployed and running inside the container.*
> 
## 🛠️ Errors Encountered & Troubleshooting Log
During the setup, I encountered several real-world DevOps errors and resolved them systematically:
### 1. Missing package-lock.json (npm ci Failure)
 * **Error:** Docker build failed at RUN npm ci --only=production.
 * **Root Cause:** npm ci strictly requires a pre-existing package-lock.json file.
 * **Resolution:** Replaced RUN npm ci with RUN npm install inside the Dockerfile to handle builds dynamically.
### 2. Broken JSON Syntax in package.json (EJSONPARSE)
 * **Error:** npm install threw JSONParseError: Bad control character.
 * **Root Cause:** Manual editing in Vim introduced escaped quotes (\") and hidden newline control characters.
 * **Resolution:** Rebuilt package.json with a clean structure and verified it locally using npm install before pushing to GitHub.
### 3. Missing Docker Repository Name (pull access denied)
 * **Error:** Stages failed with Unable to find image 'bamzy14:X' locally.
 * **Root Cause:** The image tag in Jenkinsfile was missing the repository name (bamzy14:${BUILD_NUMBER} instead of bamzy14/my-node-app:${BUILD_NUMBER}).
 * **Resolution:** Corrected the dynamic image variable across **Build**, **Test**, and **Deploy** stages in Jenkinsfile to ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}.
### 4. Shell Syntax Error in NPM Test Script
 * **Error:** Test stage threw sh: syntax error: unterminated quoted string.
 * **Root Cause:** The "test" script inside package.json contained malformed escaped quotes ("echo \"Running test... Success!\"").
 * **Resolution:** Simplified the NPM script to "test": "echo Running test... Success!".
### 5. Git File Tracking Case-Sensitivity Issue
 * **Error:** Jenkins continued running outdated pipeline steps after local updates.
 * **Root Cause:** Running git add jenkinsfile (lowercase j) failed to track changes due to Git's case sensitivity on Linux systems (Jenkinsfile).
 * **Resolution:** Used exact casing (git add Jenkinsfile), committed, and pushed to origin master.
## 🎯 Key Achievements
 * Handled containerized application lifecycles using Docker commands (build, run, stop, rm).
 * Configured dynamic variables in Jenkins declarative pipelines.
 * Troubleshot JSON, Bash shell, and Git tracking issues in a CI/CD environment.
