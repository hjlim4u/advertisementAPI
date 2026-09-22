package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Component
@Order(1)
public class Weight implements PolicyState{
    @Override
    public List<Advertisement> transmit(List<Advertisement> advertisements, long userId, int total) {
        return advertisements.stream().sorted(new Comparator<Advertisement>() {
                    @Override
                    public int compare(Advertisement o1, Advertisement o2) {
                        return o2.getWeight()-o1.getWeight();
                    }
                }).limit(total)
                .collect(Collectors.toList());
    }
}
