# batch-processing-ms-v2 #
Spring batch data loading *mysql(develop)* is the implementation that the project contains.


## Dependencies needed ##
- Spring Batch
- Lombok
- Spring Data JPA
- mySQL connector
- Spring Batch Test

## Steps to load data ##
- Build the jar file 
```bash
mvn clean install
```

- Execute the jar file
```bash
java -jar spring-batch-ms-0.0.1-SNAPSHOT.jar
```
## Lessons Learned ##
As of spring **3.X** we don't have to enable *@EnableBatchProcessing* or any logic to exit the main function. Everything is taken care by spring automatically.


## 🚀 Authors ##
Suhas H C
