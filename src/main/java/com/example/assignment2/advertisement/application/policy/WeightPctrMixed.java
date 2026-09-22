package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.concurrent.CountDownLatch;

@Component
public class WeightPctrMixed extends ExternalAPIPolicy {
    private final Weight weight;

    @Autowired
    public WeightPctrMixed(ExternalAPIService externalAPIService, Weight weight) {
        super(externalAPIService);
        this.weight = weight;
    }


    @Override
    List<Advertisement> postProcess(Flux<Long> externalResult, List<Advertisement> advertisements, long userId, int total) throws InterruptedException {
        int mixed_num = 1;
        final Set<Advertisement> temp = new LinkedHashSet<>();

        CountDownLatch cdl = new CountDownLatch(1);
        externalResult.take(mixed_num)
            .doOnTerminate(()->cdl.countDown())
            .subscribe(i-> {
                temp.add(advertisements.get(i.intValue()));

            });
        List<Advertisement> advertisementList = weight.transmit(advertisements, userId, total);
        cdl.await();
        temp.addAll(advertisementList);

        return temp.stream().limit(total).toList();

    }
}
