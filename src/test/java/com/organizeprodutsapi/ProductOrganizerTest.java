package com.organizeprodutsapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.repositories.ProductOrganizerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductOrganizerTest {
	
	private List<Product> products = new ArrayList<Product>();
	
	@Autowired
	private ProductOrganizerRepository repository;
	
	@Before
	public void setUp() throws Exception {
		Product product1 = new Product("123","7898100848355","XBOX One","nikana",1300.00,30L);
		Product product2 = new Product("456","7898100848356","Sony Playstation","nikana",1500.00,50L);
		Product product3 = new Product("789","7898100848357","Controle XBOX One","nikana",250.00,45L);
		
		Product product4 = new Product("7728uu","7898100848355","XBOX One","trek",1400.00,1L);
		Product product5 = new Product("7729uu","7898100848356","Sony Playstation","trek",1600.00,3L);
		Product product6 = new Product("7730uu","7898100848357","Controle XBOX One","trek",260.00,10L);
		
		Product product7 = new Product("u7042","7898100848355","XBOX One","redav",1200.00,4L);
		Product product8 = new Product("u7043","7898100848356","Sony Playstation","redav",1400.00,0L);
		Product product9 = new Product("u7044","7898100848357","Controle XBOX One","redav",220.00,20L);
		
		products.add(product1);
		products.add(product2);
		products.add(product3);
		products.add(product4);
		products.add(product5);
		products.add(product6);
		products.add(product7);
		products.add(product8);
		products.add(product9);
	}
	
	/**
	 * Test if repository is persisting all the products given by a list.
	 */
	@Test
	public void testPersistProductsList() {
		for (Product product : products) {
			repository.save(product);
		}
		
		List<Product> savedProducts = repository.findAll();
		
		assertEquals(9, savedProducts.size());
	}

	@Test
	public void testFilter() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFilterDefault() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGroup() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGroupDefault() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testOrder() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testOrderDefault() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testOrganize() {
		fail("Not yet implemented");
	}

}
