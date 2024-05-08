package com.example.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class TicketValidator implements TicketValidation {
    @Override
    public boolean hasValidTickets(List<Map<String, Object>> tickets) {
        for (Map<String, Object> ticket : tickets) {
            String origin = (String) ticket.get("origin");
            String destination = (String) ticket.get("destination");

            if (origin.equals("VVO") && destination.equals("TLV")) {
                return true;
            }
        }
        throw new NoValidTicketsException("No valid tickets found for analysis.");
    }

    @Override
    public boolean isValidTicket(Map<String, Object> ticket) {
        String origin = (String) ticket.get("origin");
        String destination = (String) ticket.get("destination");

        if (!origin.equals("VVO") || !destination.equals("TLV")) {
            return false;
        }



        return true;
    }
}
