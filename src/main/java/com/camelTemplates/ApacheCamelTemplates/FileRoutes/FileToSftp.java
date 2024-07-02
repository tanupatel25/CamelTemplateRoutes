package com.camelTemplates.ApacheCamelTemplates.FileRoutes;

import org.apache.camel.LoggingLevel;
import com.camelTemplates.ApacheCamelTemplates.RouteTemplate;
import org.springframework.stereotype.Component;

//Track IP address for this by checking remote login from system settings

/**************************************************************************

Classname : FileToActiveMqRoute
Project   : ApacheCamelTemplates
Purpose   : Provide file based route definition using Apache Camel


Author    Date          Issue#   Comments
--------- ------------- -------- -----------------------------------
tpatel    June 28,2024           Initial implementation
tpatel    June 28,2024           Working version, ready to demo. 
=========================================================================
>> Implementation Overview:    

    Uses Apache Camel API to do file based routing. File moves from toSftpFolder directory to toSftpFolder/processed, 
    The transfer is being done using the SFTP protocols. 
    As a way to test the code a locally host SFTP server was setup using  Code is provided in /Users/tanupatel/Desktop/sftpserver.py
    The logs in between help to identify the changes that have been made.
 **************************************************************************/

//@Component
public class FileToSftp extends RouteTemplate {
    
    @Override
    public void configureRoute() {

        // Define the error handling route
        onException(Exception.class)
        .log(LoggingLevel.ERROR, "Error occurred: ${exception.message}")
        .handled(true)
        .to("file:files/toSftpFolder/error");

        // Define the main route
        from("file:files/toSftpFolder")
        .log(LoggingLevel.INFO, "Processing file: ${file:name}")
        .to("sftp://{{camel.component.sftp.username}}@{{camel.component.sftp.host}}:{{camel.component.sftp.port}}{{camel.component.sftp.directory}}?password={{camel.component.sftp.password}}&knownHostsFile=/Users/tanupatel/.ssh/known_hosts")
        .log(LoggingLevel.INFO, "File successfully uploaded to SFTP: ${file:name}")
        .to("file:files/toSftpFolder/processed");
    }

}
