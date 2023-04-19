package com.example.assignment2.advertisement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("landing_url")
    private String landingUrl;
    @JsonProperty("weight")
    private int weight;
    @JsonProperty("target_country")
    private String targetCountry;
    @JsonProperty("target_gender")
    private String targetGender;
    @JsonProperty("reward")
    private int reward;

    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender==null ? "N": targetGender;
    }

    public String getTargetGender(){
        return this.targetGender == "N"? null : this.targetGender;
    }


}
