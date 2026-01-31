package com.example.demo.user.repository;

import com.example.demo.user.domain.UserDisease;
import com.example.demo.user.domain.UserDiseaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiseaseRepository extends JpaRepository<UserDisease, UserDiseaseId> {
}
