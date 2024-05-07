package com.example.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightAnalyzer implements TicketAnalyzer {
    private final Map<String, LocalTime> minFlightTimes;
    private final TicketValidator ticketValidator;

    public FlightAnalyzer() {
        this.minFlightTimes = new HashMap<>();
        this.ticketValidator = new TicketValidator();
    }

    @Override
    public void analyzeFlights(List<Map<String, Object>> tickets) {
        for (Map<String, Object> ticket : tickets) {
            if (!ticketValidator.isValidTicket(ticket)) {
                continue;
            }

            String carrier = (String) ticket.get("carrier");
            LocalTime departureTime = LocalTime.parse((String) ticket.get("departure_time"), DateTimeFormatter.ofPattern("H:mm"));
            LocalTime arrivalTime = LocalTime.parse((String) ticket.get("arrival_time"), DateTimeFormatter.ofPattern("H:mm"));

            long durationMinutes = ChronoUnit.MINUTES.between(departureTime, arrivalTime);
            LocalTime duration = LocalTime.of((int) (durationMinutes / 60), (int) (durationMinutes % 60));
            minFlightTimes.put(carrier, minFlightTimes.getOrDefault(carrier, LocalTime.MAX).isBefore(duration)
                    ? minFlightTimes.getOrDefault(carrier, LocalTime.MAX) : duration);
        }

        System.out.println("Минимальное время полета между Владивостоком и Тель-Авивом для каждого авиаперевозчика:");
        for (Map.Entry<String, LocalTime> entry : minFlightTimes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().format(DateTimeFormatter.ofPattern("H:mm")));
        }
    }
}
