package com.sensetime.stapi.utils;

import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class STJsonProvider {
	public static final Gson GSON = new GsonBuilder().create();

	public static String getByKey(String json, String key) {
		String value = "";
		try {
			JSONObject js = new JSONObject(json);
			value = js.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static <T> T getDTO(String json, Class<T> type) {
		return GSON.fromJson(json, type);
	}

	public static <T> List<T> getList(String json) {
		return GSON.fromJson(json, new TypeToken<List<T>>() {
		}.getType());
	}
}
