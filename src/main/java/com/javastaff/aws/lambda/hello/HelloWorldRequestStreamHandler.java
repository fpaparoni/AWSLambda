package com.javastaff.aws.lambda.hello;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context; 

public class HelloWorldRequestStreamHandler implements RequestStreamHandler{
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		int letter;
		outputStream.write("Hello World stream ".getBytes());
        while((letter = inputStream.read()) != -1){
            outputStream.write(Character.toUpperCase(letter));
        }
	}
}