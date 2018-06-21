package org.kss.pojo;

import java.util.List;

import com.mongodb.DBObject;

public class KSSMandiResults {
	private String resultsType;
	List<DBObject> queryResults;
	
	public List<DBObject> getQueryResults() {
		return queryResults;
	}
	public void setQueryResults(List<DBObject> queryResults) {
		this.queryResults = queryResults;
	}
	public String getResultsType() {
		return resultsType;
	}
	public void setResultsType(String resultsType) {
		this.resultsType = resultsType;
	}
}
