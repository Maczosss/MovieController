package com.example.vanilla;


//klasa na potrzeby developmentu

import com.example.vanilla.model.Director;
import com.example.vanilla.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private MovieDAO movieDAO;
    private DirectorDAO directorDAO;

    @Autowired
    public DataLoader(MovieDAO movieDAO,DirectorDAO directorDAO) {
        this.movieDAO = movieDAO;
        this.directorDAO = directorDAO;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {


        Director firstDirector = new Director("Camerron","USA");
        Director secondDirector = new Director("Heinz","Germany");
        Director thirdDirector = new Director("Sasha","ZSRR");


        directorDAO.save(firstDirector);
        directorDAO.save(secondDirector);
        directorDAO.save(thirdDirector);

        Movie firstMovie = new Movie("John Wick",firstDirector,2015,true);
        Movie secondMovie = new Movie("John Rambo I",secondDirector,1982,true);
        Movie thirdMovie = new Movie("Terminator",thirdDirector,2983,true);

        movieDAO.save(firstMovie);
        movieDAO.save(secondMovie);
        movieDAO.save(thirdMovie);
    }


}
