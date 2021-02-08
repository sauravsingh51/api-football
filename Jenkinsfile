pipeline {
    
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
      stage('Build Docker Image'){
        steps {
          sh 'docker build -t apifootball:latest .'
        }
      }
      stage('Run Container') {
        steps {
          sh 'docker run -p 8080:8080 apifootball'
        }
      }
      stage('Deliver') {
          steps {
              sh './jenkins/scripts/deliver.sh'
          }
      }
    }
}