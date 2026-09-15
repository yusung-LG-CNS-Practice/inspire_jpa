package com.example.inspire_jpa.features.openai.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inspire_jpa.features.openai.service.OpenAiService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/openai")
@RequiredArgsConstructor 
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/recommand")
    public ResponseEntity<?> recommand(@RequestParam("weather") String weather,
                                 @RequestParam ("location") String location) {
        
        System.out.println("debug >>>> openai controller recommand");
        System.out.println("debug >>>> openai controller recommand params : " + weather);
        System.out.println("debug >>>> openai controller recommand params : " + location);

        // openAiService.recommand(weather, location);
        // return null;

        return ResponseEntity.status(HttpStatus.OK).body(openAiService.recommand(weather, location));
    }

    @PostMapping("/quiz")
    public ResponseEntity<?> quiz(@RequestParam("subject") String subject) {

        System.out.println("debug >>>> openai controller quiz");
        System.out.println("debug >>>> openai controller quiz params : " + subject);
        
        return ResponseEntity.status(HttpStatus.OK).body(openAiService.quiz(subject));
    }
}
