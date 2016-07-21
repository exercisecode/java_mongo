package mongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Service
public class MongoDemoService {
	
	@Autowired
	private MongoDbFactory mongoDbFactory;
	
	public Object demo(){
		Object object = null;
		if(mongoDbFactory != null){
			DB teachingDb = mongoDbFactory.getDb("teaching-service-b1");
			if(teachingDb != null ){
				DBCollection collection = teachingDb.getCollection("group_callback_after_sendmsg");
				if(collection != null){
					DBObject dbObject = collection.findOne();
					
					object = dbObject;
				}
			}
		}
		
		return object;
	}

}
