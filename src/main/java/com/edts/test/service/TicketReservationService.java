package com.edts.test.service;

import com.edts.test.dto.BookingRequestDto;
import org.springframework.http.ResponseEntity;

public interface TicketReservationService {
    public ResponseEntity<String> bookTicket(BookingRequestDto bookingRequestDto);
}