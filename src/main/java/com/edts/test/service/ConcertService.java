package com.edts.test.service;

import com.edts.test.model.Concert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Service
public interface ConcertService {

    public ResponseEntity<String> searchConcert(
            String name,
            String artist,
            String location,
            String date);

}
