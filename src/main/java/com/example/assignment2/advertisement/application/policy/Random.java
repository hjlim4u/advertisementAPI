package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.domain.Advertisement;
import com.example.assignment2.advertisement.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Random implements PolicyState{
    private final UserRequest userRequest;
    @Override
    public List<Advertisement> transmit() {
        List<Advertisement> results = new ArrayList<Advertisement>();
        Advertisement[] adArr = userRequest.getAdvertisements().toArray(new Advertisement[0]);

        Set<Integer> indexes = new java.util.Random().ints(0, userRequest.getAdvertisements().size())
                .distinct()
                .limit(userRequest.getTotal())
                .boxed()
                .collect(Collectors.toSet());
        for (int index : indexes) {
            results.add(adArr[index]);
        }
        return results;
    }
}
