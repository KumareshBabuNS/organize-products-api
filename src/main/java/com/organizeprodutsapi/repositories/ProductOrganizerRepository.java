package com.organizeprodutsapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organizeprodutsapi.product.Product;

public interface ProductOrganizerRepository extends JpaRepository<Product, String> {
	
	@Transactional
	List<Product> findAll();

}
