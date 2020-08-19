package com.yara.agro;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yara.agro.api.FieldsAPI;
import com.yara.agro.api.WeatherAPI;
import com.yara.agro.exception.AgroMonitorException;

@RestController
@RequestMapping("/api")
public class AgroController {

	//Get field info
	@RequestMapping(value = "/fields/{fieldId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getField(@PathVariable String fieldId) {
		String result = null;
		try{
			result = fields.getFieldInfo(fieldId);
		}catch(AgroMonitorException e){
			result = error_msg+e.getMessage();
			return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	//Get info of all fields
		@RequestMapping(value = "/fields", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> getAllFields() {
			String result = null;
			try{
				result = fields.getAllFieldInfo();
			}catch(AgroMonitorException e){
				result = error_msg+e.getMessage();
				return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>(result,HttpStatus.OK);
		}


	//Create a Field
	@RequestMapping(value = "/fields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createField(@RequestBody String input) {
		String result = null;
		try{
			result = fields.createField(input);
		}catch(AgroMonitorException e){
			result = error_msg+e.getMessage();
			return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(result, HttpStatus.CREATED);
	}


	//Update a field
	@RequestMapping(value = "/fields/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateField(@PathVariable("id") String id, @RequestBody String input) {
		String result=null;
		try{
			result= fields.updateField(id, input);
		}catch(AgroMonitorException e){
			result = error_msg+e.getMessage();
			return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(result,HttpStatus.OK);
	}

	//Delete a field
	@RequestMapping(value = "/fields/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteField(@PathVariable("id") String id) {

		String result=null;
		try{
			result= fields.deleteField(id);
		}catch(AgroMonitorException e){
			result = error_msg+e.getMessage();
			return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}


	//Get weather info
	@RequestMapping(value = "/fields/{fieldId}/weather", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWeather(@PathVariable String fieldId) {

		String result=null;
		try{
			WeatherAPI weather = new WeatherAPI();
			result= weather.getWeatherInfo(fieldId);
		}catch(AgroMonitorException e){
			result = error_msg+e.getMessage();
			return new ResponseEntity<String>(result,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(result,HttpStatus.OK);

	}

	private String error_msg="Unable to process your request. ";
	private FieldsAPI fields = new FieldsAPI();
}
