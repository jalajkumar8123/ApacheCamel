package com.testyantra.transfer;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpProducer;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ReadFileToActive {
	public static void main(String a[]) {
		
		CamelContext context = new DefaultCamelContext();
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		context.addComponent("jms",  JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		try {
			context.addRoutes(new RouteBuilder() {
				
				@Override
				public void configure() throws Exception {
					
					//rest("rest:get:localhost:8080").get("/actor/")
					from("file:input_box").to("activemq:queue:testyantra_queue");
					
				}
			});
			while(true)
				context.start();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
