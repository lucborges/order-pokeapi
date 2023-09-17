package com.looqbox.backendchallenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.looqbox.backendchallenge.client.PokemonApiService;
import com.looqbox.backendchallenge.entity.SortType;
import com.looqbox.backendchallenge.utils.QuickSort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonApiService pokemonApiService;

    public PokemonServiceImpl(PokemonApiService pokemonApiService) {
        this.pokemonApiService = pokemonApiService;
    }

    @Override
    public List getPokemons(String query, SortType sort) throws JsonProcessingException {
        List<String> pokemonList = pokemonApiService.getPokemonApi().stream()
                .map(it -> it.get("name"))
                .toList();
        String[] sortPokemonList = query != null
                ? pokemonList.stream().filter(it -> it.contains(query)).toList().toArray(new String[0])
                : pokemonList.toArray(new String[0]);
        QuickSort.quickSort(
                sortPokemonList,
                0,
                Arrays.stream(sortPokemonList).toList().size() - 1,
                sort != null ? sort : SortType.asc);
        return Arrays.stream(sortPokemonList).toList();
    }

    @Override
    public List getPokemonsHighlight(String query, SortType sort) throws JsonProcessingException {
        if (query == null) {
            return getPokemons(query, sort);
        }
        List<String> pokemonList = pokemonApiService.getPokemonApi().stream()
                .map(it -> it.get("name"))
                .toList();
        String[] sortPokemonList = pokemonList.toArray(new String[0]);
        QuickSort.quickSort(sortPokemonList, 0, pokemonList.size() -1, sort != null ? sort : SortType.asc);
        return Arrays.stream(sortPokemonList)
                .filter(it -> it.contains(query))
                .map(it ->
                        Map.of("name", it, "highlight", it.replace(query, "<pre>" + query + "</pre>"))
                ).toList();
    }
}
