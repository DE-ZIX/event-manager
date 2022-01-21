package com.eventmanager.demo.author;

import com.eventmanager.demo.resource.Resource;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class Author {

    public Author() {

    }
    public Author(Author a) {
        this.id = a.id;
        this.name = a.name;
        this.surname = a.surname;
        this.email = a.email;
        this.affiliation = a.affiliation;
        this.orcid = a.orcid;
        this.resources = a.resources;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String email;

    @Column(length = 64)
    public String name;

    @Column(length = 64)
    public String surname;

    @Column(length = 256)
    public String affiliation;

    @Pattern(regexp = "XXXX-XXXX-XXXX-XXXX")
    public String orcid;

    @ManyToMany
    public List<Resource> resources;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getOrcid() {
        return orcid;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public String getEmail() {
        return email;
    }

}
