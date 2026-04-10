package com.metro.metro_ticket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    private String id;

    private String sourceStation;
    private String destinationStation;

    private int price;

    private LocalDateTime createdAt;
    private LocalDateTime expiryTime;

    private int usageCount;
    private boolean entryUsed;
}