package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class Pctr extends ExternalAPIPolicy {

    public Pctr(ExternalAPIService externalAPIService) {
        super(externalAPIService);
    }

    @Override
    List<Advertisement> postProcess(List<Integer> externalResult, List<Advertisement> advertisements, long userId, int total) {
        return externalResult.stream().map(advertisements::get).toList();
    }
}
