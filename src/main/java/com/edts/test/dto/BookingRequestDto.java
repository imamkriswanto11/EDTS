package com.edts.test.dto;

import lombok.Data;

@Data
public class BookingRequestDto {
    private Long concertId;
    private Long memberId;
    private int numberOfTickets;
}
