package com.universidad.QI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Teacher;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>{



	@Query("SELECT c FROM Course c WHERE c.teacher.id = ?1")
	List<Course> findassingned(@Param("teacher") String teacher);

}
