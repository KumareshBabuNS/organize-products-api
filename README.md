# Organize Products API
This API organizes products given in JSON format according to filter order specified as parameter.

## Local Endpoint
To run the application on a local environment, download jar file [here](organize-products-api-1.0.jar) and execute the command below:

```
cd \<folder-with-the-jar-file>
java -jar organize-products-api-1.0.jar
```

* **Make sure port 8080 is not in use.**

Access the endpoint through this URL:

`http://localhost:8080/organize-products-api/v1/products/organize`

**Method**

`POST`

**Params**

* `filter`: Filter to apply to the given list. The format must be field:value. <br>
   Example: `filter=ean:7898100848355`
   
* `order_by`: Order to apply to the given list. The format must be field:asc|desc. <br>
   Example: `order_by=price:asc`
   
* `body`: Body information containing an unordered list of products in JSON format. <br>
   Example:
   ```json
	[
	  {
	    "id": "123",
	    "ean": "1111111111111",
	    "title": "Product 1",
	    "brand": "brand 1",
	    "price": 50.10,
	    "stock": 10
	  },
	  {
	    "id": "456",
	    "ean": "2222222222222",
	    "title": "Product 2",
	    "brand": "brand 2",
	    "price": 100.50,
	    "stock": 100
	  }
	]
	```

**Response Status**

* `200`: List organized successfully. <br>
   Return example:
   ```json
	{
	 "data":[
	  {
	    "id": "123",
	    "ean": "1111111111111",
	    "title": "Product 1",
	    "brand": "brand 1",
	    "price": 50.10,
	    "stock": 10
	  },
	  {
	    "id": "456",
	    "ean": "2222222222222",
	    "title": "Product 2",
	    "brand": "brand 2",
	    "price": 100.50,
	    "stock": 100
	  }
	 ]
	}
	```
* `400`: Param filter and/or order_by are invalid.

## Heroku Endpoint
The API is available on [Heroku Cloud](https://ancient-gorge-51255.herokuapp.com/organize-products-api/v1/products/organize) for tests purpose.

## Note about Integration Tests
Integration tests requires that resource is available on `http://localhost:8080/organize-products-api/v1/products/organize`.
Make sure the application is running before execute JUnit tests.