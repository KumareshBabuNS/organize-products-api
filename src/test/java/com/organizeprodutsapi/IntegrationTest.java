package com.organizeprodutsapi;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.organizeprodutsapi.dto.GroupResult;
import com.organizeprodutsapi.jersey.JerseyClient;
import com.organizeprodutsapi.product.Product;
import com.organizeprodutsapi.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTest {

	private List<Product> products;

	@Autowired
	private ProductService service;
	
	private JerseyClient client = new JerseyClient();
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		products = ProductFactory.createList();

		for (Product product : products) {
			service.save(product);
		}
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
	 * Test invalid request filter param.
	 */
	@Test
	public void testIntegrationOrganizeInvalidFilterParams() throws Exception {
		expectedEx.expect(RuntimeException.class);
	    expectedEx.expectMessage("400: request failed!");
		
		client.organize(products, "aaa", null).getData();
	}
	
	/**
	 * Test invalid request order param.
	 */
	@Test
	public void testIntegrationOrganizeInvalidOrderParam() throws Exception{
		expectedEx.expect(RuntimeException.class);
	    expectedEx.expectMessage("400: request failed!");
		
		client.organize(products, null, "aaaa").getData();
	}

	/**
	 * Test organize behavior with no filter and no order. This behavior must result
	 * in a default order and no filter.
	 */
	@Test
	public void testIntegrationOrganizeNoFilterNoOrder() {
		List<GroupResult> organizedProducts = client.organize(products, null, null).getData();

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
	public void testIntegrationOrganizeNoFilter() {
		List<GroupResult> organizedProducts = client.organize(products, null, "price:asc").getData();

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
	public void testIntegrationOrganizeNoOrder() {
		List<GroupResult> organizedProducts = client.organize(products, "title:Controle XBOX One", null).getData();

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
	public void testIntegrationOrganizeWithFilterAndOrder() {
		List<GroupResult> organizedProducts = client.organize(products, "title:XBOX One", "brand:desc").getData();

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

}
