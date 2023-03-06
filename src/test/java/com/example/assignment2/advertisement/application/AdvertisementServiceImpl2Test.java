package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.domain.Advertisement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringJUnitConfig
@SpringBootTest
class AdvertisementServiceImpl2Test {

    @Autowired
    AdvertisementServiceImpl2 advertisementServiceImpl2;
    private Long ADNUM;
    @BeforeEach
    void setUp() {
        ADNUM=3L;
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4})
    void getAdvertisementsByUser(int userId) throws InterruptedException {
        String gender = "F";
        String country = "KR";

        List<Advertisement> advertisements = advertisementServiceImpl2.getAdvertisementsByUser(userId, gender, country);
        for (Advertisement advertisement : advertisements) {
            assertEquals(advertisement.getTargetGender(), gender);
            Assertions.assertThat(advertisement.getTargetCountry()).isEqualTo(country);
        }

    }
}