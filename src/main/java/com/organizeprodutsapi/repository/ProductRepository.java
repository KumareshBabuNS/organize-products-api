package com.organizeprodutsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.organizeprodutsapi.product.Product;

@Transactional(readOnly=true)
public interface ProductRepository extends JpaRepository<Product, String>, ProductCustomRepository {

	Product findById(String id);
	
}
