Local Run: first download local ddb jar fromhttps://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html 
and go to the directory where the jar is located and run the following command:
`java -Djava.library.path=/Users/charlesharnage/development/dynamodb_local_latest/DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`

Run locally service

`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

Clean, package, and then run service
`mvn clean package spring-boot:run -Dspring-boot.run.profiles=dev`

If package doesn't install new maven dependency run the following:

`mvn clean install -U`
