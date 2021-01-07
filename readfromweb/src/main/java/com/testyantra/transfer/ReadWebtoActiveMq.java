package com.testyantra.transfer;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ReadWebtoActiveMq {

	public static void main(String[] args) {
		
CamelContext context = new DefaultCamelContext();
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		context.addComponent("jms",  JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		try {
			context.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					
					//rest("rest:get:localhost:8080").get("/actor/")
					from("jetty:http://localhost:8080/actor").to("activemq:queue:testyantra_queue");
					
				}
			});
			while(true)
				context.start();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
