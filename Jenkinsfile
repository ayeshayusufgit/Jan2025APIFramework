pipeline 
{
    agent any	//	no agent is being used so any is written
    
    tools{
        maven 'maven' // 'maven' name should be the same as the Jenkin's tool Configuration -> Maven installations -> Name: 'maven'
        }

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 //build the project from the dev repo(as mentioned above), dummy project from the git hub
                 
                 //sh "mvn -Dmaven.test.failure.ignore=true clean package" for mac machines
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"	//for windows machine
                 //mvn clean package will delete the target and package will generate the jar
                 //commandline arguments in maven is provided by the -D
                 //on running the utils or unit tcs etc if there is a failure then its ignored as per -Dmaven.test.failure.ignore=true
            }
            post 
            {	
				//once the previous steps are successful then the junit reports are generated
            	//also the jar is generated in the target folder
            	
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml' //report of the unit tcs
                    archiveArtifacts 'target/*.jar'	//the jar file has to be deployed to the DEV Environment
                }
            }
        }
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to QA done")
            }
        }
        
        stage('Regression API Automation Test on QA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    //git 'https://github.com/naveenanimation20/Jan2025APIFramework.git'	Naveens API Repo
                    git	https://github.com/ayeshayusufgit/Jan2025APIFramework.git			//My API Repo
                   //sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=qa"
                   //maven runs the tcs by passing the xml file testng_regression.xml to it, currently there is no environment 
                   //so the -Denv=qa is removed as below
                   
                   //The deployment is done pls reun regression testcases
                   //sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml" for mac machines
                   bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml" //for windows machines
                   
                   //the maven command will goto the pom.xml file of My Git Repo
                   //1. download the repo in jenkins
                   //2. Goto the pom file->to the compiler->then to the surefire plugin-> to the testng_regression.xml  
                   //3.	Run the  testng_regression.xml tcs
                   //Once the execution of the TCs is done then the reports are published                 
                }
            }
        }        
     
     	
     	//To generate the Allure Report
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        //To generate the ChainTest Report
        stage('Publish ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML API Regression ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        //After the generation of the reports the build has to be deployed to Stage
        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }
        
        //Once the build is deployed to Stage then the API Sanity Automtion TCs are to be executed 
        stage('Sanity API Automation Test on Stage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    //git 'https://github.com/naveenanimation20/Jan2025APIFramework.git'
                    git	https://github.com/ayeshayusufgit/Jan2025APIFramework.git //My API Automation Repo
                    
                    //sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml -Denv=stage"
                  
                  	//sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml" for mac machines
                  	bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml"	//for windows machines
                    
                    //testng_sanity will contain only the CRUD operations, which is executed
                    
                }
            }
        }
        
        //After the execution of Sanity TCs in Stage, the publishing of the ChainTest Report(not Allure Report)
        stage('Publish sanity ChainTest Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'target/chaintest', 
                                  reportFiles: 'Index.html', 
                                  reportName: 'HTML API Sanity ChainTest Report', 
                                  reportTitles: ''])
            }
        }
        
        //After Stage the build will be deployed to PROD
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")       
            }
        }       
    }
}