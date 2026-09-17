package com.example.inspire_jpa.features.openapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder 
@Getter 
@Setter 
@ToString 
@NoArgsConstructor 
@AllArgsConstructor 
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForcastResponseDTO {

    @JsonProperty("category")
    private String category;

    @JsonProperty("fcstValue")
    private String fcstValue;

    private String categoryName;

}
