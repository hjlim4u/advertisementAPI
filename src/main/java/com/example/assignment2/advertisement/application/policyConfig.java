package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.policy.PolicyState;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Map;

//@Configuration
@Component
@RequiredArgsConstructor
public class policyConfig {

    private final Map<String, PolicyState> policyMap;

//    @Bean
//    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
//    @Primary
    public PolicyState policySelect(long userId) {
        switch ((int) (userId % 4)) {
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
