package com.example.assignment2.advertisement.application;

import lombok.Data;

import java.util.List;

@Data
public class Response {

    private static List<Double> pctr;

    public List<Double> getPctr() {
        return pctr;
    }

    public void setPctr(List<Double> pctr) {
        this.pctr = pctr;
    }

}