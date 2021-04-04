package com.deeping.domain;

import lombok.Builder;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
public class Survey {
    @Id
    Long id;
    String Type;
    String question;
}
