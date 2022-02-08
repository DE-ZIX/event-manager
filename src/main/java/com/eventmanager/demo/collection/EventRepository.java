package com.eventmanager.demo.collection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<EventCollection, Integer> {
    @Query("SELECT m FROM EventCollection m WHERE (:startDate is null or m.startDate >= :startDate) and (:endDate is null or m.endDate <= :endDate)")
    List<EventCollection> findBetweenDate(Date startDate, Date endDate, Pageable pageable);
    @Query("SELECT COUNT(m.id) FROM EventCollection m WHERE (:startDate is null or m.startDate >= :startDate) and (:endDate is null or m.endDate <= :endDate)")
    long countBetweenDate(Date startDate, Date endDate);
    List<EventCollection> findByResourcesId(int resourceId, Pageable pageable);
    long countByResourcesId(int resourceId);
    @Query(value = "select * from event_collection as ec left join resource_collections as rc on ec.id = rc.collections_id where rc.resource_id not in (:id) or rc.resource_id is null", nativeQuery = true)
    List<EventCollection> findByResourcesIdNot(int id, Pageable pageable);
    @Query(value = "select distinct count(ec.id) from event_collection as ec left join resource_collections as rc on ec.id = rc.collections_id where rc.resource_id not in (:id) or rc.resource_id is null", nativeQuery = true)
    long countByResourcesIdNot(int id);
}
