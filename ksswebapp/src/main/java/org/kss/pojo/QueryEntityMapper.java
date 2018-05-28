package org.kss.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryEntityMapper {
	
	private String query;
	private ArrayList<String> location;
	private ArrayList<String> produce;
	
	public ArrayList<String> getProduce() {
		return produce;
	}
	public void setProduce(ArrayList<String> produce) {
		this.produce = produce;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public ArrayList<String> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<String> location) {
		this.location = location;
	}
	@Override
    public String toString() {
        return "Entities{" +
                "query='" + query + '\'' +
                ", produce=" + produce.toString() + ", location=" + location.toString() +
                '}';
    }
}
