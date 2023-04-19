package com.example.assignment2.advertisement.application.port.in;

import com.example.assignment2.advertisement.domain.Advertisement;

import java.util.List;

public interface AdvertisementService {
    List<Advertisement> getAdvertisementsByUser(long userId, String gender, String country) throws InterruptedException;
}
