package com.olx.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginDelegate {

	public Boolean isValidToken(String authToken);
	public String getUsername(String authToken);
}
