# CS511-ExtractionServer

To install this web app you need to have Apache Maven installed. The go to the root of the cloned respository and run the following commands:
- mvn clean install

This command builds and packages the artifcats in a war file. Then you need to have a Apache Tomcat installed. copy target/ExtractionServer.war file to webapps directory of TOmcat. Run the tomcat by running <TOMCAT>/bin/start.sh and application should be deployed at http://<host>:8080/ExtractionServer. Server name should be set in the sample.js file of the Chrome Extension.
