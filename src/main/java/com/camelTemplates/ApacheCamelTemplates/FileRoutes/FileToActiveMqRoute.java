package com.camelTemplates.ApacheCamelTemplates.FileRoutes;

import com.camelTemplates.ApacheCamelTemplates.RouteTemplate;

import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;

/**************************************************************************

Classname : FileToActiveMqRoute
Project   : ApacheCamelTemplates
Purpose   : Provide file based route definition using Apache Camel


Author    Date          Issue#   Comments
--------- ------------- -------- -----------------------------------
tpatel    June 20,2024           Initial implementation
tpatel    June 28,2024           Working version, ready to demo. 
=========================================================================
>> Implementation Overview:    

    Uses Apache Camel API to do file based routing. File moves from input directory to ActiveMq queue, 
    The example transformation is changing the body of the message to "My ActiveMq Message". 
    The logs in between help to identify the changes that have been made.
 **************************************************************************/

//@Component
public class FileToActiveMqRoute extends RouteTemplate {

	@Override
	public void configureRoute() {
		// Define the error handling route
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Error occurred: ${exception.message}")
		.handled(true)
		.to("file:files/ToActiveMqFolder/error");

		from("file:files/ToActiveMqFolder")  // noop=true ensures that the files remain in inputDirectory after being read
		.log(LoggingLevel.INFO, "Processing file: ${file:name}")
		.transform().constant("My ActiveMq Message")   //testing transformations which can be done to the body of the message
		.log("${body}")
		.to("activemq:TestQueue")
		.to("file:files/ToActiveMqFolder/processed") // File system for processed files
		.log(LoggingLevel.INFO, "File successfully uploaded to activemq: ${file:name}");
		
		// Picking up the file from ActiveMq and adding it to another directory
		from("activemq:TestQueue")
		.to("file:files/ToActiveMqFolder/processedFromQueue")
		.log(LoggingLevel.INFO, "File : ${file:name} successfully dequeued from activemq.");
		
		
		
		



	}




}
