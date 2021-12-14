package com.eventmanager.demo.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public @ResponseBody Author PostAuthor(@RequestBody Author authorParam) {
        Author author = new Author(authorParam);
        authorRepository.save(author);
        return author;
    }

    @GetMapping
    public @ResponseBody Iterable<Author> getAuthors() {
        // This returns a JSON or XML with the users
        return authorRepository.findAll();
    }
}
