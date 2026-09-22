package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@Component
@Order(2)
public class Pctr extends ExternalAPIPolicy {

    public Pctr(ExternalAPIService externalAPIService) {
        super(externalAPIService);
    }

    @Override
    List<Advertisement> postProcess(Flux<Long> externalResult, List<Advertisement> advertisements, long userId, int total) {

        List<Advertisement> results = new ArrayList<Advertisement>();
        List<Long> ids = externalResult.collectList().block();
        for (Long id : ids) {
            results.add(advertisements.get(id.intValue()));
        }
        return results;
    }
}
