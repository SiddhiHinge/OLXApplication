package com.olx.service;

public interface MasterDataDelegate {

	public String getStatusNameById(int statusId);
	public Integer getStatusIdByName(String statusName);
	public String getCategoryNameById(int categoryId);
	public Integer getCategoryIdByName(String categoryName);
}
