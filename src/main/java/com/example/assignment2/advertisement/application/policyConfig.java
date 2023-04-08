package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.policy.PolicyState;
import com.example.assignment2.advertisement.dto.UserRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Map;

//@Configuration
@Component
@RequiredArgsConstructor
public class policyConfig {

    private final UserRequest userRequest;
    private final Map<String, PolicyState> policyMap;


    public PolicyState policyState() {
        switch ((int) (userRequest.getUserId() % 4)) {
            case 0:
                return policyMap.get("random");
            case 1:
                return policyMap.get("weight");
            case 2:
                return policyMap.get("pctr");
            case 3:
                return policyMap.get("weightPctrMixed");

        }
        return null;
    }

}
