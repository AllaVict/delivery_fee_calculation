# delivery_fee_calculation
# Core functionalities.
This web application is a RESTful API that allows calculating the delivery fee for food couriers according to input parameters from REST interface requests, weather data from the database, and business rules. This application periodically imports weather data, which is in XML format, from the weather portal of the Estonian Environment Agency and saves it in the database.
(https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php).

# Core technologies.
Java · Spring Framework · Hebirnate · PostgresSQL · GitHub · JUnit · Gradle · Swagger ·  Flyway · SLF4J · Webflux · Jakarta.xml 

# Core modules to implement:

1.	Scheduled service for importing weather data. This service periodically (every hour) imports weather data from the weather portal of the Estonian Environment Agency and saves it in the database for some stations: Tallinn, Tartu, Pärnu. URL to get data:
URL to get data:  https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php

2.	REST interface functionality to calculate delivery fee. A delivery fee has to be calculated according to input parameters from REST interface requests, weather data from the database, and business rules. The total delivery fee consists of a regional base fee for specific vehicle types and extra fees for some weather conditions:

Total delivery fee = BF + ATEF + WSEF + WPEF, 
Where: BF - base fee; 
ATEF - Extra fee based on air temperature; 
WSEF - Extra fee based on wind speed; 
WPEF - Extra fee based on weather phenomenon; 

3.	Database for storing and manipulating data. The application has module to store and manipulate data (base fees, extra fees and weather conditions) in the database. 

4.	REST interface and service for managing base fees and extra fees (CRUD). REST interface enables to request for base fees and extra fees and enables be managed them (CRUD).

# Core requests:

1.	Calculate a Delivery fee:  GET    http://localhost:8080/v1.0/fee
     
     {
     "city": "TALLINN",
     "vehicleType": " BIKE"
     }
2.	Archive a BaseFee by its id: PUT    http://localhost:8080/v1.0/base_fee/1
3.	Retrieves a BaseFee by its id: GET    http://localhost:8080/v1.0/ base_fee/1
4.	Retrieves all BaseFees: GET    http://localhost:8080/v1.0/ base_fee
5.	Deletes  a BaseFee by its id: Delete    http://localhost:8080/ base_fee
6.	Create a BaseFee with a given request body:  POST http://localhost:8080/v1.0/ base_fee
    
    {
     "name": "wind speed",
     "vehicleType": "BIKE",
     "extraFee": 2.0,
     "lowerLimit": 15.0,
     "upperLimit": 25.0,
     "weatherPhenomenon": "wind speed",
     "isForbidden": "false"
     }

7.	Archive an ExtraFee by its id: PUT    http://localhost:8080/v1.0/extra_fee/1
8.	Retrieves an ExtraFee by its id:  GET    http://localhost:8080/v1.0/extra_fee/1
9.	Retrieves all ExtraFees: GET    http://localhost:8080/v1.0/extra_fee
10.	Deletes  an ExtraFee by its id:  Delete    http://localhost:8080/extra_fee
11.	Create an ExtraFee with a given body: POST    http://localhost:8080/v1.0/extra_fee
   
    {
     "city": "TALLINN",
     "vehicleType": "SCOOTER",
     "fee": 3.0
     }
