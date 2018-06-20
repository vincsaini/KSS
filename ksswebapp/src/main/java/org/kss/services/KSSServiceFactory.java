package org.kss.services;

import org.kss.util.KSSConstants;

/**
 * Factory to return the service based on the intent
 * @author Vineet
 *
 */
public class KSSServiceFactory {
	/**
	 * 
	 * @param intent
	 * @return
	 */
	public static KSSService getKSSService(String intent) {
		KSSService kssService = null;
		switch(intent) {
			case KSSConstants.COM_PRICE:
				kssService = new KSSMandiService();
				break;
			case KSSConstants.WEATHER_INFO:
				kssService = new KSSWeatherService();
				break;
			case KSSConstants.CROP_PROB:
				kssService = new KSSQueryService();
				break;
		}
		return kssService;
	}
}
