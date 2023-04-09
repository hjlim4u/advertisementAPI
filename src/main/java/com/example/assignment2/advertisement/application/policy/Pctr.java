package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;
import com.example.assignment2.advertisement.dto.UserRequest;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@Component
public class Pctr extends ExternalAPIPolicy {

    public Pctr(ExternalAPIService externalAPIService, UserRequest userRequest) {
        super(externalAPIService, userRequest);
    }

    @Override
    List<Advertisement> postProcess(Flux<Long> externalResult, Advertisement[] adArr) {

        List<Advertisement> results = new ArrayList<Advertisement>();
        List<Long> ids = externalResult.collectList().block();
        for (Long id : ids) {
            results.add(adArr[id.intValue()]);
        }
        return results;
    }
}
