package com.gys.kitten.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
	private GsonBuilder builder = new GsonBuilder();
	
	public GsonUtils(){}
	
	public Gson getGson(){
		builder.serializeNulls();
		Gson gson = builder.create();
		return gson;
	}
}
