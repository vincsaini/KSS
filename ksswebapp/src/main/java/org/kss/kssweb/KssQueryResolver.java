package org.kss.kssweb;

import org.kss.pojo.QueryEntityMapper;
import org.kss.services.KSSService;
import org.kss.services.KSSServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class KssQueryResolver {
	
	@RequestMapping("/querykss")
	public String resolveQuery(@RequestParam(value="query", required=true, defaultValue="price Carrort Surat") String query) {
		// get the entities present in the query
		// make a call to entity resolver to get the entities
		System.out.println("query"+query);
		
		String uri = "http://localhost:5000/kssnlp/"+query;
		RestTemplate restTemplate = new RestTemplate();
		QueryEntityMapper queryEntityMapper = restTemplate.getForObject(uri, QueryEntityMapper.class);
		System.out.println("Queries and entities returned "+queryEntityMapper.toString());
		// 
		KSSService kssService = KSSServiceFactory.getKSSService(queryEntityMapper.getQuery());
		String resultJson = kssService.serveRequest(queryEntityMapper);
		
		return "greeting";
	}
}
