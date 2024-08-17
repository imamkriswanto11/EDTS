package com.edts.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String artist;
    @Column(nullable = false)
    private String location;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDate date;
    @JsonIgnore
    @Column(nullable = false)
    private LocalTime startTime;
    @JsonIgnore
    @Column(nullable = false)
    private LocalTime endTime;

    @Transient
    private String dateLabel;
    @Transient
    private String startTimeLabel;
    @Transient
    private String endTimeLabel;

    @Column(nullable = false)
    private int totalTickets;
    @Column(nullable = false)
    private int ticketsSold;

    private boolean isAlreadyPerformed = false;
}

