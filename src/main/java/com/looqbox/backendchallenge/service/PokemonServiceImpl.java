package com.looqbox.backendchallenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.looqbox.backendchallenge.client.PokemonApiService;
import com.looqbox.backendchallenge.entity.SortType;
import com.looqbox.backendchallenge.utils.QuickSort;
import com.looqbox.backendchallenge.utils.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonApiService pokemonApiService;
    private final Cache<String, List<String>> cache;

    public PokemonServiceImpl(PokemonApiService pokemonApiService, Cache<String, List<String>> cache) {
        this.pokemonApiService = pokemonApiService;
        this.cache = cache;
    }

    @Override
    public List getPokemons(String query, SortType sort) throws JsonProcessingException {
        if(cache.get(query).isPresent()) {
            List<String> cacheableResponse = (List<String>) cache.get(query).get();
            String[] sortPokemonList = this.sortPokemonList(query, cacheableResponse, sort);
            return Arrays.stream(sortPokemonList).toList();
        } else {
            List<String> pokemonList = pokemonApiService.getPokemonApi().stream()
                    .map(it -> it.get("name"))
                    .toList();
            cache.put(query, pokemonList);
            String[] sortPokemonList = this.sortPokemonList(query, pokemonList, sort);
            return Arrays.stream(sortPokemonList).toList();
        }
    }


    @Override
    public List getPokemonsHighlight(String query, SortType sort) throws JsonProcessingException {
        if (query == null) {
            return getPokemons(query, sort);
        }
        Optional<List<String>> resultList = cache.get(query);
        if(resultList.isPresent()) {
            List<String> cacheableResponse = resultList.get();
            String[] sortPokemonList = cacheableResponse.toArray(new String[0]);
            QuickSort.quickSort(sortPokemonList, 0, cacheableResponse.size() -1, sort != null ? sort : SortType.asc);
            return returnPokemonHighlightList(query, sortPokemonList);
        } else {
            List<String> pokemonList = pokemonApiService.getPokemonApi().stream()
                    .map(it -> it.get("name"))
                    .toList();
            cache.put(query, pokemonList);
            String[] sortPokemonList = pokemonList.toArray(new String[0]);
            QuickSort.quickSort(sortPokemonList, 0, pokemonList.size() -1, sort != null ? sort : SortType.asc);
            return returnPokemonHighlightList(query, sortPokemonList);
        }

    }

    public String[] sortPokemonList(String query, List<String> pokemonList, SortType sort) {
        String[] sortPokemonList = query != null
                ? pokemonList.stream().filter(it -> it.contains(query)).toList().toArray(new String[0])
                : pokemonList.toArray(new String[0]);
        QuickSort.quickSort(
                sortPokemonList,
                0,
                Arrays.stream(sortPokemonList).toList().size() - 1,
                sort != null ? sort : SortType.asc);
        return sortPokemonList;
    }

    public List returnPokemonHighlightList(String query, String[] pokemonList) {
        return Arrays.stream(pokemonList)
                .filter(it -> it.contains(query))
                .map(it ->
                        Map.of("name", it, "highlight", it.replace(query, "<pre>" + query + "</pre>"))
                ).toList();
    }
}
