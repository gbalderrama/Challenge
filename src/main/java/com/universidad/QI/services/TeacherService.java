package com.universidad.QI.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Teacher;
import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.CourseRepository;
import com.universidad.QI.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Transactional
	public Teacher save(Teacher teacher) {
		return teacherRepository.save(teacher);
	}
	
	@Transactional
	public Teacher save(User user) {
		Teacher teacher = new Teacher();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		teacher.setId(user.getId());
		teacher.setName(user.getName());
		teacher.setLastname(user.getLastname());
		teacher.setDni(user.getDni());
		teacher.setUsername(user.getUsername());
		teacher.setPassword(encoder.encode(user.getPassword()));
		teacher.setRole(user.getRole());
		return teacherRepository.save(teacher);
	}
	
	@Transactional
	public Teacher saveByCourse(Teacher teacher) {
		Optional<Teacher> optional = teacherRepository.findById(teacher.getId());
		if(optional.isPresent()) {
			teacher = optional.get();
		}
		
		return save(teacher);
	}
	
	public List<Teacher> findAll(){
		return teacherRepository.findAll();
	}
	
	public Teacher findById(Teacher teacher) {
		Optional<Teacher> optional = teacherRepository.findById(teacher.getId());
		if(optional.isPresent()) {
			teacher = optional.get();
		}
		return teacher;
	}
	public Teacher findById(String id) throws Exception {
		Optional<Teacher> optional = teacherRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			throw new Exception("No se encuentra el profe");
		}
	}
	
	public List<Course> findAssigned(String id){
		Optional<Teacher> optional = teacherRepository.findById(id);
		List<Course> cursos = new ArrayList<>();
		if(optional.isPresent()) {
			cursos = courseRepository.findassingned(optional.get().getId());
		}
		return cursos;
	}

	public void delete(Teacher teacher) {
		teacherRepository.delete(teacher);
		
	}
	
}
