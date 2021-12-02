package com.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olx.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{

	@Query("SELECT c.category FROM CategoryEntity c where c.id = :id") 
    String findCategoryNameById(@Param("id") int id);
}
