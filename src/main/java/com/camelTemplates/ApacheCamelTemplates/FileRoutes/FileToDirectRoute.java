package com.camelTemplates.ApacheCamelTemplates.FileRoutes;

import com.camelTemplates.ApacheCamelTemplates.RouteTemplate;

import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;

/**************************************************************************

Classname : FileToDirectRoute
Project   : ApacheCamelTemplates
Purpose   : Provide file based route definition using Apache Camel

Author    Date          Issue#   Comments
--------- ------------- -------- -----------------------------------
tpatel    June 20,2024           Initial implementation
tpatel    June 28,2024           Working version, ready to demo. 
=========================================================================
>> Implementation Overview:    

    Uses Apache Camel API to do file based routing. File moves from input directory to direct, 
    synchronous route. The example transformation is changing the body of the message to "My File to Direct Router". 
    The logs in between help to identify the changes that have been made.
    Limitations:
    Synchronous Only: The direct component is synchronous, meaning it blocks the producer until the consumer has processed the message.
    Single JVM: It cannot be used to communicate across different JVMs or Camel contexts.
 **************************************************************************/


//@Component
public class FileToDirectRoute extends RouteTemplate  {

    @Override
    public void configureRoute() {

        // Define the error handling route
        onException(Exception.class)
        .log(LoggingLevel.ERROR, "Error occurred: ${exception.message}")
        .handled(true)
        .to("file:files/ToActiveMqFolder/error");

        from("file:files/directFolder") 
        .log(LoggingLevel.INFO, "Processing file: ${file:name}")
        .log("Body of the message: ${body}")
        .transform().constant("My File to Direct Router") // Sample Transformation 
        .log("${body}")
        .to("direct:start")
        // .to("file:files/directFolder/processed") // File system for processed files
        .log(LoggingLevel.INFO, "File successfully uploaded to direct endpoint: ${file:name}");
        
        // Consumer for the direct:start endpoint
        from("direct:start")
        .log(LoggingLevel.INFO, "Consuming from direct:start: ${body}")
        .to("file:files/directFolder/processed");

    }

}
