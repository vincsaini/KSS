package org.kss.services;

import java.util.ArrayList;
import java.util.List;

import org.kss.pojo.KSSMandiResults;
import org.kss.pojo.QueryEntityMapper;
import org.kss.util.KSSConstants;

import com.google.gson.Gson;
import com.mongodb.DBObject;

public class KSSQueryService implements KSSService{

	@Override
	public String serveRequest(QueryEntityMapper query) {
		Gson gson = new Gson();
		List<DBObject> results = new ArrayList<DBObject>();
		KSSMandiResults kssResults = new KSSMandiResults();
		kssResults.setResultsType(KSSConstants.CROP_PROB);
		kssResults.setQueryResults(results);
		return gson.toJson(kssResults);
	}

}
