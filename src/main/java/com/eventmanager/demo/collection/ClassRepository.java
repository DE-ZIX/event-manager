package com.eventmanager.demo.collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClassRepository extends PagingAndSortingRepository<ClassCollection, Integer> {

}
