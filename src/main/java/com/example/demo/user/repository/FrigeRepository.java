package com.example.demo.user.repository;

import com.example.demo.user.domain.Frige;
import com.example.demo.user.domain.FrigeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrigeRepository extends JpaRepository<Frige, FrigeId> {
}
