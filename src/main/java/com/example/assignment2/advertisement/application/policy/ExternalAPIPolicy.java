package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class ExternalAPIPolicy implements PolicyState {
    private final ExternalAPIService externalAPIService;

    @Override
    public List<Advertisement> transmit(List<Advertisement> advertisements, long userId, int total) throws InterruptedException {
        Stream<Long> ad_ids = advertisements.stream().map(Advertisement::getId);
        Flux<Long> externalResult = externalAPIService.getAdCampaignIds(userId, ad_ids, total);
        return postProcess(externalResult, advertisements, userId, total);
    }

    abstract List<Advertisement> postProcess(Flux<Long> externalResult, List<Advertisement> advertisements, long userId, int total) throws InterruptedException;
}
