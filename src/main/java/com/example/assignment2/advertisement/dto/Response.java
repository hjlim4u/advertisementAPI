package com.example.assignment2.advertisement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Response {
    @JsonProperty("pctr")
    private List<Double> pctr;


}