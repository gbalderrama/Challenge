package com.universidad.QI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.QI.models.entity.User;

import jakarta.persistence.Id;

@Repository
public interface userRepository extends JpaRepository<User, String>{

}
