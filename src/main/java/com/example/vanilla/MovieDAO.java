package com.example.vanilla;

import antlr.collections.List;
import com.example.vanilla.model.Director;
import com.example.vanilla.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Data Access Object

//Relacja obiektu z jego ID przez CrudRepository, Long klucz

@Repository
public interface MovieDAO extends CrudRepository<Movie,Long> {
    //tej interfejs będzie repozytorium danych które będą zamieniane na zapytania sql

    // wszystkie interakcje z bazą
    List<Movie> findAll();
    Page<Movie> findAll(Pageable pageable);
    List<Movie> findAll(Sort sort);


    Movie findMovieById(Long id);

    List<Movie> findMovieByYearEquals(int year);

    Movie findMovieByTitle(String title);

    List<Movie> findAllByOrderByYearAsc();
    List<Movie> findAllByOrderByYearDesc();


    //zamiast pisać w sql mozna pisać w tej metodzie

    //interfejs extends inny interfejs?


}
