package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.port.in.AdvertisementService;
import com.example.assignment2.advertisement.application.port.out.AdvertisementRepository;
import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


import java.util.*;

import java.util.stream.Collectors;

import java.util.stream.Stream;

enum Recommendation {
    RANDOM, WEIGHT, PCTR, WEIGHT_PCTR_MIXED;
    private static final Recommendation[] RECOMMENDATIONS = Recommendation.values();


    public static Recommendation of(int num) {
        return RECOMMENDATIONS[num];
    }

    public static int Length() {
        return RECOMMENDATIONS.length;
    }
}

@Service
public class AdvertisementServiceImpl2 implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final ExternalAPIService externalAPIService;

    Random random = new Random();
    Comparator<Advertisement> comparator = new Comparator<Advertisement>() {
        @Override
        public int compare(Advertisement a, Advertisement b) {
            return b.getWeight() - a.getWeight();
        }
    };
    @Autowired
    public AdvertisementServiceImpl2(AdvertisementRepository advertisementRepository, ExternalAPIService externalAPIService){
        this.advertisementRepository = advertisementRepository;
        this.externalAPIService = externalAPIService;
    }

    @Override
    public List<Advertisement> getAdvertisementsByUser(int userId, String gender, String country) {
        List<Advertisement> advertisements = advertisementRepository.findAllByTargetGenderAndTargetCountry(gender, country);
        Stream<Long> ad_campaing_ids = advertisements.stream().map(Advertisement::getId);
        Advertisement[] adArr = advertisements.toArray(new Advertisement[0]);
        List<Advertisement> results = new ArrayList<Advertisement>();


        switch (Recommendation.of(userId % Recommendation.Length())) {
            case RANDOM -> {

                Set<Long> indexes = new HashSet<Long>();
                while (indexes.size() < 3) {
                    indexes.add(random.nextLong(advertisements.size()));
                }
                for (Long i : indexes) {
                    results.add(adArr[i.intValue()]);
                }
                return results;
            }
            case WEIGHT -> {
                System.out.println(1);
                return advertisements.stream().sorted(comparator).limit(3).collect(Collectors.toList());
            }
            case PCTR -> {
                System.out.println(2);
                List<Long> ids = externalAPIService.getAdCampaignIds(userId, ad_campaing_ids.collect(Collectors.toList()))
                                .collectList().block();
                for (Long id : ids) {
                    results.add(adArr[id.intValue()]);
                }
                return results;
            }
            case WEIGHT_PCTR_MIXED -> {
                System.out.println(3);
                Mono<Long> pctrFlux = externalAPIService.getAdCampaignIds(userId, ad_campaing_ids.collect(Collectors.toList())).single()
                        .subscribeOn(Schedulers.boundedElastic());
                List<Advertisement> advertisementList = advertisements.stream().sorted(comparator).limit(3).collect(Collectors.toList());
                System.out.println(advertisementList);

                 Long hightspctrid = pctrFlux.block();

                Advertisement highpctrad = adArr[hightspctrid.intValue()];
                for (int i = 0; i < advertisementList.size(); i++) {
                    if (advertisementList.get(i).getId()==highpctrad.getId()) {
                        Advertisement advertisement = advertisementList.remove(i);
                        advertisementList.add(0, advertisement);
                        return advertisementList;
                    }
                }
                Advertisement advertisement = advertisementRepository.findById(hightspctrid).get();
                advertisementList.remove(advertisementList.size()-1);
                advertisementList.add(0, highpctrad);
                return advertisementList;
            }

            default ->
                    throw new IllegalStateException("Unexpected value: " + Recommendation.of(userId % Recommendation.Length()));
        }

    }
}
