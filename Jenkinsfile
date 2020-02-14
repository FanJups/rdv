pipeline{
    agent any
    stages{
        
        stage('COMPILE') {
            steps{
                script{
                    last_started = env.STAGE_NAME;
                    
                    sh "mvn clean install -Dmaven.test.skip=true";
                    
                    echo "$last_started Passed successfully!!";
                }
                
            }
        }
        
        stage('JUNIT:JaCoCo') {
            steps{
                script {
                    last_started = env.STAGE_NAME;
                    sh "mvn org.jacoco:jacoco-maven-plugin:prepare-agent -Dmaven.test.failure.ignore-false";
                    echo "$last_started Passed successfully!!";
                }
                
            }
        }
        
        
        //stage('SonarQube Analysis') {
          //  steps{
            //    script{
              //      withSonarQubeEnv('SonarQube') {
                //        last_started = env.STAGE_NAME;
                  //      //sh 'mvn sonar:sonar';
                    //    echo "$last_started Passed successfully!!";
                //    }
                //}
            //    
        //    }
    //    }
        
       // stage('Quality Gate Status Check') {
        //    steps{
        //        script{
        //            last_started = env.STAGE_NAME;
        //            
                    //timeout(time: 1, unit: 'HOURS'){
                    //    def qg = waitForQualityGate()
                    //    if( qg.status != 'OK'){
                    //        error "Pipeline aborted due to Quality Gate failure: ${qg.status}"
                    //    }
                    //}
        //            
        //            echo "$last_started Passed successfully!!";
        //            
        //        }
        //        
        //    }
    //    }
        
        
        stage('Arret du Backend') {
            steps{
                script{
                    last_started = env.STAGE_NAME;
                       try {
                             sh script:"sudo fuser -k -n tcp 8092 >> tmp";
                            } catch (error) {}
                    
                    
                    echo "$last_started Passed successfully!!";
                    
                }
                
            }
        }
        
        
        stage('Deploiement') {
            steps{
                script{
                    last_started = env.STAGE_NAME;
                    
                    emailext attachLog: true,
                    mimeType: 'text/html',
                    to: 'mimba.ngouana@advance-it-group.biz,if5.forge@advance-it-group.biz,dany.djimgou@advance-it-group.biz,emmanuel.emmeni@advance-it-group.biz,fanon.jupkwo@advance-it-group.biz',
                    subject: last_started +"  - RDV Backend v2:  "+ currentBuild.currentResult +"#$BUILD_NUMBER",
                    body: "En piece-jointe le fichier des logs de ${env.JOB_NAME}#$BUILD_NUMBER ";        
            
                    sh script: "cd ${env.WORKSPACE}/target && sudo java -jar rdvserver-2.jar --server.port=8092";
                    echo "$last_started Passed successfully!!";
                    
                }
                
            }
        }
    }
    
    post {
        failure {
            echo "$last_started : " +currentBuild.currentResult;
            
            emailext attachLog: true,
            mimeType: 'text/html',
            to: 'mimba.ngouana@advance-it-group.biz,if5.forge@advance-it-group.biz,dany.djimgou@advance-it-group.biz,emmanuel.emmeni@advance-it-group.biz,fanon.jupkwo@advance-it-group.biz',
            subject: last_started +"  - RDV Backend v2:  "+ currentBuild.currentResult +"#$BUILD_NUMBER",
            body: "En piece-jointe le fichier des logs de ${env.JOB_NAME}#$BUILD_NUMBER ";
            //body: '${FILE, path="/var/lib/jenkins/jobs/$JOB_NAME/builds/$BUILD_NUMBER/log"}' , replyTo: 'if5.forge@advance-it-group.biz, dany.djimgou@advance-it-group.biz,emmanuel.emmeni@advance-it-group.biz,fanon.jupkwo@advance-it-group.biz',
            //sh script: "echo if5-forge | sudo -S sleep 1 && sudo su - if5-forge;mail -s 'Test'  baounabaouna@gmail.com << /dev/null";
            //mail bcc: '', body: 'Test pipeline', cc: '', from: 'if5.forge@advance-it-group.biz', replyTo: 'if5.forge@advance-it-group.biz', subject: 'Test pipeline', to: 'baounabaouna@gmail.com';
        }
        success{
            echo "$last_started : " +currentBuild.currentResult;
            
            emailext attachLog: true,
            mimeType: 'text/html',
            to: 'mimba.ngouana@advance-it-group.biz,if5.forge@advance-it-group.biz,dany.djimgou@advance-it-group.biz,emmanuel.emmeni@advance-it-group.biz,fanon.jupkwo@advance-it-group.biz',
            subject: last_started +"  - RDV Backend v2:  "+ currentBuild.currentResult +"#$BUILD_NUMBER",
            body: "En piece-jointe le fichier des logs de ${env.JOB_NAME}#$BUILD_NUMBER ";
        }
    }
}