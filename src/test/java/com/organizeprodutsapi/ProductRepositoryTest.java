package com.organizeprodutsapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
	
	@Autowired
	ProductRepository repository;
	
	private List<Product> products = new ArrayList<Product>();
	
	@Before
	public void setUp() throws Exception {
		Product product1 = new Product("123", "7898100848355", "XBOX One", "nikana", 1300.00, 30L);
		Product product2 = new Product("456", "7898100848356", "Sony Playstation", "nikana", 1500.00, 50L);
		Product product3 = new Product("789", "7898100848357", "Controle XBOX One", "nikana", 250.00, 45L);
		
		Product product4 = new Product("7728uu", "7898100848355", "XBOX One", "trek", 1400.00, 1L);
		Product product5 = new Product("7729uu", "7898100848356", "Sony Playstation", "trek", 1600.00, 3L);
		Product product6 = new Product("7730uu", "7898100848357", "Controle XBOX One", "trek", 260.00, 0L);

		Product product7 = new Product("u7042", "7898100848355", "XBOX One", "redav", 1200.00, 4L);
		Product product8 = new Product("u7043", "7898100848356", "Sony Playstation", "redav", 1400.00, 0L);
		Product product9 = new Product("u7044", "7898100848357", "Controle XBOX One", "redav", 220.00, 20L);
		
		products.add(product1);
		products.add(product2);
		products.add(product3);
		products.add(product4);
		products.add(product5);
		products.add(product6);
		products.add(product7);
		products.add(product8);
		products.add(product9);
		
		for (Product product : products) {
			repository.save(product);
		}
	}
	
	/**
	 * Clear repository after tests.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		repository.deleteAll();
	}

	@Test
	public void testRepositoryLoadGroups() {
		List<String> groups = repository.loadGroups("ean", null, "ean:asc");
		
		assertEquals("7898100848355", groups.get(0));
		assertEquals("7898100848356", groups.get(1));
		assertEquals("7898100848357", groups.get(2));
		assertEquals(3, groups.size());
	}
	
	@Test
	public void testRepositoryFindProducts() {
		List<Product> foundProducts = repository.findProducts("ean", "7898100848355", null, "id:asc");

		assertEquals("123", foundProducts.get(0).getId());
		assertEquals("7728uu", foundProducts.get(1).getId());
		assertEquals("u7042", foundProducts.get(2).getId());
		assertEquals(3, foundProducts.size());
	}
	
	@Test
	public void testRepositoryValidateField() {
		assertTrue(repository.validateField("title"));
		assertTrue(repository.validateField(null));
		assertFalse(repository.validateField("aaa"));
	}
	
	@Test
	public void testRepositoryFindById() {
		Product product = new Product("zzz", "7898100848355", "XBOX One", "nikana", 1300.00, 30L);
		repository.save(product);
		Product savedProduct = repository.findById(product.getId());
		
		assertEquals(savedProduct.getId(), product.getId());
		
		repository.deleteAll();
	}

}
