package com.example.assignment2.advertisement.application.port.out;

import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByTargetGenderAndTargetCountry(String gender, String country);
    List<Advertisement> findAllByTargetGenderAndTargetCountryOrderByWeightDesc(String gender, String country);


}
