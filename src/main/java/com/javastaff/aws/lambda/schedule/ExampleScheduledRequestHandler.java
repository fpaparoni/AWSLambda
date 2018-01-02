package com.javastaff.aws.lambda.schedule;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ExampleScheduledRequestHandler implements RequestHandler<CloudWatchEvent, String>{

	public String handleRequest(CloudWatchEvent event, Context context) {
		String logString=String.format("Id %s source %s",event.getId(),event.getSource());
		context.getLogger().log(logString);
        return "Scheduled handler returns: " + logString;
	}

}
