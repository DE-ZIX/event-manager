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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getResponsibleAuthor() {
        return responsibleAuthor;
    }

    public void setResponsibleAuthor(Author responsibleAuthor) {
        this.responsibleAuthor = responsibleAuthor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }
}
