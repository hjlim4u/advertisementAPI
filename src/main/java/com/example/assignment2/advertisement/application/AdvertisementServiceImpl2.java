package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.port.in.AdvertisementService;
import com.example.assignment2.advertisement.application.port.out.AdvertisementRepository;
import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import java.util.*;

import java.util.concurrent.CountDownLatch;
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
    private final Long ADNUM = 3L;
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
    public List<Advertisement> getAdvertisementsByUser(int userId, String gender, String country) throws InterruptedException {
        List<Advertisement> advertisements = advertisementRepository.findAllByTargetGenderAndTargetCountry(gender, country);

        Stream<Long> ad_campaign_ids = advertisements.stream().map(Advertisement::getId);
        Advertisement[] adArr = advertisements.toArray(new Advertisement[0]);

        List<Advertisement> results = new ArrayList<Advertisement>();


        switch (Recommendation.of(userId % Recommendation.Length())) {
            case RANDOM -> {

                Set<Long> indexes = new HashSet<Long>();
                while (indexes.size() < ADNUM) {
                    indexes.add(random.nextLong(advertisements.size()));
                }
                for (Long i : indexes) {
                    results.add(adArr[i.intValue()]);
                }
                return results;
            }
            case WEIGHT -> {
                return advertisements.stream().sorted(comparator).limit(ADNUM).collect(Collectors.toList());
            }
            case PCTR -> {

                List<Long> ids = externalAPIService.getAdCampaignIds(userId, ad_campaign_ids)
                        .take(ADNUM)
                        .map((tuple2) -> tuple2.getT1())
                        .collectList()
                        .block();

                for (Long id : ids) {
                    results.add(adArr[id.intValue()]);
                }

                return results;
            }
            case WEIGHT_PCTR_MIXED -> {

                int mixed_num = 1;
                CountDownLatch cdl = new CountDownLatch(1);
                final Advertisement[] pctrAd = new Advertisement[1];
                externalAPIService.getAdCampaignIds(userId, ad_campaign_ids)
                        .take(mixed_num)
                        .map((tuple2) -> tuple2.getT2())
                        .doOnTerminate(()->cdl.countDown())
                        .subscribe(i-> {
                            pctrAd[0] = adArr[i.intValue()];
                        });

                List<Advertisement> advertisementList = advertisements.stream().sorted(comparator).limit(ADNUM).collect(Collectors.toList());
                cdl.await();

                results.add(pctrAd[0]);

                for (Advertisement advertisement : advertisementList) {
                    if(advertisement.getId()==pctrAd[0].getId()){
                        continue;
                    }
                    results.add(advertisement);
                }
                if(results.size()>ADNUM){
                    results = results.subList(0, ADNUM.intValue());
                }

                return results;
            }

            default ->
                    throw new IllegalStateException("Unexpected value: " + Recommendation.of(userId % Recommendation.Length()));
        }

    }
}
