package com.example.assignment2.advertisement.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // image_url, landing_url, target_country, target_gender
public class Advertisement {

    @Id
    private long id;
    private String name;
    private String imageUrl;
    private String landingUrl;
    private int weight;
    private String targetCountry;
    private String targetGender;
    private int reward;

    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender==null ? "N": targetGender;
    }

    public String getTargetGender(){
        return this.targetGender == "N"? null : this.targetGender;
    }
}
