package com.looqbox.backendchallenge.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class PokemonApiService {
    public List<Map<String, String>> getPokemonApi() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon?limit=1281&offset=1", String.class);

        String pokemonsJson = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(pokemonsJson, Map.class);
        List<Map<String, String>> list = (List) map.get("results");
        return list;
    }
}
