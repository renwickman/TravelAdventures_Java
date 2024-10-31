package com.example.TravelApp.controllers;


import java.lang.Iterable;

import com.example.TravelApp.entities.Adventure;
import com.example.TravelApp.repositories.AdventureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/traveladventures")
public class TravelAdventuresController {

    private final AdventureRepository adventureRepository;

    public TravelAdventuresController(AdventureRepository adventureRepo) {
        this.adventureRepository = adventureRepo;
    }

    // Add controller methods below:
    @GetMapping()
    public Iterable<Adventure> getAdventures() {
        Iterable<Adventure> adventures = adventureRepository.findAll();
        return adventures;
    }

    @GetMapping("/bycountry/{country}")
    public Iterable<Adventure> getByCountry(@RequestParam String country) {
        Iterable<Adventure> chosenCountry = adventureRepository.findByCountry(country);
        return chosenCountry;
    }

    @GetMapping("/bystate")
    public Iterable<Adventure> getByState(@RequestParam(name="state") String state) {
        Iterable<Adventure> chosenState = adventureRepository.findByState(state);
        return chosenState;
    }

    @PostMapping("/addNew")
    public Adventure createAdventure(@RequestBody Adventure adventure) {
        Adventure newAdventure = this.adventureRepository.save(adventure);
        return newAdventure;
    }

    @PutMapping("/{id}")
    public Adventure updateAdventure(@PathVariable("id") Integer id, @RequestBody Adventure adventure) {
        Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
        if(!adventureOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Adventure adventureToUpdate = adventureOptional.get();
        if (adventureToUpdate.getBlogCompleted()) {
            adventureToUpdate.setBlogCompleted(false);
        } else {
            adventureToUpdate.setBlogCompleted(true);
        }
        Adventure updatedAdventure = this.adventureRepository.save(adventureToUpdate);
        return updatedAdventure;
    }

    @DeleteMapping("/{id}")
    public void deleteAdventure(@PathVariable("id") Integer id) {
        Optional<Adventure> removeAdventure = this.adventureRepository.findById(id);
        if(!removeAdventure.isPresent()) {
            Adventure toRemoveAdventure = removeAdventure.get();
            this.adventureRepository.delete(toRemoveAdventure);
        }
    }
}
