package org.kss.kssdao;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {
	static MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));;
	public static DBCollection getCollection(String dbname, String collection) {
		DBCollection dbcollection = null;
		try {
			DB database = mongoClient.getDB(dbname);
			dbcollection = database.getCollection(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbcollection;
	}
}
