package com.organizeprodutsapi.product;

import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Product carries product information. 
 * @author Danilo
 *
 */
@Entity
@Table(name="product")
public class Product {
	
	@Id
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

	@Override
	public String toString() {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(new Locale("pt","BR"));
		
		StringBuffer productForm = new StringBuffer();
		productForm.append("\nID: " + id + "\n");
		productForm.append("EAN: " + ean + "\n");
		productForm.append("Title: " + title + "\n");
		productForm.append("Brand: " + brand + "\n");
		productForm.append("Price: " + currencyFormatter.format(price) + "\n");
		productForm.append("Stock: " + numberFormatter.format(stock) + "\n\n");
		
		return productForm.toString();
	}
	
	

}
