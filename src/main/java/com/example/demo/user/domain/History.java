package com.example.demo.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "food_id", nullable = false)
    private Integer foodId;

    @Column(name = "create_at", nullable = false)
    private LocalDate createAt;

    public History(User user, Integer foodId, LocalDate createAt) {
        this.user = user;
        this.foodId = foodId;
        this.createAt = createAt;
    }
}
