package com.mycompany.app;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import org.json.JSONObject;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class App {

    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092";
        String topic = "alpha-vantage-data"; // Use the same topic as the producer
        String groupId = "alpha-vantage-consumer-group"; // Choose a suitable group ID
        ArrayList<Double> bitcoinRates = new ArrayList<>();
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Arrays.asList(topic)); // Subscribe to the same topic as the producer

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    //System.out.println(record.topic() + ", " + record.key() + ", " + record.value());
                    JSONObject jsonobj = new JSONObject(record.value());
                    //Reading the values only if the response is not null
                    if(jsonobj.has("Realtime Currency Exchange Rate")) {
                    	String coinName = jsonobj.getJSONObject("Realtime Currency Exchange Rate").getString("2. From_Currency Name");
                    	double exchangeRate = Double.parseDouble(jsonobj.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate"));
                    System.out.println(coinName+" to USD Exhange rate is "+exchangeRate);
                    if(coinName.equalsIgnoreCase("Bitcoin")) {
                    	bitcoinRates.add(exchangeRate);
   
                    double sum=0;
                    for(double i : bitcoinRates) {
                    	sum+=i;
                    }
                    System.out.println("Average exchange rate of Bitcoin is "+ (sum/bitcoinRates.size()));
                    }
                    }
                }
            }
        }
    }
}
