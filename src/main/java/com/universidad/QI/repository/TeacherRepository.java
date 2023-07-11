package com.universidad.QI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>{
	
	@Query("SELECT t.course FROM Teacher t WHERE t.id = :id")
	List<Course> findCoursesAttached(@Param("id") String id); 

}
 