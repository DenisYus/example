package com.example.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class ExampleApplication {
    public static void main(String[] args) throws DataReadException {
        SpringApplication.run(ExampleApplication.class, args);
        String filePath = "tickets.json";
        ClassLoader classLoader = ExampleApplication.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        DataReader jsonFileReader = new JsonFileReader(inputStream);
        TicketAnalyzer flightAnalyzer = new FlightAnalyzer();
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(new TicketValidator(), new AverageCalculator());

        try {
            Map<String, List<Map<String, Object>>> data = jsonFileReader.readData();
            List<Map<String, Object>> tickets = data.get("tickets");
            flightAnalyzer.analyzeFlights(tickets);
            priceAnalyzer.analyzePrices(tickets);
        } catch (IOException e) {
            throw new DataReadException("Error reading data from file", e);
        }

    }
}
