package com.looqbox.backendchallenge.controller.dto;

import java.util.List;

public class PokemonDto {
    private List result;

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    public PokemonDto(List result) {
        this.result = result;
    }
}
