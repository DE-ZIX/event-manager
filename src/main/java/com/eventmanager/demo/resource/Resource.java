package com.eventmanager.demo.resource;

import com.eventmanager.demo.author.Author;
import com.eventmanager.demo.collection.Collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    public Author responsibleAuthor;

    public String link;

    @Lob
    public byte[] image;

    public String imageFileName;

    public String imageFileType;
    @JsonFormat(pattern="dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date createdDate;
    @JsonFormat(pattern="dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date updatedDate;

    @ElementCollection
    public List<String> keywords;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    public List<Collection> collections;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
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

    public String getImageFileName() { return imageFileName; }

    public void setImageFileName(String imageFileName) { this.imageFileName = imageFileName; }

    public String getImageFileType() { return imageFileType; }

    public void setImageFileType(String imageFileType) { this.imageFileType = imageFileType; }

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

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
    }
}
