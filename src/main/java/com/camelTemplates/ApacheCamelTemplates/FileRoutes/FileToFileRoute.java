package com.camelTemplates.ApacheCamelTemplates.FileRoutes;

import com.camelTemplates.ApacheCamelTemplates.RouteTemplate;
import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;

/**************************************************************************

Classname : FileToFileRoute
Project   : ApacheCamelTemplates
Purpose   : Provide file based route definition using Apache Camel
 
  
Author    Date          Issue#   Comments
--------- ------------- -------- -----------------------------------
tpatel    June 20,2024           Initial implementation
tpatel    June 28,2024           Working version, ready to demo. 
=========================================================================
>> Implementation Overview:    
 
    Uses Apache Camel API to do file based routing. File moves from input directory to output directory, 
    The example transformation is changing the body of the message to "My File Router". 
    The logs in between help to identify the changes that have been made.
**************************************************************************/
//@Component
public class FileToFileRoute extends RouteTemplate {

	@Override
	public void configureRoute() {
		
		// Define the error handling route
				onException(Exception.class)
				.log(LoggingLevel.ERROR, "Error occurred: ${exception.message}")
				.handled(true)
				.to("file:files/input/error");
		
		from("file:files/input") // ?noop=true ensures that the files remain in inputDirectory after being read
		.log(LoggingLevel.INFO, "Processing file: ${file:name}")
		.transform().constant("My File Router!") // Sample transformation
		.to("file:files/output/processed") // File system for processed files
		.to("file:files/input/processed")// File system for processed files
		.log(LoggingLevel.INFO, "File successfully uploaded to output folder: ${file:name}");
	
		
		
	}
	
	

}
