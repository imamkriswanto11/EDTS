package com.edts.test.service;

import com.edts.test.base.model.dto.ResponseDto;
import com.edts.test.model.Concert;
import com.edts.test.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcertServiceImpl extends BaseService implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponseEntity<String> searchConcert(String name, String artist, String location, String date) {
        ResponseDto<Concert> responseDto = new ResponseDto<Concert>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Concert> query = cb.createQuery(Concert.class);
        Root<Concert> concertRoot = query.from(Concert.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.like(cb.lower(concertRoot.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (location != null) {
            predicates.add(cb.like(cb.lower(concertRoot.get("location")), "%" + location.toLowerCase() + "%"));
        }

        if (artist != null) {
            predicates.add(cb.like(cb.lower(concertRoot.get("artist")), "%" + artist.toLowerCase() + "%"));
        }

        if (date != null) {
            predicates.add(cb.equal(concertRoot.get("date"), LocalDate.parse(date)));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<Concert> resultList = entityManager.createQuery(query).getResultList();

        if(resultList != null && !resultList.isEmpty()) {
            for(Concert concert : resultList) {
                concert.setDateLabel(concert.getDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                concert.setStartTimeLabel(concert.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                concert.setEndTimeLabel(concert.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            }

            resultList = resultList.stream()
                    .filter(concert -> concert.isAlreadyPerformed() == false)
                    .sorted(Comparator.comparing(Concert::getDate)).collect(Collectors.toList());
            responseDto.setData(resultList);
        }
        return httpService.getResponseEntity(responseDto);
    }

}
