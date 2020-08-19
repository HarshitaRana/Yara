# RESTful API for Managing Agricultural Fields

A RESTful API based on following frameworks & technologies used for managing agricultural fields and weather history

### Stack
- Java
- Spring Boot 
- Maven
- JUnit
- Log4j
- Docker

### Building and Running the application via Maven

To build the application and run in-built tests :

Change the directory first:
##### cd Yara-Agro-docker

then execute,

##### mvn clean install

To run the application, execute:

##### java -jar target/YaraAgroAPI-0.0.1-SNAPSHOT.jar
You can now hit the application APIs at http://localhost:8080/

### Running the application through docker image

Pull the docker image using:

##### docker pull harshitarana/javaapps:1.0

Execute the below command:

##### docker run -d -p 8080:8080 --name yara-agro-cont harshitarana/javaapps:1.0

You can now hit the application APIs at http://localhost:8080/

After running the application, you can execute the following command to stop and remove the container

##### docker rm --force yara-agro-cont


### Application End points:

##### GET - http://localhost:8080/api/fields

Will fetch information about all fields

##### GET - http://localhost:8080/api/fields/{fieldId}

Will fetch information about a given field

##### POST - http://localhost:8080/api/fields

Will create a new field. 
Response status : 201
Sample Input :

           
    {
    "name":"MyPolygon",
    "geo_json":{
    "type":"Feature",
    "properties":{
    
    },
      "geometry":{
         "type":"Polygon",
         "coordinates":[
            [
               [-121.1958,37.6683],
               [-121.1779,37.6687],
               [-121.1773,37.6792],
               [-121.1958,37.6792],
               [-121.1958,37.6683]
            ]
         ]
      }
     }
    }
   
 

##### PUT - http://localhost:8080/fields/{fieldID}

Update a given field. 
Response Status : 200
Sample input:

{
   "name":"Polygon New Name",
}

##### DELETE - http://localhost:8080/fields/{fieldID}
Delete a given field.
Response Status : 204

##### GET - http://localhost:8080/api/fields/{fieldId}/weather

Will fetch weather information about a given field


### Assumptions: 

1. Authentication is not required for any APIs
2. Validation of any sort regarding the field boundary is not required
3. Because agromonitoring API, https://agromonitoring.com/api/history-weather , for weather history is paid, it is ok to use sample API, https://samples.openweathermap.org/
4. With sample API for weather, we are ok to display all the records of sample data with limited field-values i.e "timestamp", "temperature", "humidity" and "temperatureMax".
