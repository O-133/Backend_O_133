package com.example.demo.user.repository;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserDisease;
import com.example.demo.user.domain.UserDiseaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDiseaseRepository extends JpaRepository<UserDisease, UserDiseaseId> {
    @Query("SELECT ud FROM UserDisease ud WHERE ud.id.userId = :userId")
    UserDisease findByUserId(@Param("userId") Integer userId);
}
