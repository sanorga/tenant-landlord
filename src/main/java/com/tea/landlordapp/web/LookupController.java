package com.tea.landlordapp.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.tea.landlordapp.dto.LookupPropertyDto;
import com.tea.landlordapp.service.LookupService;

@Controller
public class LookupController {

	@Autowired
	LookupService lookupService;
	
	@RequestMapping(value = "/lookup/{id}/propertyById.json", method = RequestMethod.GET)
	@ResponseBody
	public String getPropertyLookupDtoById(
			@PathVariable(value = "id") Integer id,
			HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException {
		
		LookupPropertyDto dto = lookupService.getPropertyDtoById(id);
		
		return convert2Json(dto);
	}
	
	private String convert2Json(Object obj) {
		if (obj != null){
			Gson gson = new Gson();
			return gson.toJson(obj);
		}
		
		return null;
	}
	
}
