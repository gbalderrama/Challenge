package com.universidad.QI.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.universidad.QI.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	@Query("SELECT u FROM User u WHERE u.role = 'TEACHER'")
	List<User> findAllTeachers();
	
	@Query("SELECT u FROM User u WHERE u.role = 'STUDENT'")
	List<User> findAllStudents();

}
