package com.example.example;
import java.util.List;
import java.util.Map;

public interface TicketValidation {
    boolean hasValidTickets(List<Map<String, Object>> tickets);

    boolean isValidTicket(Map<String, Object> ticket);
}
