package com.eventmanager.demo.collection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface ClassRepository extends PagingAndSortingRepository<ClassCollection, Integer> {
    List<ClassCollection> findByResourcesId(int resourceId, Pageable pageable);
    List<ClassCollection> findIdByResourcesId(int resourceId);
    List<ClassCollection> findByIdNotIn(List<Integer> ids, Pageable pageable);
    long countByResourcesId(int resourceId);
    //@Query(value = "select * from class_collection as cc left join resource_collections as rc on cc.id = rc.collections_id where rc.resource_id not in (:id) or rc.resource_id is null", nativeQuery = true)
    List<ClassCollection> findByResourcesIdNot(int id, Pageable pageable);
    //@Query(value = "select distinct count(cc.id) from class_collection as cc left join resource_collections as rc on cc.id = rc.collections_id where rc.resource_id not in (:id) or rc.resource_id is null", nativeQuery = true)
    long countByResourcesIdNot(int id);
    long countByIdNotIn(List<Integer> ids);
}
