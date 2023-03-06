package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.application.port.out.AdvertisementRepository;
import com.example.assignment2.advertisement.domain.Advertisement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@SpringJUnitConfig
@SpringBootTest
class ExternalAPIServiceTest {
    @Autowired
    public ExternalAPIService externalAPIService;
    @Autowired
    public AdvertisementRepository advertisementRepository;

    Stream<Long> ad_campaign_ids;
    List<Advertisement> advertisementList;
    @BeforeEach
    public void beforeEach(){
        String gender = "F";
        String country = "KR";
        advertisementList = advertisementRepository.findAllByTargetGenderAndTargetCountry(gender,country);
        ad_campaign_ids = advertisementList.stream().map(Advertisement::getId);
    }

    @Test
    void getAdCampaignIds() {
        int userId = 1;
        List<Double> adCampaignIds = externalAPIService.getAdCampaignIds(userId, ad_campaign_ids).map(a->a.getT2()).collectList().block();
        Assertions.assertThat(adCampaignIds).isSortedAccordingTo(Comparator.reverseOrder());
        Assertions.assertThat(adCampaignIds.size()).isEqualTo(advertisementList.size());

    }
}
