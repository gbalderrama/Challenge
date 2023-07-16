package com.universidad.QI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	
	//@Query("SELECT s FROM Student s WHERE s.course != :course")
	//List<Student> studentAvailable(@Param("course") String id);
	
	List<Student> findByNameContaining(String name);
	
	List<Student> findAllById(String id);
	
	List<Student> findByCoursesContaining(String id);
	
	List<Student> findAllByCoursesId(String id);
	
	@Query("SELECT s FROM Student s WHERE :course NOT MEMBER OF s.courses")
    List<Student> findByCourseNotContaining(@Param("course") Course course);
}
