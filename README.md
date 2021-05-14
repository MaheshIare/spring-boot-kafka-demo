# spring-boot-kafka-demo

This is a simple spring application developed using Spring boot to demonstrate the integration of **[Apache Kafka](https://kafka.apache.org/)** which internally uses **[Zoo Keeper](https://zookeeper.apache.org/)** for managing different Kafka instances as a cluster. 

## Key concepts of Apache Kafka

**Topic**: Topic is a unique representation or category to which producer publishes a stream of data. A topic will be subscribed by zero or multiple consumers for receiving data.

**Producer**: Producer is responsible for sending data to one or more topics and assigning data to partitions within the topic.

**Consumer**: Consumer is responsible for consuming data from one or more topics when the producer sends the data to topics.

**Apache Kafka has a built-in system to resend the data if there is any failure while processing the data, with this inbuilt mechanism it is highly fault-tolerant.**

## Build Instructions

```bash
mvn clean install
```

## Prerequisites
**This application requires Kafka and Zookeeper setup.**
You can download Kafka setup from here **[Kafka-2.8.0-src](https://kafka.apache.org/downloads)**. Once downloaded, extract into your local machine. Navigate to the extracted folder and run the below commands using windows powershell/terminal if you are using Mac.

To run the zookeeper server

```bash
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties (Windows)

.\bin\zookeeper-server-start.sh  .\config\zookeeper.properties (linux)

```
To run the kafka instance

```bash
.\bin\windows\kafka-server-start.bat .\config\server.properties (Windows)

.\bin\kafka-server-start.sh .\config\server.properties (linux)
```

You might encounter with below issues while running the above commands:

1. If any of the above command results in this error **Classpath empty, please do a 'gradle jarAll'**, then you need to do gradle build before running the Zookeeper/Kafka servers. You can download latest gradle distribution from here **[Gradle](https://gradle.org/releases/)**, once downloaded run the below command to generate the classpath for kafka distribution downloaded above.

```bash
gradle clean jar
```
2. If any of the above command results in this error **The input line is too long**, then you can follow the steps mentioned here **[Issue starting Kafka server](https://stackoverflow.com/questions/48834927/the-input-line-is-too-long-when-starting-kafka)**. And this issue can occur only in Windows machine because of the limitation of characters in cmd/powershell appplications.

## Dependency information

Add below dependencies to your pom.xml

```java
	<!-- Spring kafka dependency -->
	<dependency>
		<groupId>org.springframework.kafka</groupId>
		<artifactId>spring-kafka</artifactId>
	</dependency>
```
Spring boot eliminates all the boiler template code of writing configuration classes for Kafka Consumers/Producers just by adding below configuration into your application.properties/application.yml file

```bash

spring.kafka.topic=<topic-name>
spring.kafka.topic.group.id=<consumer-group-id>

## kafka consumer config ##
spring.kafka.consumer.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=${spring.kafka.topic.group.id}
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
spring.kafka.consumer.properties.spring.deserializer.value.delegate.class: org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.value.default.type=com.java.techhub.kafka.demo.model.UserDetails

## kafka producer config ##
spring.kafka.producer.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

## Optional configuration ##
spring.kafka.producer.client-id=producer-${random.uuid}
spring.kafka.consumer.client-id=consumer-${random.uuid}

```

## Components of the demo code

``` java

MessagePublishController -> POST - /api/publish - To publish message to a topic with payload as user details object

MessagePublisher -> Service used for publishing messages to Kafka topic using KafkaTemplate

MessageConsumer -> Listener for consuming the messages from kafka topic, uses @KafkaListener annotation

@EnableKafka -> Annotation used for activating methods annotated with @KafkaListener annotation as message consumers

```

## Questions
If you have project related questions please create a ticket with your question here [Create Issue](https://github.com/MaheshIare/spring-boot-kafka-demo/issues)


## Author

**Mahesh Kumar Gutam**

* [Github](https://github.com/MaheshIare)

## Feedback
Please feel free to send me some feedback or questions!