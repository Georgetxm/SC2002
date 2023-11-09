package entities;

import java.util.HashMap;

public final class Data {
	private final static HashMap<String,Object> data = new HashMap<String,Object>();
	public final static Object get(String key) {
		return data.get(key);
	}
	public final static Boolean containsKey(String key) {
		return data.containsKey(key);
	}
	public final static void put(String key, Object value) {
		data.put(key, value);
	}
	public final static void clear(String key) {
		data.remove(key);
	}
}
