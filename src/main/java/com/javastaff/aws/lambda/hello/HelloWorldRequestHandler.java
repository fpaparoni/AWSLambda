package com.javastaff.aws.lambda.hello;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class HelloWorldRequestHandler implements RequestHandler<HelloRequest, String> {

	public String handleRequest(HelloRequest input, Context context) {
		context.getLogger().log("Stringa input: " + input.getUsername());
        return "Hello World " + input.getUsername();
	}

}
