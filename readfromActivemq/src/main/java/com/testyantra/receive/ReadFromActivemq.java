package com.testyantra.receive;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ReadFromActivemq {
	
public static void main(String a[]) {
		
		CamelContext context = new DefaultCamelContext();
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		context.addComponent("jms",  JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		try {
			context.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					
					from("activemq:queue:testyantra_queue").to("file:output_box/");
					
				}
			});
			while(true)
				context.start();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}
