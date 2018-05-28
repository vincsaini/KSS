package org.kss.kssweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KssQueryResolver {
	
	@RequestMapping("/querykss")
	public String resolveQuery(@RequestParam(value="query", required=true, defaultValue="price Carrort Surat") String query) {
		// get the entities present in the query
		// make a call to entity resolver to get the entities
		System.out.println("query"+query);
		
		return "greeting";
	}
}
