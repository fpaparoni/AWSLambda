package com.javastaff.aws.lambda.schedule;

public class CloudWatchEvent {
	private String id;
	private String source;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}