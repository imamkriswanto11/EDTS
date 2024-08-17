package com.edts.test.repository;

import com.edts.test.model.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketReservationRepository extends JpaRepository<TicketReservation, Long> {
}
