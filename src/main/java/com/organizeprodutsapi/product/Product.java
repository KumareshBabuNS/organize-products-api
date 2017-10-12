package com.organizeprodutsapi.product;

/**
 * Product type carries product information. 
 * @author Danilo
 *
 */
public class Product {
	
	private String id;
	private String ean;
	private String title;
	private String brand;
	private Double price;
	private Long stock;
	
	public Product() {
		super();
	}

	public Product(String id, String ean, String title, String brand, Double price, Long stock) {
		super();
		this.id = id;
		this.ean = ean;
		this.title = title;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}

}
