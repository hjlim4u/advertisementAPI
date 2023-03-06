package com.example.assignment2.advertisement;


import com.example.assignment2.advertisement.application.port.out.AdvertisementRepository;
import com.example.assignment2.advertisement.domain.Advertisement;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.util.List;



@Component
public class DataLoader implements CommandLineRunner {
    private final AdvertisementRepository advertisementRepository;
   private final ObjectMapper objectMapper = new ObjectMapper();

    public DataLoader(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource dataResource = new ClassPathResource("ad_campaigns.json");
        List<Advertisement> advertisementList = objectMapper.readValue(dataResource.getInputStream(), new TypeReference<List<Advertisement>>() {
        });

        advertisementRepository.saveAll(advertisementList);
    }
}
