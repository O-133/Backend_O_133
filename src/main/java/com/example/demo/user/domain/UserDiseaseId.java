package com.example.demo.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDiseaseId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "disease_id")
    private Integer diseaseId;
}
