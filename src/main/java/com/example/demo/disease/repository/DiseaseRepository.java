package com.example.demo.disease.repository;

import com.example.demo.disease.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    // 질병 이름으로 엔티티 조회
    Optional<Disease> findByName(String name);
}
