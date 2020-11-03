package com.example.vanilla.controllers;

import com.example.vanilla.DirectorDAO;
import com.example.vanilla.model.Director;
import com.example.vanilla.model.Movie;
import com.example.vanilla.MovieDAO;
import com.example.vanilla.exceptions.ConflictException;
import com.example.vanilla.exceptions.NotFoundException;
import com.example.vanilla.model.MovieCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// komunikacja z użytkownikiem
@RestController
public class MoviesControler {

    private MovieDAO movieDAO;
    private DirectorDAO directorDAO;


    //!dependency injection - chodzi o wstrzykiwanie zależności
    //!nie tworzyć żeby kazda klasa tworzyła nową instancję tego samego
    //!ma byc jedna klasa matka która wstrzyknie zależności
    //!do javy biblioteka Dagger do dependency injection

    @Autowired
    public MoviesControler(MovieDAO movieDAO, DirectorDAO directorDAO) {
        this.movieDAO = movieDAO;
        this.directorDAO = directorDAO;


    }



    @RequestMapping(value = "/directors",
            method = RequestMethod.GET)
    public List<Director> getDirector() {
        return directorDAO.findAll();
    }

    @RequestMapping(value = "/addmovie", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addMovieWithId(@RequestBody MovieCreator mc) {

        if (movieDAO.findMovieByTitle(mc.getTitle()) != null) {
            throw new ConflictException();
        }

        //inny obiekt do tworzenia innego obiektu (mc) do prostszego stworzenia obiektu bazodanowego
        Director dir = directorDAO.findDirectorById((long) mc.getDirector_id());
        Movie movie = new Movie(mc.getTitle(), dir, mc.getYear(), false);

        movieDAO.save(movie);
    }

    @RequestMapping(value = "/movies",
            method = RequestMethod.GET)
    public List<Movie> getMovies(@RequestParam("year") Optional<Integer> year,
                                 @RequestParam("sortBy") Optional<String> sortBy,
                                 @RequestParam("offset") Optional<Integer> offset,
                                 @RequestParam("limit") Optional<Integer> limit) {

        if (sortBy.isPresent()) {
            Sort sort = Sort.by(sortBy.get());
//            Sort sort1 = Sort.Order.asc(sort.get());
            return movieDAO.findAll(sort);
//            if (sort.get().equals("asc")) {
//                return movieDAO.findAllByOrderByYearAsc();
//            } else if (sort.get().equals("desc")) {
//                return movieDAO.findAllByOrderByYearDesc();
//            }
        } else if (offset.isPresent() && limit.isPresent()) {
            Pageable pageable = PageRequest.of(offset.get(), limit.get());
            return movieDAO.findAll(pageable).getContent();
        } else if (year.isPresent()) {
            return movieDAO.findMovieByYearEquals(year.get());
        } else {
            return movieDAO.findAll();
        }


    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    public Movie getMovieById(@PathVariable("id") long id) {
        if (movieDAO.findMovieById(id) != null) {
            return movieDAO.findMovieById(id);
        } else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/movie",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addMovie(@RequestBody Movie movie) {
        List<Movie> mylistOfMovies = movieDAO.findAll();

        for (Movie mylistOfMovie : mylistOfMovies) {
            if (mylistOfMovie.getTitle().equals(movie.getTitle()))
                throw new ConflictException();
        }
        movieDAO.save(movie);
    }

    @RequestMapping(value = "/movie",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addMovie2(@RequestBody Movie movie) {
        List<Movie> mylistOfMovies = movieDAO.findAll();
        if (mylistOfMovies.contains(movieDAO.findMovieByTitle(movie.getTitle()))) {
            throw new ConflictException();
        }

        movieDAO.save(movie);
    }


    List<Movie> myList = Arrays.asList(new Movie(0,"Hello",0),
            new Movie(1,"Hi",1),
            new Movie(2,"Alo",2));

    List<Movie> myList = new ArrayList<>();


    @RequestMapping("/helloworld/{id}")
    @ResponseBody
    public String helloWorld(@PathVariable("id") int id) {
        if (id ==5) {
            throw  new NotFoundException();
        }
        return "Hello " + id;
    }


    @RequestMapping(value = "/dodaj",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void dodaj(@RequestBody Movie powitania) {
        myList.add(powitania);
        System.out.printf("Dostalem od usera: " + powitania.title);
    }

    @RequestMapping("/helloworld/{id}")
    @ResponseBody
    public String helloWorld(@PathVariable("id") int id) {
        myList.get(id);
        return "Hello " + id;
    }


    @RequestMapping("/hello")
    @ResponseBody
    public List checkList() {
        System.out.printf(myList.toString());
        return myList;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void add(@RequestBody Movie powitania) {
        myList.add(powitania);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@RequestBody Movie powitania) {
        myList.set(powitania.getId(), powitania);
    }


}

