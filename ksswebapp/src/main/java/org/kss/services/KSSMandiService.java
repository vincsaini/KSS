package org.kss.services;

import java.util.ArrayList;
import java.util.List;

import org.kss.kssdao.MongoConnection;
import org.kss.pojo.KSSEntity;
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
		
		List<DBObject> rsults = new ArrayList<DBObject>();
		
		DBCursor cursor = dbcollection.find(getMongoQuery(query));
		for(DBObject mandiObj:cursor) {
			// map db object to java object
			//System.out.println(mandiObj.toString());
			rsults.add(mandiObj);
		}
		//return the final JSON response of mandi price
		Gson gson = new Gson();
		return gson.toJson(rsults);
	}
	
	public DBObject getMongoQuery(QueryEntityMapper query) {
		//prepare the list of commodity
		List<String> commodity = new ArrayList<String>();
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
		DBObject dbQuery = new BasicDBObject("market",new BasicDBObject("$in", market));
		//.append($in"commodity", new BasicDBObject("$in",market)));
		return dbQuery;
	}

}
