package com.example.TravelApp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.TravelApp.entities.Adventure;

public interface AdventureRepository extends CrudRepository<Adventure, Integer> {
    public List<Adventure> findByCountry(String country);
    public List<Adventure> findByState(String state);
}