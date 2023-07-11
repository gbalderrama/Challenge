package com.universidad.QI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.QI.models.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>{

}
