package com.example.inspire_jpa.features.openapi.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inspire_jpa.features.openapi.domain.ForcastRequestDTO;
import com.example.inspire_jpa.features.openapi.service.ForcastService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/forcast")
@RequiredArgsConstructor 
public class ForcastController {

    private final ForcastService forcastService;

    @PostMapping("/fcst")
    public ResponseEntity<?> fcst(@RequestBody ForcastRequestDTO request) {

        System.out.println("debug >>>> forcast controller tcst ");
        System.out.println("debug >>>> forcast controller tcst params : " + request);

        return ResponseEntity.status(HttpStatus.OK).body(forcastService.connection(request));
    }
    
}
