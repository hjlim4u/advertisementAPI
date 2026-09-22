package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(0)
public class Random implements PolicyState{
    @Override
    public List<Advertisement> transmit(List<Advertisement> advertisements, long userId, int total) {
        List<Advertisement> results = new ArrayList<Advertisement>();
        Advertisement[] adArr = advertisements.toArray(new Advertisement[0]);

        Set<Integer> indexes = new java.util.Random().ints(0, advertisements.size())
                .distinct()
                .limit(total)
                .boxed()
                .collect(Collectors.toSet());
        for (int index : indexes) {
            results.add(adArr[index]);
        }
        return results;
    }
}
