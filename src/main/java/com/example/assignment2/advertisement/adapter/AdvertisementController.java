package com.example.assignment2.advertisement.adapter;

import com.example.assignment2.advertisement.application.port.in.AdvertisementService;
import com.example.assignment2.advertisement.domain.Advertisement;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
@Tag(name = "Advertisement controller", description = "select appropriate advertisement for user")
public class AdvertisementController {
    private AdvertisementService advertisementService;

    @Autowired
    AdvertisementController(AdvertisementService advertisementService){
        this.advertisementService = advertisementService;
    }
    @Operation(summary = "advertisement selection", description = "select appropriate advertisement according to the user info")
    @GetMapping("/user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found your advertisement",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Advertisement.class))))
    })
    @ResponseBody
    public List<Advertisement> userRequest(@Parameter(description = "user id") @RequestParam("id") Integer id,
                                           @Parameter(description = "user gender") @RequestParam("gender") String gender,
                                           @Parameter(description = "user country") @RequestParam("country") String country) throws JsonProcessingException, InterruptedException {
        List<Advertisement>advertisementList = advertisementService.getAdvertisementsByUser(id, gender, country);

        return advertisementList;
    }

}
