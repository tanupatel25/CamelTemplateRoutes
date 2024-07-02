package com.camelTemplates.ApacheCamelTemplates.FileRoutes;

import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;
import com.camelTemplates.ApacheCamelTemplates.RouteTemplate;

/**************************************************************************

Classname : FileToFtpRoute
Project   : ApacheCamelTemplates
Purpose   : Provide file based route definition using Apache Camel
 
  
Author    Date          Issue#   Comments
--------- ------------- -------- -----------------------------------
tpatel    June 28,2024           Initial implementation
tpatel    June 28,2024           Working version, ready to demo. 
=========================================================================
>> Implementation Overview:    
 
    Uses Apache Camel API to do file based routing. File moves from toFtpFolder directory to output directory, 
    The example transformation is changing the body of the message to toFtpFolder/processed. The transfer is being done using the FTP protocols. 
    As a way to test the code a locally host FTP server was setup using pyftpdlib. Code is provided in /Users/tanupatel/Desktop/ftpserver.py
    The logs in between help to identify the changes that have been made. 
**************************************************************************/

//@Component
public class FileToFtpRoute extends RouteTemplate {

	@Override
	public void configureRoute() {


		// Define the error handling route
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Error occurred: ${exception.message}")
		.handled(true)
		.to("file:files/toFtpFolder/error");

		// Define the main route
		from("file:files/toFtpFolder")
		.log(LoggingLevel.INFO, "Processing file: ${file:name}")
		.to("ftp://{{camel.component.ftp.username}}@{{camel.component.ftp.host}}:{{camel.component.ftp.port}}{{camel.component.ftp.directory}}?password={{camel.component.ftp.password}}")
		.log(LoggingLevel.INFO, "File successfully uploaded to FTP: ${file:name}")
		.to("file:files/toFtpFolder/processed");
	}

}


