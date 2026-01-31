package com.example.demo.user.domain;

import com.example.demo.disease.domain.Disease;
import com.example.demo.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nickname;

    private String gender;
    private int age;
    private double height;
    private double weight;
    private boolean isCooksAtHome;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserDisease> userDiseases = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_medications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "medication_name")
    private List<String> medications = new ArrayList<>();

    @Builder
    public User(String nickname, String gender, int age, double height, double weight,
                boolean isCooksAtHome, List<String> medications) {
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.isCooksAtHome = isCooksAtHome;
        this.medications = (medications != null) ? medications : new ArrayList<>();
    }

    /**
     * 연관관계 편의 메서드
     * 중복을 제거하고 타입을 안정적으로 처리합니다.
     */
    public void addUserDisease(Disease disease) {
        UserDiseaseId userDiseaseId = new UserDiseaseId(
                this.id.intValue(),
                disease.getId().intValue()
        );

        UserDisease userDisease = new UserDisease(userDiseaseId, this, disease);
        this.userDiseases.add(userDisease);
    }
}