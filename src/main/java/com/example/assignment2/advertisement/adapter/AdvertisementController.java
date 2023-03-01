package com.example.assignment2.advertisement.adapter;

import com.example.assignment2.advertisement.application.port.in.AdvertisementService;
import com.example.assignment2.advertisement.domain.Advertisement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class AdvertisementController {
    private AdvertisementService advertisementService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AdvertisementController(AdvertisementService advertisementService){
        this.advertisementService = advertisementService;
    }

    @GetMapping("/user")
    @ResponseBody
    public String userRequest(@RequestParam("id") Integer id, @RequestParam("gender") String gender, @RequestParam("country") String country) throws JsonProcessingException {
        List<Advertisement>advertisementList = advertisementService.getAdvertisementsByUser(id, gender, country);

        return objectMapper.writeValueAsString(advertisementList);
    }

}
