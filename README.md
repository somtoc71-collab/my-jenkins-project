Here is a clean, comprehensive **README.md** project summary designed specifically for your GitHub repository to show recruiters you know how to build, debug, and scale enterprise CI/CD workflows.
# 🚀 Automated Node.js CI/CD Pipeline (Jenkins, Shared Groovy Scripts & Docker)
## 📌 Executive Summary
This project demonstrates an end-to-end, production-ready Continuous Integration and Continuous Deployment (CI/CD) pipeline for a Node.js application. Built using **Jenkins**, **Docker**, and **Groovy**, the architecture focuses on **modular pipeline design**, **secure credential management**, and **automated container lifecycle management**.
Instead of writing monolithic, messy pipeline scripts, this project separates orchestration from execution logic using a modular Groovy script (script.groovy), adhering to DevOps DRY (Don't Repeat Yourself) best practices.
## 🛠️ Tech Stack & Key Technologies
 * **Orchestration Engine:** Jenkins (Declarative Pipeline)
 * **Scripting & Logic:** Groovy (Modular execution script)
 * **Containerization:** Docker Engine & Docker CLI
 * **Artifact Registry:** Docker Hub
 * **Version Control:** Git & GitHub
## 🏗️ Architecture & Workflow Overview
```text
[ GitHub Repo ] ➔ [ Jenkins Pipeline ] ➔ [ Load script.groovy ]
                                                │
    ┌───────────────────────────────────────────┴───────────────────────────────────────────┐
    ▼                                           ▼                                           ▼
[ Checkout Code ] ➔ [ Docker Build & Tag ] ➔ [ Automated Container Testing ] ➔ [ Push to Docker Hub & Local Deploy ]

```
### Key Stages Explained
 1. **Initialize Stage:** Loads the external script.groovy helper into pipeline-wide memory without polluting global variable scopes.
 2. **Checkout Stage:** Clones the targeted commit/branch from GitHub into the Jenkins workspace.
 3. **Build Stage:** Builds a dual-tagged Docker image using the unique ${BUILD_NUMBER} for immutability and latest for release management.
 4. **Test Stage:** Executes automated testing (npm test) inside an isolated, temporary container to ensure build stability without host pollution.
 5. **Push & Deploy Stage:**
   * Securely logs into Docker Hub using encrypted Jenkins credentials.
   * Uploads both image tags to the remote repository.
   * Gracefully stops existing containers, cleans up resources, and deploys the new release on port 3000.
## 🔧 Engineering Challenges & Solutions
Recruiters and hiring managers value real-world problem-solving. Here is how key pipeline issues were diagnosed and resolved during development:
### 1. Declarative Pipeline Variable Scoping
 * **Issue:** Declaring def gv outside the pipeline {} block resulted in Jenkins syntax parse failures (Not a valid section definition).
 * **Solution:** Moved initialization inside the first script {} block using implicit global binding (gv = load 'script.groovy'), making helper functions accessible across all stages without breaking Declarative pipeline rules.
### 2. Modular Groovy Refactoring & CPS Compilation
 * **Issue:** Encountered CpsCompilationErrorsException due to misplaced closing braces and detached credential scopes.
 * **Solution:** Refactored script.groovy to correctly encapsulate step blocks, strictly wrapped Docker Hub authentication inside withCredentials, explicitly passed environment variables using env.VARIABLE, and ensured proper return instance binding via return this.
### 3. Container Lifecycle Hygiene
 * **Issue:** Re-deploying application updates caused port binding conflicts and orphaned container errors.
 * **Solution:** Implemented non-blocking pre-deployment cleanup steps (docker stop & docker rm || true) to guarantee zero-downtime container replacement.
 

 * **Jenkins Pipeline Execution: Pipeline runs cleanly through all 5 automated stages in under 3 minutes.
 * **Docker Hub Repository:** Images published successfully under bamzy14/my-repo tagged with both dynamic build numbers and latest.
 * **Container Runtime:** Application actively serves traffic on port 3000:3000 via container my-running-node-app.
