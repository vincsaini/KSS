package org.kss.services;

import org.kss.pojo.KSSEntity;
import org.kss.pojo.KSSWeatherResult;
import org.kss.pojo.QueryEntityMapper;
import org.kss.util.KSSConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class KSSWeatherService implements KSSService{

	@Override
	public String serveRequest(QueryEntityMapper query) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		KSSWeatherResult kssResults = new KSSWeatherResult();
		
		kssResults.setResultsType(KSSConstants.WEATHER_INFO);
		// set the location
		for(KSSEntity entity:query.getEntities()) {
			if(KSSConstants.LOCATION.equals(entity.getEntity())) {
				System.out.println("Added Location "+entity.getValue());
				kssResults.setLocation(entity.getValue());
			}else if(KSSConstants.MARKET.equals(entity.getEntity())) {
				System.out.println("Added Market "+entity.getValue());
				kssResults.setLocation(entity.getValue());
			}
		}
		// make a http call to get the weather response
		String uri = "http://wttr.in/" + kssResults.getLocation();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		kssResults.setWeatherResult(result.getBody());
		
		//System.out.println("Weather result "+result);
		
		return gson.toJson(kssResults);
	}

}
