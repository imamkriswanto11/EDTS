package com.edts.test.controller;

import com.edts.test.base.annotation.RateLimited;
import com.edts.test.dto.BookingRequestDto;
import com.edts.test.model.TicketReservation;
import com.edts.test.service.ConcertService;
import com.edts.test.service.TicketReservationService;
import com.edts.test.model.Concert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {
    @Autowired
    private ConcertService concertService;

    @Autowired
    private TicketReservationService ticketReservationService;

    @GetMapping("/search")
    public ResponseEntity<String> searchConcert(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String date) {
        return concertService.searchConcert(name, artist, location, date);
    }

    @PostMapping("/book-ticket")
    @RateLimited
    public ResponseEntity<String> bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
        return ticketReservationService.bookTicket(bookingRequestDto);
    }
}
