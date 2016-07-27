package mongo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Service
public class MongoDemoService {
	
	@Autowired
	private MongoDbFactory mongoDbFactory;
	
	public JSONObject demo(){
		JSONObject jsonObject = null;
		
		
		if(mongoDbFactory != null){
			DB teachingDb = mongoDbFactory.getDb("teaching-service-b1");
			if(teachingDb != null ){
				DBCollection collection = teachingDb.getCollection("group_callback_after_sendmsg");
				if(collection != null){
					DBObject dbObject = collection.findOne();
					
					jsonObject = parseToJson(dbObject);
					//object = dbObject;
				}
			}
		}
		
		return jsonObject;
	}
	
	public JSONObject parseToJson(DBObject dbObject){
		JSONObject jsonObject = new JSONObject();
		
		Set<String> keySet = dbObject.keySet();
		
	
		for(String key : keySet){
			
			Object value = dbObject.get(key);
			if(value != null){
				System.out.println("  [" + key + "]  value is " + value + " ,  type is [" + value.getClass() + "]");
				
				if(ObjectId.class.equals(value.getClass())){
					ObjectId objectId = (ObjectId)value;
					String stringId = objectId.toString();
					jsonObject.put(key, stringId);
				}else if(String.class.equals(value.getClass())){
					String stringValue = (String)value;
					
					if("data".equals(key)){
						JSONObject dataJson = new JSONObject(value.toString());
						jsonObject.put(key, dataJson);
					}else{
					  jsonObject.put(key, stringValue);
					}
				}else if(BasicDBObject.class.equals(value.getClass())){
					BasicDBObject basicDBObject = (BasicDBObject)value;
					JSONObject dbObjectJson = parseToJson(basicDBObject);
					jsonObject.put(key, dbObjectJson);
				}else if(BasicDBList.class.equals(value.getClass())){
					List<JSONObject> valueList = new ArrayList<JSONObject>();
					
					BasicDBList basicDBList = (BasicDBList)value;
					if(basicDBList != null && !basicDBList.isEmpty()){
						int totalListSize = basicDBList.size();
						for(int size = 0; size < totalListSize; size++){
							Object oneObject = basicDBList.get(size);
							if(oneObject != null && BasicDBObject.class.equals(oneObject.getClass())){
								BasicDBObject basicDBObject =  (BasicDBObject)oneObject;
								
								JSONObject basicJson = parseToJson(basicDBObject);
								valueList.add(basicJson);
							}
							
						}
					}
					
					jsonObject.put(key, valueList);
				}
			}else{
				System.out.println("  " + key + "  value is null ");
			}
		}
		
		return jsonObject;
	}

}
