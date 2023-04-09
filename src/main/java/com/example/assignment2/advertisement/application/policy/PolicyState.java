package com.example.assignment2.advertisement.application.policy;

import com.example.assignment2.advertisement.domain.Advertisement;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface PolicyState {

    public List<Advertisement> transmit() throws InterruptedException;
}
