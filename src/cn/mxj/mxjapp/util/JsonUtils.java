package cn.mxj.mxjapp.util;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	private JsonUtils() {

	}

	/**
	 * 将对象转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJson(Object ts) {
		String jsonStr = null;
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializer(Object ts,
			final String dateformat) {
		String jsonStr = null;
		gson = new GsonBuilder()
				.registerTypeHierarchyAdapter(Date.class,
						new JsonSerializer<Date>() {
							@SuppressLint("SimpleDateFormat")
							public JsonElement serialize(Date src,
									Type typeOfSrc,
									JsonSerializationContext context) {
								SimpleDateFormat format = new SimpleDateFormat(
										dateformat);
								return new JsonPrimitive(format.format(src));
							}
						}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * 将json格式转换成list对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr) {
		List<?> objList = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}

	/**
	 * 将json格式转换成list对象，并准确指定类型
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr, java.lang.reflect.Type type) {
		List<?> objList = null;
		if (gson != null) {
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}

	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<?, ?> jsonToMap(String jsonStr) {
		Map<?, ?> objMap = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}

	/**
	 * 获得JSON对象
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T getObjectJSON(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
		}
		return t;
	}

	/**
	 * 获得JSON对象列表
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T, cls> List<T> getObjectJSONList(String jsonString,
			Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<cls>>() {
			}.getType());
		} catch (Exception e) {
		}
		return list;
	}

	public static List<Map<String, Object>> listKeyMaps(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<List<Map<String, Object>>>() {
					}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	/**
	 * 根据
	 * 
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static Object getJsonValue(String jsonStr, String key) {
		Object rulsObj = null;
		Map<?, ?> rulsMap = jsonToMap(jsonStr);
		if (rulsMap != null && rulsMap.size() > 0) {
			rulsObj = rulsMap.get(key);
		}
		return rulsObj;
	}

	// public static MoocOperationResult parseResultFromJson(String jsonData){
	// MoocOperationResult mor = gson.fromJson(jsonData,
	// MoocOperationResult.class);
	// return mor;
	// }
	//
	// public static MoocOperationResult parseResultFromJson(JSONObject
	// jsonData){
	// MoocOperationResult mor = gson.fromJson(jsonData.toString(),
	// MoocOperationResult.class);
	// return mor;
	// }

	@SuppressWarnings("null")
	public static ArrayList<JSONObject> toList(JSONArray array) {

		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		if (array != null || array.length() != 0) {
			for (int index = 0; index < array.length(); index++) {
				try {
					JSONObject jo = array.getJSONObject(index);
					list.add(jo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
		StringBuffer buffer = new StringBuffer();
		try {
			int len = mData.length();
			for (int i = 0; i < len; i++) {
				JSONObject obj1 = (JSONObject) mData.get(i);
				if (i == len - 1)
					buffer.append(obj1.toString());
				else
					buffer.append(obj1.toString()).append(",");
			}
			len = array.length();
			if (len > 0)
				buffer.append(",");
			for (int i = 0; i < len; i++) {
				JSONObject obj1 = (JSONObject) array.get(i);
				if (i == len - 1)
					buffer.append(obj1.toString());
				else
					buffer.append(obj1.toString()).append(",");
			}
			buffer.insert(0, "[").append("]");
			return new JSONArray(buffer.toString());
		} catch (Exception e) {
		}
		return null;
	}
}
