package com.example.assignment2.advertisement.application;

import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByTargetGenderAndTargetCountry(String gender, String country);


}
