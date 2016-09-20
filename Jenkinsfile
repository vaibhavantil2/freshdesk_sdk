node {
try {
   stage 'Unit Tests'
   def nodeHome = tool 'node'
   env.PATH = "${nodeHome}/bin:${env.PATH}"
   sh 'npm install'
   sh 'npm run ci-test'
   sh 'npm pack'
   archive 'frsh-sdk*.tgz'
   stash includes: 'frsh-sdk*.tgz', name: 'frsh-sdk'
   //Publish Clover Report
   step([$class: 'CloverPublisher', cloverReportDir: 'coverage', cloverReportFileName: 'clover.xml'])

   stage 'Code Style Check'
   try {
       sh 'npm run check-build'
   } catch(Exception e) {
       echo 'check-build failed. still continuing.'
   }
   stage 'Integration Tests'
   git credentialsId: '8caa9e6f-de7b-4667-ada3-4f1380acd19d', url: 'git@github.com:freshdesk/freshapps_sdk-test.git'
   unstash name: 'frsh-sdk'
   sh 'npm install'
   sh 'npm test --sdkurl=frsh-sdk*.tgz'
   publishHTML(target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'mochawesome-reports', reportFiles: 'mochawesome.html', reportName: 'HTML Report'])
} catch(Exception e) {
    mail (to: 'freshapps-engg@freshdesk.com, freshapps-qa@freshdesk.com',
        subject: "Pipeline '${env.JOB_NAME}' (${env.BUILD_NUMBER}) Failed!",
        body: "Please go to ${env.BUILD_URL}.");
        throw e;
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
