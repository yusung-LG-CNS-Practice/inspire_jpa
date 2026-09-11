package com.example.inspire_jpa.featuers.commons.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RedisService {

    // TTL(Time To Live - 유효기간) : 초 단위
    private static final long RT_TTL = 60 * 60 * 24 * 7;
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveToken(String email, String rt){
        System.out.println("debug >>>> RedisService save rt token");
        redisTemplate.opsForValue()
            .set("RT: " + email, rt, RT_TTL, TimeUnit.SECONDS);
    }

    public void deleteToken(String email){
        System.out.println("debug >>>> RedisService delete rt token");
        redisTemplate.delete("RT : " + email);
    }
}
