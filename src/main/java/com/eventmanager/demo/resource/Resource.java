package com.eventmanager.demo.resource;

import com.eventmanager.demo.author.Author;
import com.eventmanager.demo.collection.Collection;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Resource {

    public Resource() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(length = 1024)
    public String title;

    @Column(length = 4096)
    public String description;

    @ManyToOne
    public Author responsibleAuthor;

    public String link;

    @Lob
    public byte[] image;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date createdDate;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date updatedDate;

    @ElementCollection
    public List<String> keywords;

    @ManyToMany
    public List<Collection> collections;

    @ManyToMany
    public List<Author> authors;
}
