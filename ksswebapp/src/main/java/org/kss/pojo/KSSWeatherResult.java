package org.kss.pojo;

public class KSSWeatherResult {
	
	private String resultsType;
	private String location;
	private String weatherResult;
	
	public String getWeatherResult() {
		return weatherResult;
	}
	public void setWeatherResult(String weatherResult) {
		this.weatherResult = weatherResult;
	}
	public String getResultsType() {
		return resultsType;
	}
	public void setResultsType(String resultsType) {
		this.resultsType = resultsType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
