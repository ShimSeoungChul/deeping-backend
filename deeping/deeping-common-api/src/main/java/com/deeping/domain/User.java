package com.deeping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String phone;

    @NotEmpty
    @Setter
    private String name;

    private String nickName;
    private String hobby;
    private String voiceIntroduction; //목소리 자기소개 링
    private String textIntroduction;  //텍스트 자기소개

    @NotNull
    @Setter
    private Long level;

    private String password;


    public boolean isAdamin() {
        return level >=5;
    }

    public boolean isActive() {
        return level > 0;
    }

    public void deactive() {
        level = 0L;
    }

    @JsonIgnore
    public String getAccessToken() {
        if(password == null){
            return "";
        }

        return password.substring(0, 10);
    }
}
