package com.eventmanager.demo.collection;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventCollection, Integer> {
}
