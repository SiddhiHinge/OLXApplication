package com.olx.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.CategoryEntity;
import com.olx.entity.StatusEntity;
import com.olx.exception.EmptyListException;
import com.olx.exception.InvalidIdException;
import com.olx.repository.CategoryRepository;
import com.olx.repository.StatusRepository;

@Service
public class MasterDataServiceImpl implements MasterDataService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Category> getAllCategories() {
		System.out.println("Inside MasterDataServiceImpl.getAllCategories()");
		List<CategoryEntity> categoryEntities = categoryRepository.findAll();
		List<Category> categories = new ArrayList<Category>();
		if (categoryEntities != null && categoryEntities.size() > 0) {
			for (CategoryEntity categoryEntity : categoryEntities) {
				Category categoryDto = this.modelMapper.map(categoryEntity, Category.class);
				categories.add(categoryDto);
			}
			return categories;
		} else {
			throw new EmptyListException("Categories");
		}

	}

	@Override
	public List<Status> getAllStatus() {
		System.out.println("Inside MasterDataServiceImpl.getAllStatus()");
		List<StatusEntity> statusEntities = statusRepository.findAll();
		List<Status> statusDtos = new ArrayList<Status>();
		for (StatusEntity statusEntity : statusEntities) {
			statusDtos.add(new Status(statusEntity.getId(), statusEntity.getStatus()));
		}
		return statusDtos;
	}

	@Override
	public String getStatusNameById(int statusId) {
		System.out.println("Inside MasterDataServiceImpl.getStatusById()");
		String status = statusRepository.findStatusNameById(statusId);
		if (status == null) {
			return "Pending";
		} else {
			return status;
		}

	}

	@Override
	public Category getCategoryById(int categoryId) {
		System.out.println("Inside MasterDataServiceImpl.getCategoryById()");
		Optional<CategoryEntity> entity = categoryRepository.findById(categoryId);
		if (entity.isPresent()) {
			CategoryEntity categoryEntity = entity.get();
			Category category = this.modelMapper.map(categoryEntity, Category.class);
			return category;
		}
		throw new InvalidIdException("category Id : " + categoryId);
	}

	@Override
	public String getCategoryNameById(int categoryId) {
		System.out.println("Inside MasterDataServiceImpl.getCategoryNameById()");
		String categoryName = categoryRepository.findCategoryNameById(categoryId);
		if (categoryName == null) {
			return "Not Mentioned";
		} else {
			return categoryName;
		}
	}

	@Override
	public Integer getStatusIdByName(String statusName) {
		System.out.println("Inside MasterDataServiceImpl.getStatusIdByName()");
		List<Status> statusList = getAllStatus();
		for (Status status : statusList) {
			if(status.getStatus().equals(statusName)) {
				return status.getId();
			}
		}
		return null;
	}
	
	@Override
	public Integer getCategoryIdByName(String categoryName) {
		System.out.println("Inside MasterDataServiceImpl.getCategoryIdByName()");
		List<Category> categoryList = getAllCategories();
		for (Category category : categoryList) {
			if(category.getCategory().equals(categoryName)) {
				return category.getId();
			}
		}
		return null;
	}

}
