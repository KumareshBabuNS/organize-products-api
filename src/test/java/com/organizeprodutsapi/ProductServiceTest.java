package com.organizeprodutsapi;

import static org.junit.Assert.assertEquals;

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
import com.organizeprodutsapi.result.GroupResult;
import com.organizeprodutsapi.service.ProductService;

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

}
