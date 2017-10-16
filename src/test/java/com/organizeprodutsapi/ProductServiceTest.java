package com.organizeprodutsapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.organizeprodutsapi.dto.GroupResult;
import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	private List<Product> products = new ArrayList<Product>();

	@Autowired
	private ProductService service;

	@Before
	public void setUp() throws Exception {
		products = ProductFactory.createList();
	}

	/**
	 * Clear repository after tests.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		service.deleteAll();
	}

	/**
	 * Test organize behavior with no filter and no order. This behavior must result
	 * in a default order and no filter.
	 */
	@Test
	public void testServiceOrganizeNoFilterNoOrder() {
		List<GroupResult> organizedProducts = service.organize(products, null, null);

		// Validate products of the first group
		assertEquals("Controle XBOX One", organizedProducts.get(0).getDescription());
		List<Product> firstGroupProducts = organizedProducts.get(0).getItems();
		assertEquals("789", firstGroupProducts.get(0).getId());
		assertEquals("u7044", firstGroupProducts.get(1).getId());
		assertEquals("7730uu", firstGroupProducts.get(2).getId());
		assertEquals(3, firstGroupProducts.size());

		// Validate products of the last group
		assertEquals("trek", organizedProducts.get(5).getDescription());
		List<Product> lastGroupProducts = organizedProducts.get(5).getItems();
		assertEquals("7729uu", lastGroupProducts.get(0).getId());
		assertEquals("7728uu", lastGroupProducts.get(1).getId());
		assertEquals("7730uu", lastGroupProducts.get(2).getId());
		assertEquals(3, lastGroupProducts.size());

		// Validate group order
		assertEquals("Controle XBOX One", organizedProducts.get(0).getDescription());
		assertEquals("Sony Playstation", organizedProducts.get(1).getDescription());
		assertEquals("XBOX One", organizedProducts.get(2).getDescription());
		assertEquals("nikana", organizedProducts.get(3).getDescription());
		assertEquals("redav", organizedProducts.get(4).getDescription());
		assertEquals("trek", organizedProducts.get(5).getDescription());
		assertEquals(6, organizedProducts.size());
	}

	/**
	 * Test organize behavior with no filter.
	 */
	@Test
	public void testServiceOrganizeNoFilter() {
		List<GroupResult> organizedProducts = service.organize(products, null, "price:asc");

		// Validate products of the first group
		assertEquals("Controle XBOX One", organizedProducts.get(0).getDescription());
		List<Product> firstGroupProducts = organizedProducts.get(0).getItems();
		assertEquals("u7044", firstGroupProducts.get(0).getId());
		assertEquals("789", firstGroupProducts.get(1).getId());
		assertEquals("7730uu", firstGroupProducts.get(2).getId());
		assertEquals(3, firstGroupProducts.size());

		// Validate products of the last group
		assertEquals("trek", organizedProducts.get(5).getDescription());
		List<Product> lastGroupProducts = organizedProducts.get(5).getItems();
		assertEquals("7730uu", lastGroupProducts.get(0).getId());
		assertEquals("7728uu", lastGroupProducts.get(1).getId());
		assertEquals("7729uu", lastGroupProducts.get(2).getId());
		assertEquals(3, lastGroupProducts.size());

		// Validate group order
		assertEquals("Controle XBOX One", organizedProducts.get(0).getDescription());
		assertEquals("Sony Playstation", organizedProducts.get(1).getDescription());
		assertEquals("XBOX One", organizedProducts.get(2).getDescription());
		assertEquals("nikana", organizedProducts.get(3).getDescription());
		assertEquals("redav", organizedProducts.get(4).getDescription());
		assertEquals("trek", organizedProducts.get(5).getDescription());
		assertEquals(6, organizedProducts.size());

	}

	/**
	 * Test organize behavior with no order.
	 */
	@Test
	public void testServiceOrganizeNoOrder() {
		List<GroupResult> organizedProducts = service.organize(products, "title:Controle XBOX One", null);

		// Validate products of the first group
		assertEquals("Controle XBOX One", organizedProducts.get(0).getDescription());
		List<Product> firstGroupProducts = organizedProducts.get(0).getItems();
		assertEquals("789", firstGroupProducts.get(0).getId());
		assertEquals("u7044", firstGroupProducts.get(1).getId());
		assertEquals("7730uu", firstGroupProducts.get(2).getId());
		assertEquals(3, firstGroupProducts.size());

		// Validate products of the last group
		assertEquals("trek", organizedProducts.get(3).getDescription());
		List<Product> lastGroupProducts = organizedProducts.get(3).getItems();
		assertEquals("7730uu", lastGroupProducts.get(0).getId());
		assertEquals(1, lastGroupProducts.size());

		// Validate group order
		assertEquals("Controle XBOX One", organizedProducts.get(0).getDescription());
		assertEquals("nikana", organizedProducts.get(1).getDescription());
		assertEquals("redav", organizedProducts.get(2).getDescription());
		assertEquals("trek", organizedProducts.get(3).getDescription());
		assertEquals(4, organizedProducts.size());
	}

	/**
	 * Test organize behavior with filter and order.
	 */
	@Test
	public void testServiceOrganizeWithFilterAndOrder() {
		List<GroupResult> organizedProducts = service.organize(products, "title:XBOX One", "brand:desc");

		// Validate products of the first group
		assertEquals("XBOX One", organizedProducts.get(0).getDescription());
		List<Product> firstGroupProducts = organizedProducts.get(0).getItems();
		assertEquals("7728uu", firstGroupProducts.get(0).getId());
		assertEquals("u7042", firstGroupProducts.get(1).getId());
		assertEquals("123", firstGroupProducts.get(2).getId());
		assertEquals(3, firstGroupProducts.size());

		// Validate products of the last group
		assertEquals("trek", organizedProducts.get(3).getDescription());
		List<Product> lastGroupProducts = organizedProducts.get(3).getItems();
		assertEquals("7728uu", lastGroupProducts.get(0).getId());
		assertEquals(1, lastGroupProducts.size());

		// Validate group order
		assertEquals("XBOX One", organizedProducts.get(0).getDescription());
		assertEquals("nikana", organizedProducts.get(1).getDescription());
		assertEquals("redav", organizedProducts.get(2).getDescription());
		assertEquals("trek", organizedProducts.get(3).getDescription());
		assertEquals(4, organizedProducts.size());
	}

	/**
	 * Test organize behavior with invalid order field.
	 */
	@Test(expected = RuntimeException.class)
	public void testServiceOrganizerWithInvalidOrderField() {
		service.organize(products, "stock:0", "abc:asc");
	}

	/**
	 * Test organize behavior with invalid filter field.
	 */
	@Test(expected = RuntimeException.class)
	public void testServiceOrganizerWithInvalidFilterField() {
		service.organize(products, "abc:0", "price:asc");
	}
	
	/**
	 * Test save product.
	 */
	@Test
	public void testServiceSaveProduct() {
		Product product = new Product("zzz", "7898100848355", "XBOX One", "nikana", 1300.00, 30L);
		Product savedProduct = service.save(product);
		
		assertEquals(savedProduct.getId(), product.getId());
	}
	
	/**
	 * Test find product by id.
	 */
	@Test
	public void testServiceFindProductById() {
		Product product = new Product("zzz", "7898100848355", "XBOX One", "nikana", 1300.00, 30L);
		service.save(product);
		Product savedProduct = service.findById(product.getId());
		
		assertEquals(savedProduct.getId(), product.getId());
		
		service.deleteAll();
	}
	
	/**
	 * Test delete all products.
	 */
	@Test
	public void testServiceDeleteAllProducts() {
		Product product = new Product("zzz", "7898100848355", "XBOX One", "nikana", 1300.00, 30L);
		Product savedProduct = service.save(product);
		
		assertEquals(savedProduct.getId(), product.getId());
		
		service.deleteAll();
		
		savedProduct = service.findById(savedProduct.getId());
		
		assertNull(savedProduct);
	}
	
}
