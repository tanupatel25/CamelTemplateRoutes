package com.camelTemplates.ApacheCamelTemplates.FileRoutes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**************************************************************************

Classname : FileToActiveMqRoute
Project   : ApacheCamelTemplates
Purpose   : Provide file based route definition using Apache Camel


Author    Date          Issue#   Comments
--------- ------------- -------- -----------------------------------
tpatel    June 28,2024           Initial implementation
tpatel    June 28,2024           The code is currently giving a 400 Bad Request error 
=========================================================================
>> Implementation Overview:    

    Uses Apache Camel API to do file based routing. File moves from toHttpFolder directory to toHttpFolder/processed. 
    The transfer is being done using the http protocols. 
    As a way to test the code a locally hosted RESTAPI server was setup using the code provided in /Users/tanupatel/Desktop/restapi.py
    The logs in between help to identify the changes that have been made.
 **************************************************************************/

//@Component
public class FileToHttpRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// Define the error handling route
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Error occurred: ${exception.message}")
		.handled(true)
		.to("file:files/toHttpFolder/error");

		// Define the main route
		
		from("http://{{camel.component.http.endpoint}}")
		
		.log(LoggingLevel.INFO, "Processing file: ${file:name}")
		.setHeader("CamelHttpMethod", constant("POST"))
		.setHeader("Accept", constant("application/json")) // Set accept header
		.setHeader(Exchange.CONTENT_TYPE, constant("multipart/form-data")) // Set content type
		.multicast() // Use multicast to send to multiple endpoints
		.to("http://{{camel.component.http.endpoint}}") // HTTP endpoint
		.to("file:files/toHttpFolder/processed"); // File system for processed files
		
	}

}
