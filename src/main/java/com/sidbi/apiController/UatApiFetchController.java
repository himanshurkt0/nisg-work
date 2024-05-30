package com.sidbi.apiController;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UatApiFetchController {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getUatApiJSONValidator(String userName, String authToken) throws JsonMappingException, JsonProcessingException {

		String apiUrl = "https://ssouat.sidbi.in:8443/login-1.0/uservalid";
		RestTemplate restTemplate = new RestTemplate();
		String apiResponseKey = "apiResponse";
		String apiResponse = "NA";
		Map<String, String> requestData = new HashMap<>();
		requestData.put("userName", userName);
		requestData.put("authToken", authToken);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccessControlAllowOrigin("https://capuat.sidbi.in/CapDesk/validateLogin");
		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestData, headers);
		ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			Map<String, String> responseBody = responseEntity.getBody();

			if (responseBody != null) {
				for (Map.Entry<String, String> entry : responseBody.entrySet()) {
					if (entry.getKey().equals(apiResponseKey)) {
						apiResponse = entry.getValue();
					}
				}
			} else {
				apiResponse = "No Response";
			}
		}

		return apiResponse;

	}

}
