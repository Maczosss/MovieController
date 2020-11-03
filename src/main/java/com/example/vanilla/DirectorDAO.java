package com.example.vanilla;

import com.example.vanilla.model.Director;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorDAO extends CrudRepository<Director,Long> {

    List<Director> findAll();

    Director findDirectorById(Long id);



}
