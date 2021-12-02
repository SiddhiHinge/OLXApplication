package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginDelegateImpl implements LoginDelegate {

	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Override
	public Boolean isValidToken(String authToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authToken);
		HttpEntity entity = new HttpEntity(headers);
		try {
			ResponseEntity<Boolean> response = this.restTemplate.exchange("http://API-GATEWAY/olx/user/validate/token",
					HttpMethod.GET, entity, Boolean.class);
			if (response.getStatusCode() == HttpStatus.OK)
				return response.getBody();
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getUsername(String authToken) {
		ResponseEntity<String> responseUsername = restTemplate.getForEntity("http://API-GATEWAY/olx/user/username?auth-token="+authToken, String.class);
		return responseUsername.getBody();
	}

}
