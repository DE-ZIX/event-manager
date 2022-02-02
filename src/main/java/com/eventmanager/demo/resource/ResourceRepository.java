package com.eventmanager.demo.resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.List;

public interface ResourceRepository extends PagingAndSortingRepository<Resource, Integer> {
    List<Resource> findResourceByAuthorsId(Integer id, Pageable pageable);
    List<Resource> findResourceByCollectionsId(Integer id, Pageable pageable);
    List<Resource> findResourceByAuthorsIdAndCollectionsId(Integer authorId, Integer collectionId, Pageable pageable);
    long countByAuthorsId(Integer id);
    long countByCollectionsId(Integer id);
    long countByAuthorsIdAndCollectionsId(Integer authorId, Integer collectionId);
}
