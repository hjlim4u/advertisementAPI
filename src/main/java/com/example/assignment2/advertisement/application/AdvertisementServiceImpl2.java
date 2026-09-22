package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.policy.PolicyState;
import com.example.assignment2.advertisement.application.port.in.AdvertisementService;
import com.example.assignment2.advertisement.application.port.out.AdvertisementRepository;
import com.example.assignment2.advertisement.domain.Advertisement;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;



import java.util.*;



@Service
public class AdvertisementServiceImpl2 implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final ExternalAPIService externalAPIService;
    private final policyConfig policyConfig;
    private final int ADNUM = 3;

    @Autowired
    public AdvertisementServiceImpl2(AdvertisementRepository advertisementRepository, ExternalAPIService externalAPIService, policyConfig policyConfig){
        this.advertisementRepository = advertisementRepository;
        this.externalAPIService = externalAPIService;
        this.policyConfig = policyConfig;
    }

    @Override
    public List<Advertisement> getAdvertisementsByUser(long userId, String gender, String country) throws InterruptedException {
        List<Advertisement> advertisements = advertisementRepository.findAllByTargetGenderAndTargetCountry(gender, country);
        PolicyState policy = policyConfig.policySelect(userId);
//        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
//        PolicyState policySelect = ac.getBean(PolicyState.class);
        return policy.transmit(advertisements, userId, ADNUM);

    }
}
