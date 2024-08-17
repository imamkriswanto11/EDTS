package com.edts.test.model;

import javax.persistence.*;
import lombok.Data;


import java.time.LocalTime;

@Entity
@Data
public class BookingWindow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    private LocalTime startTime;
    private LocalTime endTime;
    private int maxTickets;
    private int ticketsSold;
}
