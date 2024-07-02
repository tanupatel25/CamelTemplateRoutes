package com.camelTemplates.ApacheCamelTemplates.FileRoutes;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@ComponentScan
@PropertySource("classpath:application.properties")
public class FileToHttp {

    public static void main(String[] args) throws Exception {
        // Load Spring context to access properties
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FileToHttpRoute.class);
        Environment env = context.getEnvironment();

        // Get properties
        String inputDirectory = env.getProperty("file.inputDirectory");
        String httpEndpoint = env.getProperty("http.endpoint");

        // Create Camel context
        CamelContext camelContext = new DefaultCamelContext();

        // Add our route to the Camel context
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("file:" + inputDirectory + "?noop=true") // Read files from inputDirectory without moving them
                    .setHeader("CamelHttpMethod", constant("POST"))
                    .setHeader("Content-Type", constant("application/octet-stream"))
                    .to(httpEndpoint); // Local HTTP endpoint
            }
        });

        // Start the context
        camelContext.start();

        // Let the route run for some time before stopping
        Thread.sleep(10000);

        // Stop the context
        camelContext.stop();

        // Close Spring context
        context.close();
    }
}
