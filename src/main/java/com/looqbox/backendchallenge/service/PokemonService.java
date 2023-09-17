package com.looqbox.backendchallenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.looqbox.backendchallenge.entity.SortType;

import java.util.List;

public interface PokemonService {
    List getPokemons(String query, SortType sort) throws JsonProcessingException;
    List getPokemonsHighlight(String query, SortType sort) throws JsonProcessingException;
}
