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
        return userRequest.getAdvertisements().stream().sorted(new Comparator<Advertisement>() {
                    @Override
                    public int compare(Advertisement o1, Advertisement o2) {
                        return o2.getWeight()-o1.getWeight();
                    }
                }).limit(userRequest.getTotal())
                .collect(Collectors.toList());
    }
}
