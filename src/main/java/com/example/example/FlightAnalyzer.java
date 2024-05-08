package com.example.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightAnalyzer implements TicketAnalyzer {
    private final Map<String, Map<String, Object>> minFlightTimes;
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

            LocalDate departureDate = LocalDate.parse((String) ticket.get("departure_date"), DateTimeFormatter.ofPattern("dd.MM.yy"));
            LocalDate arrivalDate = LocalDate.parse((String) ticket.get("arrival_date"), DateTimeFormatter.ofPattern("dd.MM.yy"));

            long daysDifference = ChronoUnit.DAYS.between(departureDate, arrivalDate);
            long durationMinutes = ChronoUnit.MINUTES.between(departureTime, arrivalTime);
            if (arrivalDate.isAfter(departureDate) && durationMinutes < 0) {
                durationMinutes += 1440;
                daysDifference--;
            }

            Map<String, Object> flightInfo = minFlightTimes.getOrDefault(carrier, new HashMap<>());
            long currentFlightTime = (long) flightInfo.getOrDefault("flightTime", Long.MAX_VALUE);
            long currentDaysDifference = (long) flightInfo.getOrDefault("daysDifference", Long.MAX_VALUE);

            if (daysDifference < currentDaysDifference || (daysDifference == currentDaysDifference && durationMinutes < currentFlightTime)) {
                flightInfo.put("flightTime", durationMinutes);
                flightInfo.put("daysDifference", daysDifference);
                minFlightTimes.put(carrier, flightInfo);
            }
        }

        System.out.println("Минимальное время полета между Владивостоком и Тель-Авивом для каждого авиаперевозчика:");
        for (Map.Entry<String, Map<String, Object>> entry : minFlightTimes.entrySet()) {
            String carrier = entry.getKey();
            Map<String, Object> flightInfo = entry.getValue();
            long flightTime = (long) flightInfo.get("flightTime");
            long daysDifference = (long) flightInfo.get("daysDifference");


            long hours = flightTime / 60;
            long minutes = flightTime % 60;

            System.out.println(carrier + ": " + String.format("%02d:%02d", hours, minutes) + " дней в пути: " + daysDifference);
        }

    }
}
