package mongo.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JSONTool {

	private static Set<String> unsupportClassSet = new TreeSet<String>();

	public static JSONObject parseToJson(DBObject dbObject) {

		JSONObject jsonObject = new JSONObject();

		Set<String> keySet = dbObject.keySet();

		for (String key : keySet) {

			Object value = dbObject.get(key);
			if (value != null) {
				// System.out.println(" [" + key + "] value is " + value + " ,
				// type is [" + value.getClass() + "]");
				if (ObjectId.class.equals(value.getClass())) {
					ObjectId objectId = (ObjectId) value;
					String stringId = objectId.toString();
					jsonObject.put(key, stringId);
				} else if (Integer.class.equals(value.getClass())) {
					Integer valueInteger = (Integer) value;
					jsonObject.put(key, valueInteger);
				} else if (String.class.equals(value.getClass())) {
					String stringValue = (String) value;

					if ("data".equals(key)) {
						JSONObject dataJson = new JSONObject(value.toString());
						jsonObject.put(key, dataJson);
					} else {
						jsonObject.put(key, stringValue);
					}
				} else if (BasicDBObject.class.equals(value.getClass())) {
					BasicDBObject basicDBObject = (BasicDBObject) value;
					JSONObject dbObjectJson = parseToJson(basicDBObject);
					jsonObject.put(key, dbObjectJson);
				} else if (BasicDBList.class.equals(value.getClass())) {
					List<JSONObject> valueList = new ArrayList<JSONObject>();

					BasicDBList basicDBList = (BasicDBList) value;
					if (basicDBList != null && !basicDBList.isEmpty()) {
						int totalListSize = basicDBList.size();
						for (int size = 0; size < totalListSize; size++) {
							Object oneObject = basicDBList.get(size);
							if (oneObject != null && BasicDBObject.class.equals(oneObject.getClass())) {
								BasicDBObject basicDBObject = (BasicDBObject) oneObject;

								JSONObject basicJson = parseToJson(basicDBObject);
								valueList.add(basicJson);
							}

						}
					}

					jsonObject.put(key, valueList);
				} else {
					if (!unsupportClassSet.contains(value.getClass())) {
						unsupportClassSet.add(value.getClass().toString());
					}
					System.out
							.println("\n\n unsupport key[" + key + "]  type[" + value.getClass() + "] value:" + value);
				}
			} else {
				System.out.println("  " + key + "  value is null ");
			}
		}

		return jsonObject;
	}

	public static Set<String> getUnsupportClass() {
		return unsupportClassSet;
	}

}
