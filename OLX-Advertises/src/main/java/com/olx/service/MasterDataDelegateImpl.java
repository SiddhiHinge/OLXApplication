package com.olx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class MasterDataDelegateImpl implements MasterDataDelegate{

	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Override
	@CircuitBreaker(name = "STATUS-FROM-MASTER-DATA-SERVICE", fallbackMethod = "fallbackStatusMasterData")
	public String getStatusNameById(int statusId) {
		ResponseEntity<String> responseStatus = restTemplate.getForEntity("http://API-GATEWAY/olx/masterData/advertise/status/"+statusId, String.class);
		return responseStatus.getBody();
	}

	@Override
	public String getCategoryNameById(int categoryId) {
		ResponseEntity<String> responseCategory = restTemplate.getForEntity("http://API-GATEWAY/olx/masterData/advertise/category/name/"+categoryId, String.class);
		return responseCategory.getBody();
	}

	@Override
	public Integer getStatusIdByName(String statusName) {
		ResponseEntity<Integer> responseStatusId = restTemplate.getForEntity("http://API-GATEWAY/olx/masterData/advertise/statusId/"+statusName, Integer.class);
		return responseStatusId.getBody();
	}
	
	@Override
	public Integer getCategoryIdByName(String categoryName) {
		ResponseEntity<Integer> responseCategoryId = restTemplate.getForEntity("http://API-GATEWAY/olx/masterData/advertise/categoryId/"+categoryName, Integer.class);
		return responseCategoryId.getBody();
	}
	
	public String fallbackStatusMasterData(int statusId, Exception exception) {
		System.out.println("Fallback mathod called "+exception);
		return null;
	}

}
