package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.domain.Advertisement;

import java.util.List;
public interface PolicyState {

    public List<Advertisement> transmit(List<Advertisement> advertisements, long userId, int total);
}
