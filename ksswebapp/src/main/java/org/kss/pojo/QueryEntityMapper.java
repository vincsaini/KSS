package org.kss.pojo;

import java.util.ArrayList;
import com.google.gson.Gson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryEntityMapper {
	
	private Intent intent;
	private ArrayList<Intent> intentRankings;
	private ArrayList<KSSEntity> entities;
	
	public ArrayList<KSSEntity> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<KSSEntity> entities) {
		this.entities = entities;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	
	

	public ArrayList<Intent> getIntentRankings() {
		return intentRankings;
	}
	public void setIntentRankings(ArrayList<Intent> intentRankings) {
		this.intentRankings = intentRankings;
	}
	
	@Override
    public String toString() {
		Gson gson = new Gson();
        return gson.toJson(this);
    }
}
