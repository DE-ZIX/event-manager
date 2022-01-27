package com.eventmanager.demo.collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<EventCollection, Integer> {
    @Query("SELECT m FROM EventCollection m WHERE (:startDate is null or m.startDate >= :startDate) and (:endDate is null or m.endDate <= :endDate)")
    List<EventCollection> findBetweenDate(Date startDate, Date endDate);
}
