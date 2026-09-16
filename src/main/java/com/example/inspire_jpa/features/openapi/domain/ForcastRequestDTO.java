package com.example.inspire_jpa.features.openapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder 
@Getter 
@ToString 
@NoArgsConstructor 
@AllArgsConstructor 
public class ForcastRequestDTO {

    private String beach_num, base_date, base_time;
}
