pipeline {
    agent any
    
    tools {
        jdk 'JDK25'
    }
    
    environment {
        // Configure your Docker image name here
        DOCKER_IMAGE = 'kafkamessager'
        DOCKER_TAG = "${BUILD_NUMBER}"
        // Optional: for pushing to Docker Hub, uncomment and set your username
        // DOCKER_REGISTRY = 'your-dockerhub-username'
    }
    
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
        
        stage('Checkout') {
            steps {
                echo 'Checking out source...'
                git branch: 'main', url: 'https://github.com/251027-Java/trainer-code.git'
                echo 'Checked out repo.'
            }
        }
        
        stage('Build & Test') {
            steps {
                dir('./W8/Kafka') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean install'
                }
            }
        }
        
        stage('Package') {
            steps {
                dir('./W8/Kafka') {
                    sh './mvnw package -DskipTests'
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                dir('./W8/Kafka') {
                    echo "Building Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    sh "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
                }
            }
        }
        
        // Optional: Uncomment to push to Docker Hub
        // stage('Docker Push') {
        //     steps {
        //         withCredentials([usernamePassword(
        //             credentialsId: 'dockerhub-creds',
        //             usernameVariable: 'DOCKER_USER',
        //             passwordVariable: 'DOCKER_PASS'
        //         )]) {
        //             sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
        //             sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${DOCKER_TAG}"
        //             sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest"
        //         }
        //     }
        // }
    }
    
    post {
        success {
            echo "✅ Build successful! Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}