package com.example.demo.user.domain;

import com.example.demo.ingredient.domain.Ingredient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// user/domain/Fridge.java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "fridge")
@EntityListeners(AuditingEntityListener.class)
public class Fridge {

    @EmbeddedId
    private FridgeId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // User 테이블의 PK와 매핑
    private User user;

    @MapsId("ingredientId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id") // Ingredient 테이블의 PK와 매핑
    private Ingredient ingredient;

    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Builder
    public Fridge(User user, Ingredient ingredient) {
        this.id = new FridgeId(user.getId(), ingredient.getId());
        this.user = user;
        this.ingredient = ingredient;
    }
}
