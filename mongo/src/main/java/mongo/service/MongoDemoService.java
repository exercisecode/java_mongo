package mongo.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import mongo.tool.JSONTool;

@Service
public class MongoDemoService {

	@Autowired
	private MongoDbFactory mongoDbFactory;

	public JSONObject demo() {
		JSONObject jsonObject = null;

		if (mongoDbFactory != null) {
			DB teachingDb = mongoDbFactory.getDb("teaching-service-b1");
			if (teachingDb != null) {
				DBCollection collection = teachingDb.getCollection("group_callback_after_sendmsg");
				if (collection != null) {

					int testSize = 0;
					List<DBObject> dbObjectList = collection.find().toArray();
					if (dbObjectList != null) {
						for (DBObject dbObject : dbObjectList) {
							jsonObject = JSONTool.parseToJson(dbObject);
							testSize++;
							System.out.println(" testSize=" + testSize);
						}
					}

					// DBObject dbObject = collection.findOne();
					// jsonObject = parseToJson(dbObject);

				}
			}
		}

		return jsonObject;
	}

}
