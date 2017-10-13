package com.organizeprodutsapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.organizeprodutsapi.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
	
	private List<Product> products = new ArrayList<Product>();
	
	@Autowired
	private ProductRepository repository;
	
	@Before
	public void setUp() throws Exception {
		Product product1 = new Product("123","7898100848355","XBOX One","nikana",1300.00,30L);
		Product product2 = new Product("456","7898100848356","Sony Playstation 4","nikana",1500.00,50L);
		Product product3 = new Product("789","7898100848357","Controle XBOX One","nikana",250.00,45L);
		
		Product product4 = new Product("7728uu","7898100848355","XBOX One","trek",1400.00,1L);
		Product product5 = new Product("7729uu","7898100848356","Sony Playstation 4","trek",1600.00,3L);
		Product product6 = new Product("7730uu","7898100848357","Controle XBOX One","trek",260.00,10L);
		
		Product product7 = new Product("u7042","7898100848355","XBOX One","redav",1200.00,0L);
		Product product8 = new Product("u7043","7898100848356","Sony Playstation 4","redav",1400.00,0L);
		Product product9 = new Product("u7044","7898100848357","Controle XBOX One","redav",250.00,20L);
		
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
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		repository.deleteAll();
	}
	
	/**
	 * Test if repository is persisting all the products given by a list.
	 */
	@Test
	public void testFindAll() {
		List<Product> savedProducts = repository.findAll();
		
		assertEquals(9, savedProducts.size());
	}
	
	/**
	 * Test if repository is filtering by ID.
	 */
	@Test
	public void testFilterById() {
		Product prod = repository.findById("7729uu");
		
		assertNotNull("7729uu", prod.getId());
	}
	
	/**
	 * Test if repository is filtering by title.
	 */
	@Test
	public void testFilterByTitle() {
		List<Product> prods = repository.findByTitle("XBOX One");
		
		assertEquals("7898100848355", prods.get(0).getEan());
		assertEquals(3, prods.size());
	}
	
	/**
	 * Test if repository is filtering by EAN.
	 */
	@Test
	public void testFilterByEan() {
		List<Product> prods = repository.findByEan("7898100848356");
		
		assertEquals("Sony Playstation 4", prods.get(0).getTitle());
		assertEquals(3, prods.size());
	}
	
	/**
	 * Test if repository is filtering by Brand.
	 */
	@Test
	public void testFilterByBrand() {
		List<Product> prods = repository.findByBrand("nikana");
		
		assertEquals("nikana", prods.get(0).getBrand());
		assertEquals("nikana", prods.get(1).getBrand());
		assertEquals("nikana", prods.get(2).getBrand());
		assertEquals(3, prods.size());
	}
	
	/**
	 * Test if repository is filtering by Price.
	 */
	@Test
	public void testFilterByPrice() {
		List<Product> prods = repository.findByPrice(250.00);
		
		assertEquals(new Double(250.00), prods.get(0).getPrice());
		assertEquals(new Double(250.00), prods.get(1).getPrice());
		assertEquals(2, prods.size());
	}
	
	/**
	 * Test if repository is filtering by Stock.
	 */
	@Test
	public void testFilterByStock() {
		List<Product> prods = repository.findByStock(0L);
		
		assertEquals(new Long(0), prods.get(0).getStock());
		assertEquals(new Long(0), prods.get(1).getStock());
		assertEquals(2, prods.size());
	}

}
