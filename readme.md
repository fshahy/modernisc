# Demo Project for ModernISC
This project has two main components:
* ACH: process detected files and stores them in a database, then sends the lines to the *Core* component. By default this components listens on port *8080*.
* Core: accepts incoming lines and stores them in a database, then for every line it returns an *ID* to the *ach* component. By default this components listens on port *9090*.

*Note:* These two projects have no GUI, they just expose some REST services.

## Notes
* Before using this project you need to install a **MySQL** server. Do not forget to update the database settings in *application.properties* file.

* The default username is **root**, with password of **root**.

* Create a database named *ach*.

* Create a database named *core*.

* The **ACH** projects watches a folder for new files. After it detects a new file, it is read line by line and every single line is treated as an entity (maybe a payment order etc.)

* To specify which folder to watch you need to set the property **misc.watch.folder** in *custom.properties* file. For example I have set this to */home/farid/tmp* folder.

* If you want you can set the property **misc.core.url** in the same file. If you use the default settings in the **Core** project you do not need to change this. The default value for this property is *http://localhost:9090/line*.

* In this demo project a **Line** is an abstract concept. I assume it to be an string of characters. There is a file named **sample_data.txt** which contains some dummy data to start with.

* Database schemas are defined in a file named **schema.sql** in the *resources* folder of each project.

## End-points
* ACH components has the following end-points:
    - http://localhost:8080/file
    - http://localhost:8080/file/{fileId}
    - http://localhost:8080/file/{fileId}/lines
    - http://localhost:8080/file/{fileId}/lines/{lineId}

* Core component has the following end-points:
    - http://localhost:9090/line
    - http://localhost:9090/line/{lineId}
    
## Using Postman: 
For your convenience a *Postman* collection file is available for testing end-points. Please import the file named **ModernISC.postman_collection.json** in your *Postman* and start using it.

## Tests
There are some unit test for these two projects. They are by no means a complete coverage of all end-points and services. You can run tests by entering the below command in a terminal:

for ACH project:

```
cd ach
mvn test
```

for Core project

```
cd core
mvn test
```

## Running
After finishing database and watch folder settings you can the project by entering this command in a terminal:

for ACH project:

```
cd ach
mvn spring-boot:run
```

for Core project:

```
cd core
mvn spring-boot:run
```
