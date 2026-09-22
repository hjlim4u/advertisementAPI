package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

@Component
@Order(3)
public class WeightPctrMixed extends ExternalAPIPolicy {
    private final Weight weight;

    @Autowired
    public WeightPctrMixed(ExternalAPIService externalAPIService, Weight weight) {
        super(externalAPIService);
        this.weight = weight;
    }

    @Override
    List<Advertisement> postProcess(Flux<Long> externalResult, List<Advertisement> advertisements, long userId, int total) {
        // top-1 by pCTR first, then fill by weight
        List<Advertisement> top = externalResult.take(1).map(i -> advertisements.get(i.intValue())).collectList().block();
        return Stream.concat(top.stream(), weight.transmit(advertisements, userId, total).stream())
                .distinct().limit(total).toList();
    }
}
