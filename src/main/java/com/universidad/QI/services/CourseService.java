package com.universidad.QI.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.QI.models.entity.Course;
import com.universidad.QI.repository.CourseRepository;

import jakarta.transaction.Transactional;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentService studentService;
	
	
	public List<Course> listAll(){
		return courseRepository.findAll();
	}
	
	@Transactional
	public Course save(Course course) {
		
		return courseRepository.save(course);
	}
	
	
	@Transactional
	public List<Course> saveByStudent(List<Course> courses) {
		List<Course> devuelvo = new ArrayList<>();
		
		for(Course curso : courses) {
			Optional<Course> optional = courseRepository.findById(curso.getId());
			if(optional.isPresent()) {
				curso = optional.get();
			}
			devuelvo.add(curso);
			save(curso);
		}
		return devuelvo;
	}
	
	@Transactional
	public void delete(Course course) {
		courseRepository.delete(course);
	}
	
	@Transactional
	public void deleteById(String id) {
		Optional<Course> optional = courseRepository.findById(id);
		
		if(optional.isPresent()) {
			courseRepository.delete(optional.get());
		}
	}
	
	public Optional<Course> findByID(String id) {
		return courseRepository.findById(id);
	}
	
}
