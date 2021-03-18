package com.deeping.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class UserTest {

    @Test
    public void creation(){
        User user = User.builder()
                .phone("010-7756-1989")
                .name("테스터")
                .level(5L)
                .build();

        assertThat(user.getName()).isEqualTo("테스터");
        assertThat(user.isAdamin()).isEqualTo(true);
    }
}