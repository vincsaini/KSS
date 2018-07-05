package org.kss.services;

import java.util.ArrayList;
import java.util.List;

import org.kss.kssdao.MongoConnection;
import org.kss.pojo.KSSEntity;
import org.kss.pojo.KSSMandiResults;
import org.kss.pojo.QueryEntityMapper;
import org.kss.util.KSSConstants;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.google.gson.Gson;



public class KSSMandiService implements KSSService{
	
	//@Autowired
	//MandiPriceRepository mandiPriceRepo;
	
	
	@Override
	public String serveRequest(QueryEntityMapper query) {
		//MandiPriceRepository mandiPriceRepo = new MandiPriceRepository();
		//List<MandiPrice> listMandiPrice = mandiPriceRepo.getCommodityPrice(query);
		//List<MandiPrice> listMandiPrice = repository.findByCommodity(query.getProduce().get(0));
		DBCollection dbcollection = MongoConnection.getCollection("mandi", "mandi_price");
		//String querySample ="{commodity:{$in:['Guar','Green Chilli']},district:{$in:['Maharajganj']}}";
		
		System.out.println("DB Query"+getMongoQuery(query).toString());
		
		List<DBObject> results = new ArrayList<DBObject>();
		
		DBCursor cursor = dbcollection.find(getMongoQuery(query));
		for(DBObject mandiObj:cursor) {
			// map db object to java object
			//System.out.println(mandiObj.toString());
			results.add(mandiObj);
		}
		//return the final JSON response of mandi price
		Gson gson = new Gson();
		// create the result object
		KSSMandiResults kssResults = new KSSMandiResults();
		kssResults.setResultsType(KSSConstants.COM_PRICE);
		kssResults.setQueryResults(results);
		return gson.toJson(kssResults);
	}
	
	public DBObject getMongoQuery(QueryEntityMapper query) {
		//prepare the list of commodity
		List<String> commodity = new ArrayList<String>();
		//TODO this is default till I resolve the commodity recognition issue in NLP
		//commodity.add("Tomato");
		List<String> market = new ArrayList<String>();
		for(KSSEntity entity:query.getEntities()) {
			if(KSSConstants.COMMODITY.equals(entity.getEntity())) {
				System.out.println("Added Commodity "+entity.getValue());
				commodity.add(entity.getValue());
			}else if(KSSConstants.MARKET.equals(entity.getEntity())) {
				System.out.println("Added Market "+entity.getValue());
				market.add(entity.getValue());
			}
		}
		// prepare the list of market
		//if(market.size()>0) {
		//TODO need to handle multiple strings
		List<BasicDBObject> andQuery = new ArrayList<BasicDBObject>();
		andQuery.add(new BasicDBObject("$text",new BasicDBObject("$search", market.get(0))));
		andQuery.add(new BasicDBObject("commodity", new BasicDBObject("$in",commodity)));
		
		DBObject dbQuery = new BasicDBObject(); dbQuery.put("$and", andQuery);  
				//new BasicDBObject("$text",new BasicDBObject("$search", market.get(0)));
		//append(new BasicDBObject("commodity", new BasicDBObject("$in",commodity))));
		//}
		//.append($in"commodity", new BasicDBObject("$in",market)));
		return dbQuery;
	}

}
