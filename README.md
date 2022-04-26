##Bank demo api

###General info
This is a sample application that has some core banking functionalities:  
-Creating bank current account (multi-currency)  
-Creating transaction

Therefore are 2 REST API-s endpoints:  
-http://localhost:8080/api/v1/account/add  
-http://localhost:8080/api/v1/transaction/add

###Example for calling API-s
**Account API:**  
**Request:**  
```json
{  
"customer": "5",  
"country": "EE",  
"currency": ["EUR","SEK", "USD"]  
}  
```
**Response:**  
```json
{  
"id": 132,  
"customer": "5",  
"country": "EE",  
"balances": [
{  
"balance": 0,  
"currency": "EUR"  
},  
{  
"balance": 0,  
"currency": "SEK"  
},  
{  
"balance": 0,  
"currency": "USD"  
}
],  
"error": null  
}  
```
**Transaction API:**  
**Request:**  
```json
{  
"accountId": 123,  
"amount": 100,  
"currency": "USD",  
"direction" : "IN"  
}
```
**Response:**  
```json
{  
"id": 5,  
"amount": 100,  
"direction": "IN"  
"currency": "USD",  
"accountId": 0,  
"error": null  
}
```


###Technologies
-Java 17  
-SpringBoot  
-MyBatis  
-Gradle  
-Postgres  


###Setup
Run `gradle bootRun` to run the application.

Script for creating the database:   
`bank-demo-api-main\config\db_struct_bank.sql`

###Tests
There are created tests to create a few scenarios of API-s Controllers:  
`bank-demo-api-main\test `  
Run `gradle test` to run controller tests. 




