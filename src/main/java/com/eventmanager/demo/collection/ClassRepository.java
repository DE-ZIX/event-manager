package com.eventmanager.demo.collection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface ClassRepository extends PagingAndSortingRepository<ClassCollection, Integer> {
    List<ClassCollection> findByResourcesId(int resourceId, Pageable pageable);
    long countByResourcesId(int resourceId);
}
