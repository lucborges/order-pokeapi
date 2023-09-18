package com.looqbox.backendchallenge.controller;

import com.looqbox.backendchallenge.controller.dto.PokemonDto;
import com.looqbox.backendchallenge.entity.SortType;
import com.looqbox.backendchallenge.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemons")
    public ResponseEntity<PokemonDto> getPokemons(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) SortType sort) throws IOException {
        return ResponseEntity.ok(new PokemonDto(pokemonService.getPokemons(query, sort)));
    }

    @GetMapping("/pokemons/highlight")
    public ResponseEntity<PokemonDto> getPokemonsHighlight(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) SortType sort) throws IOException {
        return ResponseEntity.ok(new PokemonDto(pokemonService.getPokemonsHighlight(query, sort)));
    }
}
