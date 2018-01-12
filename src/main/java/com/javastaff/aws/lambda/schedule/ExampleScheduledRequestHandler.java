package com.javastaff.aws.lambda.schedule;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;

public class ExampleScheduledRequestHandler implements RequestHandler<ScheduledEvent, String>{

	public String handleRequest(ScheduledEvent event, Context context) {
		String logString=String.format("Id %s source %s",event.getId(),event.getSource());
		context.getLogger().log(logString);
        return "Scheduled handler returns: " + logString;
	}
	
}
