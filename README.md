# CS511-ExtractionServer

To install and deploy this web app you need to have Java 8, Apache Tomcat and Apache Maven installed and a server with at least 10GB of main memory. Then go to the root of the cloned respository and run the following commands:
- mvn clean install

This command builds and packages the artifcats in a war file. Then you need to have a Apache Tomcat installed. copy $PROJECT_ROOT/target/ExtractionServer.war file to $TOMCAT/webapps directory. 
Run the Tomcat instance by running 
- export JAVA_OPTS="-Xms1G -Xmx8G"
- $TOMCAT/bin/start.sh 

And the application should be deployed at http://hostname:8080/ExtractionServer. Server name should be set in the "sample.js" file of the Chrome Extension and the extension must be reloaded.
