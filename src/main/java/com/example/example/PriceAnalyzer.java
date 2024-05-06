package com.example.example;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PriceAnalyzer {
    private final DataReader dataReader;
    private final TicketValidation ticketValidation;
    private AverageCalculator averageCalculator;

    public PriceAnalyzer(DataReader dataReader, TicketValidation ticketValidation, AverageCalculator averageCalculator) {
        this.dataReader = dataReader;
        this.ticketValidation = ticketValidation;
        this.averageCalculator = averageCalculator;
    }

    public void analyzePrices() throws IOException {
        try {
            Map<String, List<Map<String, Object>>> data = dataReader.readData();
            List<Map<String, Object>> tickets = data.get("tickets");
            if (!ticketValidation.hasValidTickets(tickets)) {
                throw new NoValidTicketsException("No valid tickets found for analysis.");
            }

            List<Double> validPrices = data.get("tickets").stream()
                    .filter(ticketValidation::isValidTicket)
                    .map(ticket -> ((Number) ticket.get("price")).doubleValue())
                    .collect(Collectors.toList());


            double averagePrice = averageCalculator.calculateAverage(validPrices);
            double medianPrice = averageCalculator.calculateMedian(validPrices);
            double priceDifference = Math.abs(averagePrice - medianPrice);

            System.out.println("Average price: " + averagePrice);
            System.out.println("Median price: " + medianPrice);
            System.out.println("Price difference: " + priceDifference);
        } catch (DataReadException e) {
            System.err.println("Error reading data: " + e.getMessage());
        } catch (NoValidTicketsException e) {
            System.out.println(e.getMessage());
        }
    }
}
