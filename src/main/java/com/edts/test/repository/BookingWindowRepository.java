package com.edts.test.repository;

import com.edts.test.model.BookingWindow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface BookingWindowRepository extends JpaRepository<BookingWindow, Long> {
    Optional<BookingWindow> findByConcertIdAndStartTimeBeforeAndEndTimeAfter(Long concertId, LocalTime currentTimeStart, LocalTime currentTimeEnd);
}
