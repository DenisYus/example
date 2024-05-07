package com.example.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PriceAnalyzer {

    private final TicketValidation ticketValidation;
    private final AverageCalculator averageCalculator;

    public PriceAnalyzer(TicketValidation ticketValidation, AverageCalculator averageCalculator) {

        this.ticketValidation = ticketValidation;
        this.averageCalculator = averageCalculator;
    }

    public void analyzePrices(List<Map<String, Object>> tickets) throws IOException {


        if (!ticketValidation.hasValidTickets(tickets)) {
            throw new NoValidTicketsException("No valid tickets found for analysis.");
        }

        List<Double> validPrices = tickets.stream()
                .filter(ticketValidation::isValidTicket)
                .map(ticket -> ((Number) ticket.get("price")).doubleValue())
                .collect(Collectors.toList());


        double averagePrice = averageCalculator.calculateAverage(validPrices);
        double medianPrice = averageCalculator.calculateMedian(validPrices);
        double priceDifference = Math.abs(averagePrice - medianPrice);

        System.out.println("Средняя цена: " + averagePrice);
        System.out.println("Медиана цен: " + medianPrice);
        System.out.println("Отличия цены: " + priceDifference);

    }
}
