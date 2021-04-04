package com.deeping.domain.Values;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    private String description;
}