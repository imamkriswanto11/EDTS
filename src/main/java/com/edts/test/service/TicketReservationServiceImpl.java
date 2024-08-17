package com.edts.test.service;

import com.edts.test.dto.BookingRequestDto;
import com.edts.test.enumeration.ErrorCode;
import com.edts.test.enumeration.ErrorMessage;
import com.edts.test.model.BookingWindow;
import com.edts.test.model.Concert;
import com.edts.test.model.Member;
import com.edts.test.model.TicketReservation;
import com.edts.test.repository.BookingWindowRepository;
import com.edts.test.repository.ConcertRepository;
import com.edts.test.repository.MemberRepository;
import com.edts.test.repository.TicketReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

@Service
public class TicketReservationServiceImpl extends BaseService implements TicketReservationService {
    @Autowired
    private TicketReservationRepository ticketReservationRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private BookingWindowRepository bookingWindowRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ErrorMessage errorMessage;
    @Transactional
    public ResponseEntity<String> bookTicket(BookingRequestDto bookingRequestDto) {
        Locale locale = Locale.getDefault();
        LocalTime currentTime = LocalTime.now();

        try {

            Member member = memberRepository.findById(bookingRequestDto.getMemberId()).orElse(null);
            if (member == null) {
                return httpService.getErrorResponse(ErrorCode.MEMBER_NOT_FOUND.toString(),
                        MessageFormat.format(errorMessage.getMessage(locale,
                                        ErrorCode.MEMBER_NOT_FOUND.toString()),
                                bookingRequestDto.getMemberId()), HttpStatus.BAD_REQUEST);
            }

            Concert concert = concertRepository.findById(bookingRequestDto.getConcertId()).orElse(null);
            if (concert == null) {
                return httpService.getErrorResponse(ErrorCode.CONCERT_NOT_FOUND.toString(),
                        MessageFormat.format(errorMessage.getMessage(locale,
                                        ErrorCode.CONCERT_NOT_FOUND.toString()),
                                bookingRequestDto.getConcertId()), HttpStatus.BAD_REQUEST);
            } else {
                if(concert.isAlreadyPerformed()) {
                    System.out.println("AAAAAAA" + MessageFormat.format(errorMessage.getMessage(locale,
                                    ErrorCode.CONCERT_ALREADY_PERFORMED.toString()),
                            concert.getName()));
                    return httpService.getErrorResponse(ErrorCode.CONCERT_ALREADY_PERFORMED.toString(),
                            MessageFormat.format(errorMessage.getMessage(locale,
                                            ErrorCode.CONCERT_ALREADY_PERFORMED.toString()),
                                    concert.getName()), HttpStatus.BAD_REQUEST);
                }
            }

            BookingWindow bookingWindow = bookingWindowRepository
                    .findByConcertIdAndStartTimeBeforeAndEndTimeAfter(bookingRequestDto.getConcertId(), currentTime, currentTime)
                    .orElse(null);

            if (bookingWindow == null) {
                return httpService.getErrorResponse(
                        ErrorCode.NO_BOOKING_WINDOW_AVAILABLE.toString(),
                        MessageFormat.format(errorMessage.getMessage(locale,
                                        ErrorCode.NO_BOOKING_WINDOW_AVAILABLE.toString()),
                                bookingRequestDto.getConcertId()), HttpStatus.BAD_REQUEST);
            } else {
                if (bookingWindow.getTicketsSold() + bookingRequestDto.getNumberOfTickets() > bookingWindow.getMaxTickets()) {
                    return httpService.getErrorResponse(ErrorCode.NOT_ENOUGH_TICKET_AVAILABLE.toString(),
                            MessageFormat.format(errorMessage.getMessage(locale,
                                            ErrorCode.NOT_ENOUGH_TICKET_AVAILABLE.toString()),
                                    bookingRequestDto.getConcertId()), HttpStatus.BAD_REQUEST);
                } else {

                    bookingWindow.setTicketsSold(bookingWindow.getTicketsSold() + bookingRequestDto.getNumberOfTickets());
                    bookingWindowRepository.save(bookingWindow);

                    concert.setTicketsSold(concert.getTicketsSold() + bookingRequestDto.getNumberOfTickets());
                    concertRepository.save(concert);

                    TicketReservation reservation = new TicketReservation();
                    reservation.setConcert(concert);
                    reservation.setMember(member);
                    reservation.setTicketsReserved(bookingRequestDto.getNumberOfTickets());
                    reservation.setReservationTime(LocalDateTime.now());
                    ticketReservationRepository.save(reservation);
                    return httpService.getSuccessResponse(
                            "Hi, "+ member.getName()+". You have success booking " + bookingRequestDto.getNumberOfTickets() + " ticket of " + concert.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return httpService.getErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.toString(), "Something went wrong", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}