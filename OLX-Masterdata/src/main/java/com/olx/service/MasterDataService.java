package com.olx.service;

import java.util.List;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.exception.EmptyListException;

public interface MasterDataService {
	public List<Category> getAllCategories();

	public List<Status> getAllStatus();

	public String getStatusNameById(int statusId);

	public Category getCategoryById(int categoryId);

	public String getCategoryNameById(int categoryId);
	
	public Integer getStatusIdByName(String statusName);

	public Integer getCategoryIdByName(String categoryName);

}
