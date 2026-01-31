package com.example.demo.user.domain;

import com.example.demo.disease.domain.Disease;
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

    @MapsId("userId") // UserDiseaseId 내의 userId 필드와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("diseaseId") // UserDiseaseId 내의 diseaseId 필드와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id")
    private Disease disease;

    // 명확한 생성을 위해 연관 엔티티를 모두 받는 생성자 사용
    public UserDisease(User user, Disease disease) {
        this.id = new UserDiseaseId(user.getId(), disease.getId());
        this.user = user;
        this.disease = disease;
    }
}
