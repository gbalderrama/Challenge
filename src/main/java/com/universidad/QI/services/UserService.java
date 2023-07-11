package com.universidad.QI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User save(User user) {

		return userRepository.save(user);
	}

	public List<User> listAll() {
		return userRepository.findAll();
	}
	
	public List<User> listTeacher(){
		return userRepository.findAllTeachers();
		
	}
	
	public List<User> listStudent(){
		return userRepository.findAllStudents();
		
	}

	public Optional<User> findById(String id) {
		return userRepository.findById(id);
		
	}

	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Transactional
	public void deleteById(String id) {

		Optional<User> optional = userRepository.findById(id);

		if (optional.isPresent()) {
			userRepository.delete(optional.get());
		}

	}
}
