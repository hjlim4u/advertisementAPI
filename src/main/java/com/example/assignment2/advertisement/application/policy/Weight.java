package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.domain.Advertisement;
import com.example.assignment2.advertisement.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class Weight implements PolicyState{
    private final UserRequest userRequest;
    @Override
    public List<Advertisement> transmit() {
        return userRequest.getAdvertisements().stream().sorted(Comparator.comparingInt(Advertisement::getWeight)).limit(userRequest.getTotal()).collect(Collectors.toList());
    }
}
