package com.olx.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.service.MasterDataService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("olx/masterData")
public class MasterDataController {

	@Autowired
	private MasterDataService masterDataService;
	static ArrayList<Category> categories = new ArrayList<Category>();
	static ArrayList<Status> statusList = new ArrayList<Status>();

	@GetMapping(value = "/advertise/category", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint gets all categories")
	public List<Category> getAllCategories() {
		System.out.println("Inside MasterDataController.getAllCategories()");
		return masterDataService.getAllCategories();
	}

	@GetMapping(value = "/advertise/status", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint gets all status")
	public List<Status> getAllStatus() {
		System.out.println("Inside MasterDataController.getAllStatus()");
		return masterDataService.getAllStatus();
	}
	
	@GetMapping(value = "/advertise/status/{id}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint gets status name by Id")
	public String getStatusNameById(@PathVariable("id") int statusId) {
		System.out.println("Inside MasterDataController.getStatusNameById()");
		return masterDataService.getStatusNameById(statusId);
	}
	
	@GetMapping(value = "/advertise/category/{id}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint gets category by Id")
	public Category getCategoryById(@PathVariable("id") int categoryId) {
		System.out.println("Inside MasterDataController.getCategoryById()");
		return masterDataService.getCategoryById(categoryId);
	}
	
	@GetMapping(value = "/advertise/category/name/{id}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint gets category name by Id")
	public String getCategoryNameById(@PathVariable("id") int categoryId) {
		System.out.println("Inside MasterDataController.getCategoryNameById()");
		return masterDataService.getCategoryNameById(categoryId);
	}
	
	@GetMapping(value = "/advertise/statusId/{statusName}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint gets status Id by Name")
	public Integer getStatusIdByName(@PathVariable("statusName") String statusName) {
		System.out.println("Inside MasterDataController.getStatusIdByName()");
		return masterDataService.getStatusIdByName(statusName);
	}
	
	@GetMapping(value = "/advertise/categoryId/{categoryName}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value="This REST endpoint gets category Id by Name")
	public Integer getCategoryIdByName(@PathVariable("categoryName") String categoryName) {
		System.out.println("Inside MasterDataController.getCategoryIdByName()");
		return masterDataService.getCategoryIdByName(categoryName);
	}
}
