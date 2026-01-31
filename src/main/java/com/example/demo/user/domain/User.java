package com.example.demo.user.domain;

import com.example.demo.disease.domain.Disease;
import com.example.demo.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// user/domain/User.java
@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nickname;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private boolean isCooksAtHome;

    // cascade 설정으로 User 저장 시 UserDisease도 함께 저장됨
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
     * 연관관계 편의 메서드: Setter 대신 비즈니스 로직으로 관계 설정
     */
    public void addDisease(Disease disease) {
        UserDisease userDisease = new UserDisease(this, disease);
        this.userDiseases.add(userDisease);
    }
    public void updateProfile(int age, double height, double weight, boolean isCooksAtHome, List<String> medications) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.isCooksAtHome = isCooksAtHome;
        this.medications = (medications != null) ? medications : new ArrayList<>();
    }

    public void clearDiseases() {
        this.userDiseases.clear();
    }
}