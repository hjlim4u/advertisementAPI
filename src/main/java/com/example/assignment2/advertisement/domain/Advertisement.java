package com.example.assignment2.advertisement.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
public class Advertisement {

    @Id
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("landing_url")
    private String landingUrl;
    @JsonProperty("weight")
    private Integer weight;
    @JsonProperty("target_country")
    private String targetCountry;

    private String targetGender;
    @JsonProperty("reward")
    private Integer reward;

    @JsonSetter("target_gender")
    public void targetGenderSetter(String gender) {
        this.targetGender = gender==null ? "N" : gender;
    }

    @JsonGetter("target_gender")
    public String targetGenderGetter() {
        return this.targetGender == "N" ? null : this.targetGender;
    }

}
