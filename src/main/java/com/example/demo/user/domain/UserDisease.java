package com.example.demo.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_disease")
public class UserDisease {

    @EmbeddedId
    private UserDiseaseId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "disease_id", insertable = false, updatable = false)
    private Integer diseaseId;

    public UserDisease(UserDiseaseId UserDiseaseId, user) {
        this.user = user;
        this.id = UserDiseaseId.getDiseaseId();
    }
}
