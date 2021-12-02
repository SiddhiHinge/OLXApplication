package com.olx.actuators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.olx.repository.AdvertiseRepository;

@Component
public class CustomAdvertiseInfoActuator implements InfoContributor {
	
	@Autowired
	AdvertiseRepository advertiseRepository;

	@Override
	public void contribute(Builder builder) {
		builder.withDetail("total-active-advertises", advertiseRepository.countOfActiveAdvertises());
		builder.withDetail("total-advertises", advertiseRepository.countOfAdvertises());
	}

}
