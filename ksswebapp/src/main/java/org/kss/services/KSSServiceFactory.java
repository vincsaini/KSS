package org.kss.services;

public class KSSServiceFactory {
	public KSSService getKSSService(String query) {
		KSSService kssService = null;
		if(query.contains("prices")) {
			kssService = new KSSMandiService();
		}else if(query.contains("weather")) {
			kssService = new KSSWeatherService();
		}else {
			kssService = new KSSQueryService();
		}	
		return kssService;
	}
}
