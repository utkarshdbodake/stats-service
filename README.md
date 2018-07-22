# Stats Service

This service used to provide functionality around Statistics calculation. It contains restful API's for generating statistics and saving transactions.

Client app github link: https://github.com/utkarshdbodake/stats_app

-----

# Approach
### Flow 1. Add transaction
- If transaction timestamp is not within 60 seconds range, then return with HTTP status code 204.
- If transaction timestamp is within 60 seconds, then consider this transaction for calculation of statistics. Go to Flow 2.

### Flow 2. Update statistics
- We use `ConcurrentHashMap` to store resultant statistics for a given second.
- Its structure is as follows:  `{ second: resulant_stats_for_this_second }`
- Whenever there is a valid transaction we check if compute the stats and store it in above `ConcurrentHashMap`
- Calculate resultant statistics and then returns.

### Flow 3. Delete stale statistics
- This is a scheduled job which runs every second.
- This cleanses the stale stats entries from `ConcurrentHashMap` and then recomputes the resultant in-memory statistics.

----
# Entities
### Transaction Entity
```
- Represents single transaction entity.
{
	"amount": 12.3,
	"timestamp": 1478192204000
}
Where:
- amount: transaction amount
- timestamp: transaction time in epoch in millis in UTC time zone (this is not current
timestamp)
```

### Statistics Entity
```
- Represents statistics entity.
{
	"sum": 1000,
	"avg": 100,
	"max": 200,
	"min": 50,
	"count": 10
}
Where:
- sum: double specifying the total sum of transaction value in the last 60 seconds
- avg: double specifying the average amount of transaction value in the last 60
seconds
- max: double specifying single highest transaction value in the last 60 seconds
- min: double specifying single lowest transaction value in the last 60 seconds
- count: long specifying the total number of transactions happened in the last 60
seconds
```

# APIs
### Transaction
- Structure: ```POST /transactions```
- Input payload:
    ```
    {
		"amount": 12.3,
		"timestamp": 1478192204000
	}
    ```
- Example using cURL request:
    ```
	  curl -X POST \
	  http://localhost:8080/transactions \
	  -H 'Content-Type: application/json' \
	  -d '{
		"amount": "12",
		"timestamp": "1532236533000"
	   }'
    ```
    ```
    Success response
    HTTP status code: 201 OK - in case of success
    HTTP status code: 204 OK - if transaction is older than 60 seconds
  
    ```

    ```
    Failure responses
    
    Status code: 400
    Response payload (incase mandatory input payload is missing):
    {
	    "timestamp": "<current timestamp>",
	    "status": 400,
	    "error": "Bad Request",
	    "message": "Required request body is missing...",
	    "path": "/transactions"
	}
    ```

### Statistics
- Structure: ```GET /statistics```
- Example using cURL request:
    ```
	  curl -X GET http://localhost:8080/statistics
    ```
    ```
    Success response
    HTTP status code: 200 OK - in case of success
	Response payload:
	{
	    "sum": 50,
	    "avg": 25,
	    "max": 20,
	    "min": 5,
	    "count": 5
	}
    ```
# Test cases

- Unit Test cases have been added for each module to check the functionality at the grass root level.

# Environment Setup
Install the following:
 - Java JDK 1.8
 - Apache Maven 3.3+
 - Git

## Build
* Clone this repository: `git@github.com:utkarshdbodake/stats-service.git`
* Change directory to: `cd stats-service`

### JAR
Build project: `mvn clean install`

### Running of application web server locally
1. Run ``mvn spring-boot:run`` which will start the application Tomcat web server listening on port 8080.
2. All API's will be accessible with Base URL `http://localhost:8080/`.


### Running Test
Run tests: `mvn test`

### Artifacts
1. Executable jar file: `.../stats-service/target/stats-service-${version}-SNAPSHOT.jar`

-----
# Known Limitations
 - Current implementation uses in-memory data store; hence there is no persistence. So on server reboot, all the data would be lost.
