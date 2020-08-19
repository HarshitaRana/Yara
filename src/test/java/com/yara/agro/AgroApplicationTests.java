package com.yara.agro;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class AgroApplicationTests {

	 @LocalServerPort
	    int randomServerPort;
	 
	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateField() throws URISyntaxException 
	{
		
		ResponseEntity<String> result = createFieldHelper();
		//Verify response
		Assert.assertEquals(201, result.getStatusCodeValue());
		
	}
	
	
	@Test
	public void testGetField() throws URISyntaxException 
	{
		final String baseUrl = "http://localhost:"+randomServerPort+"/api/fields/5f3ad61757ac6400073c8498";
		URI uri = new URI(baseUrl);

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		//Verify response
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void testGetFieldWeather() throws URISyntaxException 
	{
		final String baseUrl = "http://localhost:"+randomServerPort+"/api/fields/5f3ad61757ac6400073c8498/weather";
		URI uri = new URI(baseUrl);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		//Verify response
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void testUpdateField() throws URISyntaxException 
	{
		final String baseUrl = "http://localhost:"+randomServerPort+"/api/fields/5f3c368e714b52fee1e0e8da";
		URI uri = new URI(baseUrl);
		double x= Math.random()*(10)+1;
		String input = "{"+
				"\"name\":\"Polygon "+x+"\""+
			"}";
		HttpEntity request = new HttpEntity(input);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
		//Verify response
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void testDeleteField() throws URISyntaxException 
	{
		ResponseEntity<String> create_result = createFieldHelper();
		JSONObject o;
		try {
			o = new JSONObject(create_result.getBody());
			testid=(String) o.get("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		final String baseUrl = "http://localhost:"+randomServerPort+"/api/fields/"+testid;
		URI uri = new URI(baseUrl);
		HttpEntity request = new HttpEntity("");
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
		//Verify response
		Assert.assertEquals(204, result.getStatusCodeValue());
	}
	
	private ResponseEntity<String> createFieldHelper() throws URISyntaxException  {
		final String baseUrl = "http://localhost:"+randomServerPort+"/api/fields";
		URI uri = new URI(baseUrl);
		double x= Math.random()*(10)+1;
		String json = "{"+
		   "\"name\":\"Polygon "+x+"\","+
		   "\"geo_json\":{"+
		      "\"type\":\"Feature\","+
		      "\"properties\":{"+

		      "},"+
		      "\"geometry\":{"+
		         "\"type\":\"Polygon\","+
		         "\"coordinates\":["+
		           "["+
		               "[-121.1958,37.6683],"+
		               "[-121.1779,37.6687],"+
		               "[-121.1773,37.6792],"+
		               "[-121.1958,37.6792],"+
		               "[-121.1958,37.6683]"+
		            "]"+
		         "]"+
		      "}"+
		   "}"+
		"}";

		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity(uri, json, String.class);
	return result;
	}


	private String testid=null;
}
