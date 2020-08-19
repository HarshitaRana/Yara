/*
 * API class to interact with backend/Open Agro Monitoring API
 * for fields information
 * 
 * */
package com.yara.agro.api;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.yara.agro.exception.AgroMonitorException;

public class FieldsAPI {

	//Get information of given field
	public String getFieldInfo(String fieldId) throws AgroMonitorException {
		log.info("Getting field Info:"+fieldId);
		String uri = baseUri+"/"+fieldId+appid;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = null;
		try{
			result = restTemplate.getForEntity(uri, String.class);
			if(result.getStatusCodeValue()==200)
				return result.getBody();
			else
				throw new AgroMonitorException(result.getBody());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new AgroMonitorException(e.getMessage());
		}finally{
			//releasing resources;
			result = null;
			restTemplate=null;
		}
	}

	//Create a given field
	public String createField(String input) throws AgroMonitorException {
		log.info("Creating a new field");
		String uri = baseUri+appid;
		ResponseEntity<String> result = null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type","application/json"); 
		HttpEntity request = new HttpEntity(input, headers);
		try{
			result = restTemplate.postForEntity( uri, request, String.class);
			if(result.getStatusCodeValue()==201)
				return result.getBody();
			else
				throw new AgroMonitorException(result.getBody());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new AgroMonitorException(e.getMessage());
		}finally{
			//releasing resources;
			result = null;
			restTemplate=null;
		}
	}

	//Update a given field
	public String updateField(String id, String input) throws AgroMonitorException {
		log.info("Updating field Info: "+id);
		String uri = baseUri;
		if(id!=null){
			uri+="/"+id+appid;
		}
		else{
			throw new AgroMonitorException("Field id is required");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type","application/json"); 

		HttpEntity request = new HttpEntity(input, headers);
		ResponseEntity<String> result = null;
		RestTemplate restTemplate = new RestTemplate();
		try{
			result = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
			if(result.getStatusCodeValue()==200)
				return result.getBody();
			else
				throw new AgroMonitorException(result.getBody());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new AgroMonitorException(e.getMessage());
		}finally{
			//releasing resources;
			result = null;
			restTemplate=null;
		}

	}

	//Delete a given field
	public String deleteField(String id) throws AgroMonitorException {
		log.info("Deleting field: "+id);
		String uri = baseUri;
		if(id!=null){
			uri+="/"+id+appid;
		}
		else{
			throw new AgroMonitorException("Field id is required");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type","application/json"); 
		HttpEntity request = new HttpEntity(headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = null;
		try{
			result = restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
			if(result.getStatusCodeValue()==204)
				return result.getBody();
			else
				throw new AgroMonitorException(result.getBody());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new AgroMonitorException(e.getMessage());
		}finally{
			//releasing resources;
			result = null;
			restTemplate=null;
		}

	}
	
	//Get information of all fields
	public String getAllFieldInfo() throws AgroMonitorException {
		log.info("Getting all field info..");
		String uri = baseUri+appid;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = null;
		try{
			result = restTemplate.getForEntity(uri, String.class);
			if(result.getStatusCodeValue()==200)
				return result.getBody();
			else
				throw new AgroMonitorException(result.getBody());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new AgroMonitorException(e.getMessage());
		}finally{
			//releasing resources;
			result = null;
			restTemplate=null;
		}
	}

	private String baseUri = "http://api.agromonitoring.com/agro/1.0/polygons";
	private String appid = "?appid=a9c1b8458e33266586f21bfa9b9cb615";
	private static Logger log = Logger.getLogger(FieldsAPI.class);
	


}
