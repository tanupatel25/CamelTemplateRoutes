package com.camelTemplates.ApacheCamelTemplates.ActiveMqRouteFromArs;

import java.util.List;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

	
	@Component
	public class RouteBuilderFactoryImpl implements RouteBuilderFactory {
	    
	    @Autowired
	    private InboundErrorProcessor inboundErrorProcessor;
	    
	    @Autowired
	    private InboundMessageProcessor inboundMessageProcessor;
	    
	    @Autowired
	    private XsltProcessor xsltProcessor;

		@Override
		public List<RouteBuilder> createOutboundInstance(String routeId, String archiveLocation) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RouteBuilder createInboundInstance(String routeId) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

}
