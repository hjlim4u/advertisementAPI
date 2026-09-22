package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.policy.PolicyState;
import com.example.assignment2.advertisement.application.port.in.AdvertisementService;
import com.example.assignment2.advertisement.application.port.out.AdvertisementRepository;
import com.example.assignment2.advertisement.domain.Advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdvertisementServiceImpl2 implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final List<PolicyState> policies; // ordered by @Order: random, weight, pctr, weightPctrMixed
    private final int ADNUM = 3;

    @Autowired
    public AdvertisementServiceImpl2(AdvertisementRepository advertisementRepository, List<PolicyState> policies){
        this.advertisementRepository = advertisementRepository;
        this.policies = policies;
    }

    @Override
    public List<Advertisement> getAdvertisementsByUser(long userId, String gender, String country) {
        List<Advertisement> advertisements = advertisementRepository.findAllByTargetGenderAndTargetCountry(gender, country);
        PolicyState policy = policies.get((int) (userId % policies.size()));
        return policy.transmit(advertisements, userId, ADNUM);
    }
}
