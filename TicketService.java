package com.metro.metro_ticket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metro.metro_ticket.entity.Ticket;
import com.metro.metro_ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TicketService {

    private final TicketRepository repo;
    private Map<String, Integer> stationPrices;

    public TicketService(TicketRepository repo) {
        this.repo = repo;
        loadStations();
    }

    private void loadStations() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/stations.json");

            if (is == null) {
                throw new RuntimeException("stations.json not found in resources folder");
            }

            Map<?, ?> data = mapper.readValue(is, Map.class);
            Map<?, ?> stations = (Map<?, ?>) data.get("stations");

            stationPrices = new HashMap<>();

            for (Map.Entry<?, ?> entry : stations.entrySet()) {
                String station = (String) entry.getKey();
                Map<?, ?> value = (Map<?, ?>) entry.getValue();

                Integer price = (Integer) value.get("price");
                stationPrices.put(station, price);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading stations");
        }
    }

    public Ticket createTicket(String source, String destination) {

        if (!stationPrices.containsKey(source) || !stationPrices.containsKey(destination)) {
            throw new RuntimeException("Invalid station name");
        }

        int price = Math.abs(stationPrices.get(source) - stationPrices.get(destination));

        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID().toString());
        ticket.setSourceStation(source);
        ticket.setDestinationStation(destination);
        ticket.setPrice(price);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setExpiryTime(LocalDateTime.now().plusHours(18));
        ticket.setUsageCount(0);
        ticket.setEntryUsed(false);

        return repo.save(ticket);
    }

    public String entry(String id) {
        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "Ticket expired";
        }

        if (ticket.isEntryUsed()) {
            return "Entry already used";
        }

        ticket.setEntryUsed(true);
        ticket.setUsageCount(ticket.getUsageCount() + 1);

        repo.save(ticket);
        return "Entry successful";
    }

    public String exit(String id) {
        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "Ticket expired";
        }

        if (!ticket.isEntryUsed()) {
            return "Please use entry first";
        }

        if (ticket.getUsageCount() >= 2) {
            return "Ticket already used twice";
        }

        ticket.setUsageCount(ticket.getUsageCount() + 1);

        repo.save(ticket);
        return "Exit successful";
    }
}