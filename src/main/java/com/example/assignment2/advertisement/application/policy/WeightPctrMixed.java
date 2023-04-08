package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;
import com.example.assignment2.advertisement.dto.UserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@Component
public class WeightPctrMixed extends ExternalAPIPolicy {
    private final Weight weight;

    @Autowired
    public WeightPctrMixed(ExternalAPIService externalAPIService, UserRequest userRequest, Weight weight) {
        super(externalAPIService, userRequest);
        this.weight = weight;
    }


    @Override
    List<Advertisement> postProcess(Flux<Long> externalResult, Advertisement[] adArr) throws InterruptedException {
        int mixed_num = 1;
        final Set<Advertisement> temp = new LinkedHashSet<>();

        CountDownLatch cdl = new CountDownLatch(1);
        externalResult.take(mixed_num)
            .doOnTerminate(()->cdl.countDown())
            .subscribe(i-> {
                temp.add(adArr[i.intValue()]);

            });
        List<Advertisement> advertisementList = weight.transmit();
        cdl.await();
        temp.addAll(advertisementList);

        return temp.stream().limit(userRequest.getTotal()).toList();

    }
}
