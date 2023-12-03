package com.mycompany.app;import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule the producer to run every 1 minute
        scheduler.scheduleAtFixedRate(() -> {
            fetchDataAndSendToKafka("https://alpha-vantage.p.rapidapi.com/query?from_currency=BTC&function=CURRENCY_EXCHANGE_RATE&to_currency=USD");
            fetchDataAndSendToKafka("https://alpha-vantage.p.rapidapi.com/query?from_currency=ETH&function=CURRENCY_EXCHANGE_RATE&to_currency=USD");
            fetchDataAndSendToKafka("https://alpha-vantage.p.rapidapi.com/query?from_currency=DOGE&function=CURRENCY_EXCHANGE_RATE&to_currency=USD");
            fetchDataAndSendToKafka("https://alpha-vantage.p.rapidapi.com/query?from_currency=SOL&function=CURRENCY_EXCHANGE_RATE&to_currency=USD");
            fetchDataAndSendToKafka("https://alpha-vantage.p.rapidapi.com/query?from_currency=BNB&function=CURRENCY_EXCHANGE_RATE&to_currency=USD");
        }, 0, 1, TimeUnit.MINUTES);
    }

    private static void fetchDataAndSendToKafka(String apiUrl) {
        String bootstrapServers = "localhost:9092";
        String topic = "alpha-vantage-data";

        // Alpha Vantage API configuration
        String alphaVantageApiKey = "ba9c2bffdamsh06073bdde70c2e7p14d2f2jsnd701f6133cc7";

        // Kafka producer configuration
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        try {
            // Fetch data from Alpha Vantage API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("X-RapidAPI-Key", alphaVantageApiKey)
                    .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String alphaVantageData = response.body();

            // Send data to Kafka topic
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, alphaVantageData);
            producer.send(producerRecord);

            log.info("Data sent to Kafka topic: {}", topic);
        } catch (Exception e) {
            log.error("Error fetching or sending data: {}", e.getMessage());
        } finally {
            producer.flush();
            producer.close();
        }
    }
}
