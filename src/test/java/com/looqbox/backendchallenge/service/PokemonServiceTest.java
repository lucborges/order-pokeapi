package com.looqbox.backendchallenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.looqbox.backendchallenge.client.PokemonApiService;
import com.looqbox.backendchallenge.entity.SortType;
import com.looqbox.backendchallenge.utils.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

public class PokemonServiceTest {
    private PokemonApiService pokemonApiService;
    private PokemonServiceImpl pokemonService;
    private Cache cache;

    @BeforeEach
    void setupService() {
        pokemonApiService = Mockito.mock(PokemonApiService.class);
        cache = Mockito.mock(Cache.class);
        pokemonService = new PokemonServiceImpl(pokemonApiService, cache);
    }

    @Test
    public void testGetAllPokemonsSuccess() throws JsonProcessingException {
        Mockito.when(pokemonApiService.getPokemonApi()).thenReturn(List.of(
                Map.of("name", "pidgey"),
                Map.of("name", "pidgeotto"),
                Map.of("name", "charmander")));
        List getPokemonList = pokemonService.getPokemons(null, SortType.asc);
        List expectedPokemonList = List.of("charmander", "pidgeotto", "pidgey");

        Assertions.assertEquals(expectedPokemonList, getPokemonList);
    }

    @Test
    public void testGetSortPokemonsSuccess() throws JsonProcessingException {
        Mockito.when(pokemonApiService.getPokemonApi()).thenReturn(List.of(
                Map.of("name", "pidgey"),
                Map.of("name", "pidgeotto"),
                Map.of("name", "charmander")));
        List getPokemonList = pokemonService.getPokemons("pi", SortType.desc);
        List expectedPokemonList = List.of("pidgey", "pidgeotto");

        Assertions.assertEquals(expectedPokemonList, getPokemonList);
    }

    @Test
    public void testGetPokemonsHighlightSuccess() throws JsonProcessingException {
        Mockito.when(pokemonApiService.getPokemonApi()).thenReturn(List.of(
                Map.of("name", "pidgey", "highlight", "<pre>pi</pre>dgey"),
                Map.of("name", "pidgeotto", "highlight", "<pre>pi</pre>dgeotto")));
        List getPokemonList = pokemonService.getPokemonsHighlight("pi", SortType.asc);
        List expectedPokemonList = List.of(
                Map.of("name", "pidgeotto", "highlight", "<pre>pi</pre>dgeotto"),
                Map.of("name", "pidgey", "highlight", "<pre>pi</pre>dgey"));

        Assertions.assertEquals(expectedPokemonList, getPokemonList);
    }

    @Test
    public void testGetAllPokemonsHighlightSuccess() throws JsonProcessingException {
        Mockito.when(pokemonApiService.getPokemonApi()).thenReturn(List.of(
                Map.of("name", "pidgey"),
                Map.of("name", "pidgeotto"),
                Map.of("name", "charmander")));
        List getPokemonList = pokemonService.getPokemonsHighlight(null, SortType.asc);
        List expectedPokemonList = List.of("charmander", "pidgeotto", "pidgey");

        Assertions.assertEquals(expectedPokemonList, getPokemonList);
    }
}