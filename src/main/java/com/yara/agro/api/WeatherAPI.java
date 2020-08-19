/*
 * API class to interact with backend/Open Agro Monitoring API
 * for weather information
 * 
 * */
package com.yara.agro.api;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.yara.agro.exception.AgroMonitorException;

public class WeatherAPI {

	public String getWeatherInfo(String fieldId) throws AgroMonitorException {
		log.info("Fetching weather info: "+fieldId);
		if(fieldId!=null){
			uri+=fieldId+appid;
		}
		else{
			throw new AgroMonitorException("Field id is required");
		} 
		long now = System.currentTimeMillis()/1000L; //date in unix time
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7); // because we want data of last 7 days
		Date from = cal.getTime(); 
		long utfrom = from.getTime() / 1000L; //date in unix time
		String start = Long.toString(utfrom);
		String end = Long.toString(now);
		uri += "&start="+start+"&end="+end;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = null;
		try{
			result = restTemplate.getForEntity(uri, String.class);
			if(result.getStatusCodeValue()==200){
				String response =  parseWeatherJson(result.getBody());
				return response;
			}	
			else
				throw new AgroMonitorException(result.getBody());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new AgroMonitorException(e.getMessage());
		}finally{
			//releasing resources;
			result = null;
			restTemplate=null;
			start=null;
			end=null;
			cal=null;
		}
	}

	private String parseWeatherJson(String body) throws JSONException {
		JSONArray jsonArray = new JSONArray(body);
		JSONObject weatherData = new JSONObject();
		JSONArray weatherArray = new JSONArray();
		int len = jsonArray.length();
		for(int i=0; i<len; i++){
			Map<String,Object> data = new LinkedHashMap<String,Object>();
			JSONObject j = jsonArray.getJSONObject(i);
			data.put("timestamp",j.get("dt"));
			data.put("temperature",j.getJSONObject("main").get("temp"));
			data.put("humidity",j.getJSONObject("main").get("humidity"));
			data.put("temperatureMax",j.getJSONObject("main").get("temp_max"));
			data.put("temperatureMin",j.getJSONObject("main").get("temp_min"));
			weatherArray.put((Object)data);
		}
		weatherData.put("weather", weatherArray);
		return weatherData.toString();
	}

	private String uri = "https://samples.openweathermap.org/agro/1.0/weather/history?polyid=";
	private String appid ="&appid=a9c1b8458e33266586f21bfa9b9cb615";
	private static Logger log = Logger.getLogger(WeatherAPI.class);
}
