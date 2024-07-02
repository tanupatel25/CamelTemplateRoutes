package com.camelTemplates.ApacheCamelTemplates;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.Transformer;
import org.apache.camel.Processor;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public abstract class RouteTemplate extends RouteBuilder {
    // Method to configure the route
    public abstract void configureRoute();

    // Method to add a processor
    protected void addProcessor(String fromEndpoint, String toEndpoint, Processor processor) {
        from(fromEndpoint).process(processor).to(toEndpoint);
    }

    // Method to add a transformation
    protected void addTransformation(String fromEndpoint, String toEndpoint, Transformer transformer) {
        //from(fromEndpoint).transform(body().method(transformer, "transform")).to(toEndpoint);
    }

    // Override the configure method from RouteBuilder
    @Override
    public void configure() {
        configureRoute();
    }
}

