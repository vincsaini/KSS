package org.kss.pojo;

public class Intent {
	private String name;
	private double confidence;
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}