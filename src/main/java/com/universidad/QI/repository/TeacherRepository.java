package com.universidad.QI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.QI.models.entity.User;

@Repository
public interface teacherRepository extends JpaRepository<User, String>{

}
