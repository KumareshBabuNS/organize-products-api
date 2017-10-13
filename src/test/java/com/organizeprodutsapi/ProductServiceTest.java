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
import com.organizeprodutsapi.repositories.ProductRepository;
import com.organizeprodutsapi.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
	
	private List<Product> products = new ArrayList<Product>();
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;
	
	@Before
	public void setUp() throws Exception {
		Product product1 = new Product("123","7898100848355","XBOX One","nikana",1300.00,30L);
		Product product2 = new Product("456","7898100848356","Sony Playstation","nikana",1500.00,50L);
		Product product3 = new Product("789","7898100848357","Controle XBOX One","nikana",250.00,45L);
		
		Product product4 = new Product("7728uu","7898100848355","XBOX One","trek",1400.00,1L);
		Product product5 = new Product("7729uu","7898100848356","Sony Playstation","trek",1600.00,3L);
		Product product6 = new Product("7730uu","7898100848357","Controle XBOX One","trek",260.00,0L);
		
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
		String expectedQuery = "select prod from Product prod where brand = 'nikana' order by price desc"; 
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
	
	/**
	 * Test field validation
	 */
	@Test
	public void testServiceValidateFilterOrderField() {
		assertTrue(service.validateFilterOrderField("title"));
		assertFalse(service.validateFilterOrderField("seller"));
	}
	
	/**
	 * Test organize behavior with no filter and no order.
	 * This behavior must result in a default order and no filter.
	 */
	@Test
	public void testServiceOrganizeNoFilterNoOrder() {
		repository.deleteAll();
		
		List<Product> organizedProducts = service.organize(products, null, null);
		
		assertEquals("456", organizedProducts.get(0).getId());
		assertEquals("u7043", organizedProducts.get(8).getId());
		assertEquals(9, organizedProducts.size());
		
	}
	
	/**
	 * Test organize behavior with no filter.
	 */
	@Test
	public void testServiceOrganizeNoFilter() {
		repository.deleteAll();
		
		List<Product> organizedProducts = service.organize(products, null, "price:asc");
		
		assertEquals("u7044", organizedProducts.get(0).getId());
		assertEquals("7729uu", organizedProducts.get(8).getId());
		assertEquals(9, organizedProducts.size());
		
	}
	
	/**
	 * Test organize behavior with no order.
	 */
	@Test
	public void testServiceOrganizeNoOrder() {
		repository.deleteAll();
		
		List<Product> organizedProducts = service.organize(products, "title:XBOX One", null);
		
		assertEquals("123", organizedProducts.get(0).getId());
		assertEquals("7728uu", organizedProducts.get(2).getId());
		assertEquals(3, organizedProducts.size());		
	}
	
	/**
	 * Test organize behavior with filter and order.
	 */
	@Test
	public void testServiceOrganizeFilterAndOrder() {
		repository.deleteAll();
		
		List<Product> organizedProducts = service.organize(products, "ean:7898100848356", "brand:desc");
		
		assertEquals("7729uu", organizedProducts.get(0).getId());
		assertEquals("456", organizedProducts.get(2).getId());
		assertEquals(3, organizedProducts.size());		
	}

}
