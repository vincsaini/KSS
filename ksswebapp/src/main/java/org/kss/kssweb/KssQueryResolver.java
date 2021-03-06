package org.kss.kssweb;

import org.kss.pojo.KSSEntity;
import org.kss.pojo.QueryEntityMapper;
import org.kss.services.KSSService;
import org.kss.services.KSSServiceFactory;
import org.kss.util.KSSConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class KssQueryResolver {

	@RequestMapping("/querykss")
	public String resolveQuery(
			@RequestParam(value = "query", required = true, defaultValue = "price Carrort Surat") String query) {
		String resultJson="";
		try {
			// get the entities present in the query
			// make a call to entity resolver to get the entities
			System.out.println("query" + query);

			// String uri = "http://localhost:5000/kssnlp/"+query;
			String uri = "http://localhost:5000/parse?q=" + query;
			RestTemplate restTemplate = new RestTemplate();
			QueryEntityMapper queryEntityMapper = (QueryEntityMapper) restTemplate.getForObject(uri,
					QueryEntityMapper.class);
			
			//function to correct the entities
			for (KSSEntity entity : queryEntityMapper.getEntities()) {
				if (KSSConstants.COMMODITY.equals(entity.getEntity())) {
					String spellCheck = "http://localhost:5002/kssspell/" + entity.getValue();
					RestTemplate restTemplate1 = new RestTemplate();
					String corrected = (String) restTemplate1.getForObject(spellCheck,String.class);
					entity.setValue(corrected.substring(1,corrected.length()-1));
					System.out.println("Corrected "+corrected.substring(1,corrected.length()-1));
				}
			}
			
			System.out.println("Queries and entities returned " + queryEntityMapper.toString());
			//
			KSSService kssService = KSSServiceFactory.getKSSService(queryEntityMapper.getIntent().getName());
			resultJson = kssService.serveRequest(queryEntityMapper);
			System.out.println("Result from the service"+resultJson);
			
		} catch (Exception e) {
			System.err.println("Error in resolving the query");
			e.printStackTrace();
			return "error";
		}
		return resultJson;
	}
}
