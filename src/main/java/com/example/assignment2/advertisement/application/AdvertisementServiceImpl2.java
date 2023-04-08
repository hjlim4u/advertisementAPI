package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.policy.PolicyState;
import com.example.assignment2.advertisement.application.port.in.AdvertisementService;
import com.example.assignment2.advertisement.application.port.out.AdvertisementRepository;
import com.example.assignment2.advertisement.domain.Advertisement;
import com.example.assignment2.advertisement.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;



import java.util.*;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import java.util.stream.Stream;
//enum Recommendation {
//    RANDOM, WEIGHT, PCTR, WEIGHT_PCTR_MIXED;
//    private static final Recommendation[] RECOMMENDATIONS = Recommendation.values();
//
//
//    public static Recommendation of(int num) {
//        return RECOMMENDATIONS[num];
//    }
//
//    public static int Length() {
//        return RECOMMENDATIONS.length;
//    }
//}

@Service
public class AdvertisementServiceImpl2 implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final ExternalAPIService externalAPIService;
    private UserRequest userRequest;
    private final policyConfig policyConfig;
    private PolicyState policy;
    private final int ADNUM = 3;
//    Random random = new Random();
//    Comparator<Advertisement> comparator = new Comparator<Advertisement>() {
//        @Override
//        public int compare(Advertisement a, Advertisement b) {
//            return b.getWeight() - a.getWeight();
//        }
//    };
    @Autowired
    public AdvertisementServiceImpl2(AdvertisementRepository advertisementRepository, ExternalAPIService externalAPIService, UserRequest userRequest, policyConfig policyConfig){
        this.advertisementRepository = advertisementRepository;
        this.externalAPIService = externalAPIService;
        this.userRequest = userRequest;
        this.policyConfig = policyConfig;
    }

    @Override
    public List<Advertisement> getAdvertisementsByUser(long userId, String gender, String country) throws InterruptedException {
        List<Advertisement> advertisements = advertisementRepository.findAllByTargetGenderAndTargetCountry(gender, country);
        userRequest.setAdvertisements(advertisements);
        userRequest.setUserId(userId);
        userRequest.setTotal(ADNUM);
        policy = policyConfig.policyState();
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(policyConfig.class);
//        PolicyState policy = ac.getBean(PolicyState.class);
        return policy.transmit();

    }
}
