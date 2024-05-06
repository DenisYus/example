package com.example.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
        String filePath = "E:\\example\\tickets.json";
        DataReader jsonFileReader = new JsonFileReader(filePath);
        TicketAnalyzer flightAnalyzer = new FlightAnalyzer();
        AverageCalculator averageCalculator = new AverageCalculator();
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(jsonFileReader, new TicketValidator(), new AverageCalculator());

        try {
            Map<String, List<Map<String, Object>>> data = jsonFileReader.readData();
            List<Map<String, Object>> tickets = data.get("tickets");
            flightAnalyzer.analyzeFlights(tickets);

            Map<String, LocalTime> minFlightTimes = flightAnalyzer.getMinFlightTimes();
            System.out.println("Минимальное время полета между Владивостоком и Тель-Авивом для каждого авиаперевозчика:");
            for (Map.Entry<String, LocalTime> entry : minFlightTimes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue().format(DateTimeFormatter.ofPattern("H:mm")));
            }
            priceAnalyzer.analyzePrices();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
