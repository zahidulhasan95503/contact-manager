package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entites.Contact;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
	
	@Query("from Contact as c where c.user.Id =:userId")
	//current page
	//contacts per page = 5
	public Page<Contact> findContactsByUser(@Param("userId")int userId, Pageable pageable);
		
	

}
