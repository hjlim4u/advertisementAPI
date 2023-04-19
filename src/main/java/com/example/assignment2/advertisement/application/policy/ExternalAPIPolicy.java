package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.application.ExternalAPIService;
import com.example.assignment2.advertisement.domain.Advertisement;
import com.example.assignment2.advertisement.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class ExternalAPIPolicy implements PolicyState {
    private final ExternalAPIService externalAPIService;

    protected final UserRequest userRequest;

    @Override
    public List<Advertisement> transmit() throws InterruptedException {
        Stream<Long> ad_ids = userRequest.getAdvertisements().stream().map(Advertisement::getId);
        Advertisement[] adArr = userRequest.getAdvertisements().toArray(new Advertisement[0]);
        Flux<Long> externalResult = externalAPIService.getAdCampaignIds(userRequest.getUserId(), ad_ids, userRequest.getTotal());
        return postProcess(externalResult, adArr);
    }



    abstract List<Advertisement> postProcess(Flux<Long> externalResult, Advertisement[] adArr) throws InterruptedException;
}
