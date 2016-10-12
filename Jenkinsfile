node {
try {

   stage('Unit Tests') {
   checkout scm
   def nodeHome = tool 'node'
   env.PATH = "${nodeHome}/bin:${env.PATH}"
   sh 'npm install'
   sh 'npm test'
   sh 'npm pack'
   archive 'frsh-sdk*.tgz'
   //Publish Clover Report
   step([$class: 'CloverPublisher', cloverReportDir: 'coverage', cloverReportFileName: 'clover.xml'])
   }

   stage('Code Style Check') {
   try {
       sh 'npm run check-build'
   } catch(Exception e) {
       echo 'check-build failed. still continuing.'
   }
   }

   stage('Integration Tests') {
   try {
   sh '''rm -rf freshapps_sdk-test;
   branchToUse=`git ls-remote --heads origin | grep $(git rev-parse HEAD) | cut -d / -f 3`;
   git clone -b $branchToUse  git@github.com:freshdesk/freshapps_sdk-test.git;
   '''
   } catch(Exception e) {
       sh 'git clone -b master  git@github.com:freshdesk/freshapps_sdk-test.git'
   }
   sh '''cd freshapps_sdk-test;
   npm install;
   sdkpath=`ls -Art ../frsh-sdk*.tgz | tail -n 1`;
   npm test --sdkurl=$sdkpath;
   '''
   publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'freshapps_sdk-test/mochawesome-reports', reportFiles: 'mochawesome.html', reportName: 'HTML Report'])
   }

   currentBuild.result = 'SUCCESS'
   } catch(any) {
    currentBuild.result = 'FAILURE'
    step([$class: 'ClaimPublisher'])
    throw any
   } finally {
    step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'freshapps-engg@freshdesk.com', sendToIndividuals: false])
  }

//Check for newer version of SDK:
sh 'ls frsh-sdk* | wc -l > ./count'
def count = readFile('count').trim()
if(count == "2" ) {
    mail (to: 'freshapps-engg@freshdesk.com, freshapps-qa@freshdesk.com',
        subject: "New Version of SDK is available!",
        body: "You can download it from ${env.BUILD_URL}.");
    sh 'ls -t1 frsh-sdk-* | tail -n 1  | xargs rm'
}
}
