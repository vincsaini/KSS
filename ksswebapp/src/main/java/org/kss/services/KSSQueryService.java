package org.kss.services;

import java.util.ArrayList;
import java.util.List;

import org.kss.kssdao.MongoConnection;
import org.kss.pojo.KSSEntity;
import org.kss.pojo.KSSMandiResults;
import org.kss.pojo.QueryEntityMapper;
import org.kss.util.KSSConstants;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class KSSQueryService implements KSSService {

	@Override
	public String serveRequest(QueryEntityMapper query) {
		Gson gson = new Gson();
		List<DBObject> results = new ArrayList<DBObject>();
		DBCollection dbcollection = MongoConnection.getCollection("mandi", "kccquery");
		// get the mongo query based on the intent and entity
		DBObject mongoQuery = getMongoQuery(query);

		System.out.println("DB Query in KSS Query " + mongoQuery.toString());

		DBCursor cursor = dbcollection.find(mongoQuery);

		for (DBObject mandiObj : cursor) {
			// map db object to java object
			// System.out.println(mandiObj.toString());
			results.add(mandiObj);
		}

		// create the result object
		KSSMandiResults kssResults = new KSSMandiResults();
		kssResults.setResultsType(query.getIntent().getName());
		kssResults.setQueryResults(results);

		return gson.toJson(kssResults);
	}

	public DBObject getMongoQuery(QueryEntityMapper query) {

		System.out.println("Inside variety query get");
		List<String> crop = new ArrayList<String>();
		List<BasicDBObject> varietyQuery = new ArrayList<BasicDBObject>();
		// prepare query for varieties
		if (KSSConstants.CROP_VARIETY.equalsIgnoreCase(query.getIntent().getName())
				|| KSSConstants.CROP_PROB.equalsIgnoreCase(query.getIntent().getName())) {

			for (KSSEntity entity : query.getEntities()) {
				if (KSSConstants.CROP.equals(entity.getEntity())) {
					crop.add(entity.getValue());
					System.out.println("Added Crop " + entity.getValue());
				} else if (KSSConstants.COMMODITY.equals(entity.getEntity())) {
					crop.add(entity.getValue());
					System.out.println("Added Crop " + entity.getValue());
				} else if (KSSConstants.DESEASE.equals(entity.getEntity())) {
					crop.add(entity.getValue());
					System.out.println("Added Desease " + entity.getValue());
				}
			}
			// TODO need to do the error handling in case when we get no crop value
			varietyQuery.add(new BasicDBObject("$text", new BasicDBObject("$search", crop.get(0))));
			if(KSSConstants.CROP_VARIETY.equalsIgnoreCase(query.getIntent().getName())){
				varietyQuery.add(new BasicDBObject("QueryType", "Varieties"));
			}else if(KSSConstants.CROP_PROB.equalsIgnoreCase(query.getIntent().getName())){
				varietyQuery.add(new BasicDBObject("QueryType", "Plant Protection"));
			}
		}
		// prepare the list of commodity

		/*
		 * List<String> market = new ArrayList<String>(); for(KSSEntity
		 * entity:query.getEntities()) {
		 * if(KSSConstants.COMMODITY.equals(entity.getEntity())) {
		 * System.out.println("Added Commodity "+entity.getValue());
		 * //commodity.add(entity.getValue()); }else
		 * if(KSSConstants.MARKET.equals(entity.getEntity())) {
		 * System.out.println("Added Market "+entity.getValue());
		 * market.add(entity.getValue()); } }
		 */
		// prepare the list of market
		// if(market.size()>0) {
		// TODO need to handle multiple strings
		// List<BasicDBObject> andQuery = new ArrayList<BasicDBObject>();
		// andQuery.add(new BasicDBObject("$text",new BasicDBObject("$search",
		// market.get(0))));
		// andQuery.add(new BasicDBObject("commodity", new BasicDBObject("$in",crop)));

		DBObject dbQuery = new BasicDBObject();
		dbQuery.put("$and", varietyQuery);
		// new BasicDBObject("$text",new BasicDBObject("$search", market.get(0)));
		// append(new BasicDBObject("commodity", new BasicDBObject("$in",commodity))));
		// }
		// .append($in"commodity", new BasicDBObject("$in",market)));
		return dbQuery;
	}
}
