package com.deeping.domain;

import lombok.Builder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Entity
@Builder
public class SurveyResult {
    @Id
    Long id;

    @NotBlank
    Long userId;

    @NotBlank
    Long SurveyId;

    @NotBlank
    String answer;
}

