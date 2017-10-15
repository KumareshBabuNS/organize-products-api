# Organize Products API
This API organizes products given in JSON format according to filter and/or order specified as parameter.

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

**Headers**

`Content-type:application/json`<br>
`Accept:application/json`

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
			"ean": "7898100848355",
			"title": "XBOX One",
			"brand": "nikana",
			"price": 1300.00,
			"stock": 30
		},
		{
			"id": "456",
			"ean": "7898100848356",
			"title": "Sony Playstation",
			"brand": "nikana",
			"price": 1500.00,
			"stock": 50
		},
		{
			"id": "789",
			"ean": "7898100848357",
			"title": "Controle XBOX One",
			"brand": "nikana",
			"price": 250.00,
			"stock": 45
		},
		{
			"id": "7728uu",
			"ean": "7898100848355",
			"title": "XBOX One",
			"brand": "trek",
			"price": 1300.00,
			"stock": 30
		},
		{
			"id": "7729uu",
			"ean": "7898100848356",
			"title": "Sony Playstation",
			"brand": "trek",
			"price": 1500.00,
			"stock": 50
		},
		{
			"id": "7730uu",
			"ean": "7898100848357",
			"title": "Controle XBOX One",
			"brand": "trek",
			"price": 260.00,
			"stock": 45
		},
		{
			"id": "u7042",
			"ean": "7898100848355",
			"title": "XBOX One",
			"brand": "redav",
			"price": 1200.00,
			"stock": 4
		},
		{
			"id": "u7043",
			"ean": "7898100848356",
			"title": "Sony Playstation",
			"brand": "redav",
			"price": 1400.00,
			"stock": 0
		},
		{
			"id": "u7044",
			"ean": "7898100848357",
			"title": "Controle XBOX One",
			"brand": "redav",
			"price": 220.00,
			"stock": 20
		}
	]
	```

**Response Status**

* `200`: List organized successfully. <br>
   Return example:
   ```json
	{
	    "data": [
	        {
	            "description": "Controle XBOX One",
	            "items": [
	                {
	                    "id": "u7044",
	                    "ean": "7898100848357",
	                    "title": "Controle XBOX One",
	                    "brand": "redav",
	                    "price": 220,
	                    "stock": 20
	                },
	                {
	                    "id": "789",
	                    "ean": "7898100848357",
	                    "title": "Controle XBOX One",
	                    "brand": "nikana",
	                    "price": 250,
	                    "stock": 45
	                },
	                {
	                    "id": "7730uu",
	                    "ean": "7898100848357",
	                    "title": "Controle XBOX One",
	                    "brand": "trek",
	                    "price": 260,
	                    "stock": 45
	                }
	            ]
	        },
	        {
	            "description": "nikana",
	            "items": [
	                {
	                    "id": "789",
	                    "ean": "7898100848357",
	                    "title": "Controle XBOX One",
	                    "brand": "nikana",
	                    "price": 250,
	                    "stock": 45
	                }
	            ]
	        },
	        {
	            "description": "redav",
	            "items": [
	                {
	                    "id": "u7044",
	                    "ean": "7898100848357",
	                    "title": "Controle XBOX One",
	                    "brand": "redav",
	                    "price": 220,
	                    "stock": 20
	                }
	            ]
	        },
	        {
	            "description": "trek",
	            "items": [
	                {
	                    "id": "7730uu",
	                    "ean": "7898100848357",
	                    "title": "Controle XBOX One",
	                    "brand": "trek",
	                    "price": 260,
	                    "stock": 45
	                }
	            ]
	        }
	    ]
	}
	```
* `400`: Param filter and/or order_by are invalid.

## Heroku Endpoint
The API is available on [Heroku Cloud](https://ancient-gorge-51255.herokuapp.com/organize-products-api/v1/products/organize) for tests purpose.

## Notes About Integration Tests
Integration tests requires that endpoint is available on `http://localhost:8080/organize-products-api/v1/products/organize`.
Make sure the application is running before execute JUnit tests.