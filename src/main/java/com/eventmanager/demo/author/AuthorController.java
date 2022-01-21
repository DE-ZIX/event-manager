package com.eventmanager.demo.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping(path="/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public @ResponseBody Iterable<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Author getAuthor(@PathVariable(value = "id") int id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Author not found"));
    }

    @PostMapping
    public @ResponseBody Author PostAuthor(@RequestBody Author authorParam) {
        Author author = new Author(authorParam);
        authorRepository.save(author);
        return author;
    }

    @PutMapping
    public @ResponseBody Author PutAuthor(@RequestBody Author authorParam) {
        Author author = authorRepository.findById(authorParam.id).orElse(null);
        if (author == null) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find author");
        }
        author.setName(authorParam.getName());
        author.setSurname(authorParam.getSurname());
        author.setEmail(authorParam.getEmail());
        author.setOrcid(authorParam.getOrcid());
        author.setAffiliation(authorParam.getAffiliation());
        author.setResources(authorParam.getResources());
        authorRepository.save(author);
        return author;
    }

    @DeleteMapping(path ={"/{id}"})
    public @ResponseBody String DeleteAuthor(@PathVariable(value="id") int id) {
        authorRepository.deleteById(id);
        return "Author deleted";
    }

}
