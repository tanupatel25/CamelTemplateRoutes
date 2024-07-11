package com.camelTemplates.ApacheCamelTemplates.ActiveMqRouteFromArs;

import java.util.List;


import org.apache.camel.builder.RouteBuilder;


public interface RouteBuilderFactory {
	   
    public List<RouteBuilder> createOutboundInstance(//Outbound outboundConfig,
    		String routeId, String archiveLocation)
    		//ArsInterfacerConfig arsInterfacerConfigurator) 
    		throws Exception;
    
    public RouteBuilder createInboundInstance(//Inbound inboundConfig
    		String routeId) throws Exception;
}
