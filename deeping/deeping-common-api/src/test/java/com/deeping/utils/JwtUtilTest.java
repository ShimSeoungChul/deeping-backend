package com.deeping.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;

class JwtUtilTest {

    //글자 256 == 32 비트 이상으로 구성
    private static String SECRET = "12345678901234567890123456789012";
    private static JwtUtil jwtUtil;

    @BeforeAll
    public static void setUp(){
         jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){
        String token = jwtUtil.createToken(1004L, "John"); //사용자 Id, 이름 입력

        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0.8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";
        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("name")).isEqualTo("John");
        assertThat(claims.get("userId", Long.class)).isEqualTo(1004L);

    }

}