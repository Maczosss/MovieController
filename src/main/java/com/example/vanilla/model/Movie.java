package com.example.vanilla.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


// Entity - obiekt bazodanowy, nie trzeba ręcznie robć tabel
// niżej wywołany zostanie w tle kod SQL

@Entity
//@Table(name = "Film")
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Integer year;

    @JsonIgnoreProperties
    private boolean isValid = false;

    @ManyToOne
    @JoinColumn(name = "DIRECTOR_ID")
    private Director director;

    public Movie(String title, Director director, Integer year, boolean isValid) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.isValid = isValid;
    }

    public Movie() {
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
