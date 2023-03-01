package com.example.assignment2.advertisement.application;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalAPIService {

    private final WebClient webClient;

    public ExternalAPIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://predict-ctr-pmj4td4sjq-du.a.run.app/")
                .build();
    }
    public Flux<Long> getAdCampaignIds(long userId, List<Long> adCampaignIds) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("user_id", userId)
                        .queryParam("ad_campaign_ids", adCampaignIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> response.getPctr())
                .flatMapMany(Flux::fromIterable)
                .index()
                .sort((a,b)->Double.compare(b.getT2(),a.getT2()))
                .take(3)
                .map((tuple2) -> tuple2.getT1());
    }

}
