package com.olx.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.olx.dto.Category;
import com.olx.entity.CategoryEntity;

public class MasterDataConverter {
	
	@Autowired
	ModelMapper modelMapper;
	
	public Category ConvertCategoryEntityToCategoryDto(CategoryEntity categoryEntity) {
		Category category = this.modelMapper.map(categoryEntity,Category.class);
		return category;
	}
}
