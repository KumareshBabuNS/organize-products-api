# organize-products-api
API to organize products.

## Local Endpoint
To execute the application on a local environment, execute the command below:

```
cd \<folder-with-the-jar-file>
java -jar organize-products-api-1.0.jar
```

* **Make sure port 8080 is not in use.**

Access the endpoint through [this URL](https://localhost:8080/organize-products-api/v1/products/organize).

## Heroku Endpoint
The API is available on [Heroku Cloud](https://ancient-gorge-51255.herokuapp.com/organize-products-api/v1/products/organize).

----

**API Docs**
----

#### BasePath

  `/organize-products-api`

#### Organize Resource
  
  `/v1/products/organize`

  `POST`
  
#### Parameters
<table>
    <tr>
        <td><strong>Name</strong></td>
        <td><strong>Type</strong></td>
        <td><strong>Description</strong></td>
        <td><strong>Example</strong></td>
    </tr>
    <tr>
        <td><code>filter</code></td>
        <td>string</td>
        <td>Filter to apply to the given list. The format must be field:value.</td>
        <td>ean:7898100848355</td>
    </tr>
    <tr>
        <td><code>order_by</code></td>
        <td>string</td>
        <td>Order to apply to the given list. The format must be field:asc|desc.</td>
        <td>price:asc</td>
    </tr>
    <tr>
        <td><code>body</code></td>
        <td>application/json</td>
        <td>List of unorganized products in JSON format.</td>
        <td>
            ```json
            [{
		"id": "123",
		"ean": "7898100848355",
		"title": "Cruzador espacial Nikana - 3000m - sem garantia",
		"brand": "nikana",
		"price": 820900.90,
		"stock": 1
	},
	{
		"id": "u7042",
		"ean": "7898054800492",
		"title": "Espada de Fótons Nikana Azul",
		"brand": "nikana",
		"price": 2199.90,
		"stock": 82
	},
	{
		"id": "bb2r3s0",
		"ean": "2059251400402",
		"title": "Corredor POD 3000hp Nikana",
		"brand": "nikana",
		"price": 17832.90,
		"stock": 8
	},
	{
		"id": "321",
		"ean": "7898100848355",
		"title": "Cruzador espacial Nikana - 3000m - sem garantia",
		"brand": "trek",
		"price": 790300.90,
		"stock": 0
	},
	{
		"id": "80092",
		"ean": "",
		"title": "Espada de Fótons REDAV Azul",
		"brand": "redav",
		"price": 1799.90,
		"stock": 0
	},
	{
		"id": "7728uu",
		"ean": "7898100848355",
		"title": "Cruzador espacial Ekul - 3000m - sem garantia",
		"brand": "ekul",
		"price": 1300000.00,
		"stock": 1
	}
]
          ```
        </td>
    </tr>
</table>

* **Success Response:**
  
  <_What should the status code be on success and is there any returned data? This is useful when people need to to know what their callbacks should expect!_>

  * **Code:** 200 <br />
    **Content:** `{ id : 12 }`
 
* **Error Response:**

  <_Most endpoints will have many ways they can fail. From unauthorized access, to wrongful parameters etc. All of those should be liste d here. It might seem repetitive, but it helps prevent assumptions from being made where they should be._>

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "Log in" }`

  OR

  * **Code:** 422 UNPROCESSABLE ENTRY <br />
    **Content:** `{ error : "Email Invalid" }`

* **Sample Call:**

  <_Just a sample call to your endpoint in a runnable format ($.ajax call or a curl request) - this makes life easier and more predictable._> 

* **Notes:**

  <_This is where all uncertainties, commentary, discussion etc. can go. I recommend timestamping and identifying oneself when leaving comments here._> 
