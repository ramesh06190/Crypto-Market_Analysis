# Crypto-Market_Analysis

This repository contains two Java applications for processing financial data: a Kafka Data Pipeline for real-time cryptocurrency exchange rates and a Cryptocurrency Data Processing application for historical analysis.

## Prerequisites

- Java 8 or higher
- Apache Kafka
- OpenCSV library
- SLF4J Logger
- Alpha Vantage API Key (for the Kafka Data Pipeline)

## Setting Up the Environment

1. **Java Development Kit (JDK):** Install Java on your system. You can download it from [here](https://www.oracle.com/java/technologies/javase-downloads.html).

2. **Apache Kafka:** Install and start Apache Kafka on your local machine. You can find instructions [here](https://kafka.apache.org/quickstart).

3. **OpenCSV Library:** If not already included, make sure to include the OpenCSV library in your Java project. You can add the dependency in your `pom.xml` file for Maven projects:

    ```xml
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.5</version>
    </dependency>
    ```

4. **SLF4J Logger:** Ensure SLF4J is included in your project for logging. Add the following dependency for Maven:

    ```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.32</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.32</version>
    </dependency>
    ```

## Running the Kafka Data Pipeline

 1. Open the `kafka-sample-consumer-main` and `kafka-sample-producer-main` projects.

 2. Make sure you run Zookeeper
   - To Run Zookeeper ---> .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
 3. And also Kafka server
   - To Run Kafka Server --->  .\bin\windows\kafka-server-start.bat .\config\server.properties

 4. Update the Alpha Vantage API key in the `App.java` file:

    ```java
    String alphaVantageApiKey = "YOUR_API_KEY_HERE";
    ```

5. Run the producer and consumer components. The producer fetches real-time cryptocurrency exchange rates and sends them to the Kafka topic, while the consumer processes and logs the data.

## Running the Cryptocurrency Data Processing Application

1. Open the `CryptocurrencyDataProcessing` project.

2. Ensure that your CSV file is correctly formatted with headers and data.

3. Run the `App.java` file. The application performs various data processing tasks, including filtering, finding extremes, calculating averages, and data transformation.

## Additional Notes

- Ensure that the CSV file follows the expected structure for the Cryptocurrency Data Processing application.
- Integrate proper error handling and validation mechanisms based on the characteristics of the data.

Feel free to explore and extend the functionalities of these applications based on your needs.

Happy coding!
