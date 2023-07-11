package com.universidad.QI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.QI.models.entity.Teacher;
import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Transactional
	public Teacher save(Teacher teacher) {
		return teacherRepository.save(teacher);
	}
	
	@Transactional
	public Teacher save(User user) {
		Teacher teacher = new Teacher();
		teacher.setId(user.getId());
		teacher.setName(user.getName());
		teacher.setLastname(user.getLastname());
		teacher.setUsername(user.getUsername());
		teacher.setPassword(user.getPassword());
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
	
}
