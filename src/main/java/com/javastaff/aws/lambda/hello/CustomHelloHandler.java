package com.javastaff.aws.lambda.hello;

import com.amazonaws.services.lambda.runtime.Context;

public class CustomHelloHandler {
	public String myHandler(HelloRequest request, Context context) {
        return String.format("Hello World custom %s.", request.getUsername());
    }
}
