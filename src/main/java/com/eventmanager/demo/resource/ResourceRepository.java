package com.eventmanager.demo.resource;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.List;

public interface ResourceRepository  extends CrudRepository<Resource, Integer> {
    List<Resource> findResourceByAuthorsId(Integer id);
    List<Resource> findResourceByCollectionsId(Integer id);
    List<Resource> findResourceByAuthorsIdAndCollectionsId(Integer authorId, Integer collectionId);
}
