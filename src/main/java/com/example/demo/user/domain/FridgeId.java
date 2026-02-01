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
public class FridgeId implements Serializable {

    @Column(name = "user_id") // 'id'에서 'user_id'로 수정!
    private Integer userId;

    @Column(name = "ingredient_id")
    private Integer ingredientId;
}