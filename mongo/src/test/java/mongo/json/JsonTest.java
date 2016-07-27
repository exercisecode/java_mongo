package mongo.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import junit.framework.TestCase;

public class JsonTest extends TestCase {

	public void testJson() {

		JSONObject s1 = new JSONObject();
		s1.put("param1", 1);
		s1.put("param2", "value2");
		s1.put("jsonBoolean", Boolean.TRUE);
		// s1.put("jsonCollection", null);
		s1.put("jsonDouble", Double.valueOf("11.22"));
		s1.put("jsonInt", Integer.valueOf(1111));
		s1.put("jsonLong", Long.valueOf("22334455"));
		s1.put("jsonString", "string value");

		JSONArray a1 = new JSONArray();
		a1.put(100).put(55).put(96).put(67);

		JSONObject demoJson = new JSONObject();
		demoJson.put("_id", "578f729ae4b09889e8a3a405");
		demoJson.put("groupId", "@TGS#2ONDWGBEG");
		demoJson.put("fromAccount", "76ef6c2824942bea729af2f3e92cbd7e");
		demoJson.put("callbackCommand", "Group.CallbackAfterSendMsg");
		demoJson.put("type", "Public");
		demoJson.put("createAt", "2016-07-20T20:46:18.941");

		JSONObject demoMessageJson = new JSONObject();
		demoMessageJson.put("msgType", "TIMCustomElem");
		// demoMessageJson.put("fileSize", 0);
		// demoMessageJson.put("desc", "flow");

		JSONObject demoContentJson = new JSONObject();
		demoContentJson.put("ext", "");
		demoContentJson.put("fileSize", 0);
		demoContentJson.put("desc", "flow");

		JSONObject demoDataJson = new JSONObject();
		demoDataJson.put("screenWidth", 0);
		demoDataJson.put("pageIndex", 14);
		demoDataJson.put("screenHeight", 0);
		demoDataJson.put("index", 26);
		demoDataJson.put("subType", "basic");
		demoDataJson.put("controlId", "LogicTreeDislayNextPart3");
		demoDataJson.put("type", "templateAction");
		demoDataJson.put("command", "tap");

		demoContentJson.put("data", demoDataJson.toString());
		demoMessageJson.put("content", demoContentJson);
		demoJson.put("message", demoMessageJson);

		String[] targetArray = new String[] { "abcd", s1.toString(), a1.toString(), demoJson.toString() };

		for (String targetString : targetArray) {
			JSONObject json = parseString(targetString);

			System.out.println(json + "\n\n\n");
		}

	}

	public JSONObject parseString(String string) {
		JSONObject jsonObject = null;
		if (string != null && !string.trim().isEmpty()) {
			boolean stringIsJsonObject = stringIsJSONObject(string);
			if (stringIsJsonObject) {
				jsonObject = new JSONObject();
				JSONObject targetJson = new JSONObject(string.trim());
				Set<String> jsonKeySet = targetJson.keySet();

				for (String jsonKey : jsonKeySet) {
					Object jsonValue = targetJson.get(jsonKey);

					if (jsonValue != null) {
						if (Boolean.class.equals(jsonValue.getClass())) {
							Boolean booleanValue = (Boolean) jsonValue;
							jsonObject.put(jsonKey, booleanValue);
						} else if (Double.class.equals(jsonValue.getClass())) {
							Double doubleValue = (Double) jsonValue;
							jsonObject.put(jsonKey, doubleValue);
						} else if (Integer.class.equals(jsonValue.getClass())) {
							Integer integerValue = (Integer) jsonValue;
							jsonObject.put(jsonKey, integerValue);
						} else if (Long.class.equals(jsonValue.getClass())) {
							Long longValue = (Long) jsonValue;
							jsonObject.put(jsonKey, longValue);
						} else if (String.class.equals(jsonValue.getClass())) {
							String stringValue = (String) jsonValue;
							boolean valueIsJsonObject = stringIsJSONObject(stringValue);
							boolean valueIsJsonArray = stringIsJSONArray(stringValue);
							if (!valueIsJsonObject && !valueIsJsonArray) {
								jsonObject.put(jsonKey, stringValue);
							} else if (valueIsJsonObject) {
								JSONObject valueJsonObject = parseString(stringValue);
								jsonObject.put(jsonKey, valueJsonObject);
							} else if (valueIsJsonArray) {
								List<JSONObject> valueJsonObjectList = parseStringToJSONArray(stringValue);
								jsonObject.put(jsonKey, valueJsonObjectList);
							}
						} else if (JSONObject.class.equals(jsonValue.getClass())) {
							JSONObject valueJsonObject = parseString(jsonValue.toString());
							jsonObject.put(jsonKey, valueJsonObject);
						} else if (JSONArray.class.equals(jsonValue.getClass())) {
							List<JSONObject> valueJsonObjectList = parseStringToJSONArray(jsonValue.toString());
							jsonObject.put(jsonKey, valueJsonObjectList);
						} else {
							System.out.println("\t " + jsonValue.getClass());
						}
					} else {
						jsonObject.put(jsonKey, "");
					}
				}

			} else {
				System.out.println(" \t " + string + " is not json ");
			}
		}

		return jsonObject;
	}

	public List<JSONObject> parseStringToJSONArray(String string) {
		List<JSONObject> jsonObjectList = null;
		if (stringIsJSONArray(string)) {
			JSONArray jsonArray = new JSONArray(string.trim());
			jsonObjectList = new ArrayList<JSONObject>();
			int arrayLength = jsonArray.length();
			for (int arraySize = 0; arraySize < arrayLength; arraySize++) {
				Object objectValue = jsonArray.get(arraySize);
				if (objectValue != null) {
					JSONObject jsonObject = parseString(objectValue.toString());
					jsonObjectList.add(jsonObject);
				} else {
					jsonObjectList.add(null);
				}
			}
		}
		return jsonObjectList;
	}

	public boolean stringIsJSONObject(String string) {
		boolean stringIsJsonObject = false;

		if (string != null && !string.trim().isEmpty()) {
			try {
				new JSONObject(string.trim());
				stringIsJsonObject = true;
			} catch (Exception e) {
				// error , not a json
			}
		}
		return stringIsJsonObject;
	}

	public boolean stringIsJSONArray(String string) {
		boolean stringIsJsonArray = false;
		if (string != null && !string.trim().isEmpty()) {
			try {
				new JSONArray(string.trim());
				stringIsJsonArray = true;
			} catch (Exception e) {
				// error, not a json array
			}
		}
		return stringIsJsonArray;
	}

}
