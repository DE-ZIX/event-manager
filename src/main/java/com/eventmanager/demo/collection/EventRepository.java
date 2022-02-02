package com.eventmanager.demo.collection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<EventCollection, Integer> {
    @Query("SELECT m FROM EventCollection m WHERE (:startDate is null or m.startDate >= :startDate) and (:endDate is null or m.endDate <= :endDate)")
    List<EventCollection> findBetweenDate(Date startDate, Date endDate, Pageable pageable);
    @Query("SELECT COUNT(m.id) FROM EventCollection m WHERE (:startDate is null or m.startDate >= :startDate) and (:endDate is null or m.endDate <= :endDate)")
    long countBetweenDate(Date startDate, Date endDate);
}
