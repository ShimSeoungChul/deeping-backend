package com.deeping.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String url;

    @NotBlank
    private Long type;

    @NotBlank
    private String userId;


}
