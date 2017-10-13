package com.organizeprodutsapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
	
	private List<Product> products = new ArrayList<Product>();
	
	@Autowired
	private ProductService service;
	
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
		
		for (Product product : products) {
			service.save(product);
		}
	}
	
	/**
	 * Test if service is persisting all the products given by a list.
	 */
	@Test
	public void testServiceFindAll() {
		List<Product> savedProducts = service.findAll();
		
		assertEquals(9, savedProducts.size());
	}
	
	/**
	 * Test if service can save and delete a product.
	 */
	@Test
	public void testServiceSaveAndDelete() {
		Product productToSave = new Product("111222333","7898100848355","XBOX One","redav",1200.00,4L);
		service.save(productToSave);
		Product savedProduct = service.findById("111222333");
		
		assertNotNull(savedProduct);
		
		service.delete("111222333");
		Product deletedProduct = service.findById("111222333");
		
		assertNull(deletedProduct);
	}
	
	/**
	 * Test query build with no filter
	 */
	@Test
	public void testServicePrepareQueryNoFilter() {
		String expectedQuery = "select prod from Product prod order by title asc"; 
		String obteinedQuery = service.prepareQuery(null, "title:asc");
		
		assertEquals(expectedQuery,obteinedQuery);
	}
	
	/**
	 * Test query build with no order
	 */
	@Test
	public void testServicePrepareQueryNoOrder() {
		String expectedQuery = "select prod from Product prod order by stock desc, price asc"; 
		String obteinedQuery = service.prepareQuery(null, null);
		
		assertEquals(expectedQuery,obteinedQuery);
	}
	
	/**
	 * Test query build with string filter and order
	 */
	@Test
	public void testServicePrepareQueryWithStringFilterAndOrder() {
		String expectedQuery = "select prod from Product prod where brand = \"nikana\" order by price desc"; 
		String obteinedQuery = service.prepareQuery("brand:nikana", "price:desc");
		
		assertEquals(expectedQuery,obteinedQuery);
	}
	
	/**
	 * Test query build with number filter and order
	 */
	@Test
	public void testServicePrepareQueryWithNumberFilterAndOrder() {
		String expectedQuery = "select prod from Product prod where stock = 0 order by brand asc"; 
		String obteinedQuery = service.prepareQuery("stock:0", "brand:asc");
		
		assertEquals(expectedQuery,obteinedQuery);
	}
	
	/**
	 * Test query build with invalid filter field
	 */
	@Test(expected=RuntimeException.class)
	public void testServicePrepareQueryWithInvalidFilterField() {
		service.prepareQuery("abc:0", "brand:asc");
	}
	
	/**
	 * Test query build with invalid order field
	 */
	@Test(expected=RuntimeException.class)
	public void testServicePrepareQueryWithInvalidOrderField() {
		service.prepareQuery("stock:0", "abc:asc");
	}
	
	@Test
	public void validateFilterOrderField() {
		assertTrue(service.validateFilterOrderField("title"));
		assertFalse(service.validateFilterOrderField("seller"));
	}

}
