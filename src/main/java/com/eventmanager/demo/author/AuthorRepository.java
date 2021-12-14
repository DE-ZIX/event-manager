package com.eventmanager.demo.author;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AuthorRepository extends CrudRepository<Author, Integer> {

}