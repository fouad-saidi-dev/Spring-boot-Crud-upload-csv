package com.example.demoback.repositories;

import com.example.demoback.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSVRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);
}
