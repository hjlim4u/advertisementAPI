package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.dto.Response;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class ExternalAPIService {
    private final RestTemplate restTemplate;

    public ExternalAPIService(RestTemplateBuilder builder) {
        this.restTemplate = builder.rootUri("https://predict-ctr-pmj4td4sjq-du.a.run.app").build();
    }

    /** Indexes (into adCampaignIds order) of the top-ADNUM ads by predicted CTR, best first. */
    public List<Integer> getAdCampaignIds(long userId, Stream<Long> adCampaignIds, int ADNUM) {
        String ids = adCampaignIds.map(String::valueOf).collect(Collectors.joining(","));
        List<Double> pctr = restTemplate
                .getForObject("/?user_id={userId}&ad_campaign_ids={ids}", Response.class, userId, ids)
                .getPctr();
        return IntStream.range(0, pctr.size()).boxed()
                .sorted(Comparator.comparing(pctr::get).reversed())
                .limit(ADNUM)
                .toList();
    }
}
