package com.organizeprodutsapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.organizeprodutsapi.product.Product;

@Transactional(readOnly=true)
public interface ProductRepository extends JpaRepository<Product, String> {
	
	List<Product> findAll();
	
	Product findById(String id);
	
	List<Product> findByTitle(String title);
	
	List<Product> findByEan(String ean);
	
	List<Product> findByBrand(String brand);
	
	List<Product> findByPrice(Double price);
	
	List<Product> findByStock(Long stock);

}
