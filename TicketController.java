package com.metro.metro_ticket.controller;

import com.metro.metro_ticket.entity.Ticket;
import com.metro.metro_ticket.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Map<String, String> request) {
        return service.createTicket(
                request.get("source"),
                request.get("destination")
        );
    }

    @PostMapping("/entry/{id}")
    public String entry(@PathVariable String id) {
        return service.entry(id);
    }

    @PostMapping("/exit/{id}")
    public String exit(@PathVariable String id) {
        return service.exit(id);
    }
}