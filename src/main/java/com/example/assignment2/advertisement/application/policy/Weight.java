package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
@Component
@Order(1)
public class Weight implements PolicyState{
    @Override
    public List<Advertisement> transmit(List<Advertisement> advertisements, long userId, int total) {
        return advertisements.stream()
                .sorted(Comparator.comparingInt(Advertisement::getWeight).reversed())
                .limit(total)
                .toList();
    }
}
