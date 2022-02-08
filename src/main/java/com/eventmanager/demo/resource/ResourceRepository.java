package com.eventmanager.demo.resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.List;

public interface ResourceRepository extends PagingAndSortingRepository<Resource, Integer> {
    List<Resource> findResourceByAuthorsId(Integer id, Pageable pageable);
    List<Resource> findResourceByCollectionsId(Integer id, Pageable pageable);
    List<Resource> findByCollectionsId(Integer id, Pageable pageable);
    List<Resource> findResourceByAuthorsIdAndCollectionsId(Integer authorId, Integer collectionId, Pageable pageable);
    @Query(value = "select * from resource as r left join collection_resources as cr on r.id = cr.resources_id where cr.collection_id not in (:id) or cr.collection_id is null", nativeQuery = true)
    List<Resource> findByCollectionsIdNot(Integer id, Pageable pageable);
    @Query(value = "select distinct count(r.id) from resource as r left join collection_resources as cr on r.id = cr.resources_id where cr.collection_id not in (:id) or cr.collection_id is null", nativeQuery = true)

    long countByCollectionsIdNot(Integer id);
    long countByAuthorsId(Integer id);
    long countByCollectionsId(Integer id);
    long countByAuthorsIdAndCollectionsId(Integer authorId, Integer collectionId);
}
