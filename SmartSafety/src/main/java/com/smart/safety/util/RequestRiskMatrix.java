package com.smart.safety.util;

import java.io.*;
import java.util.*;

import org.springframework.web.client.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.smart.safety.controller.PrintController.CODETYPE;

public class RequestRiskMatrix {
	public static RequestRiskMatrix INSTANCE = new RequestRiskMatrix();
	public static final String RISK_DATA_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getRiskData=&codelist=";
	public static final String CODE_DETAIL_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getDetailByJSON=";
	public static final String GET_PERMIT_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getPermitList=";
	public static final String TOOL_IMG_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getToolImage=&filename=";
	public static final String CHECK_IMG_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getChekcListImage=&filename=";
	public static final String FILE_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Category.action?getFile=&fileIdx=";
	
	
	public String getRiskData(String list) {
		RestTemplate restTemplate = new RestTemplate();
		String url = RISK_DATA_URL + list;
		String result = restTemplate.getForObject(url, String.class);
		String json_result = result.substring(result.indexOf('(') + 1, result.length() - 1);
		return json_result;
				
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getPermitList(String workcode, String placecodes) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = GET_PERMIT_URL + "&workcode="+ workcode + "&placecodes=" + placecodes;
		String result = restTemplate.getForObject(url, String.class);
		String json_result = result.substring(result.indexOf('(') + 1, result.length() - 1);
		if(json_result != null) {
			System.out.println(json_result.toString());
			return objectMapper.readValue(json_result, Map.class);
		}
		
		return null;
				
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getDetailByJSON(String code, CODETYPE type) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();	
		RestTemplate restTemplate = new RestTemplate();
		
		String url = CODE_DETAIL_URL + "&code="+code+"&type="+type.idx;
		String result = restTemplate.getForObject(url, String.class);
		String json_result = result.substring(result.indexOf('(') + 1, result.length() - 1);
		if(json_result != null) {
			System.out.println(json_result.toString());
			return objectMapper.readValue(json_result, Map.class);
		}
		return null;
	}
	
}
