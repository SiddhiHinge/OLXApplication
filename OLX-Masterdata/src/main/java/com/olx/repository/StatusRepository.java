 package com.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olx.entity.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer>{

	@Query("SELECT s.status FROM StatusEntity s where s.id = :id") 
    String findStatusNameById(@Param("id") int id);
	
}
