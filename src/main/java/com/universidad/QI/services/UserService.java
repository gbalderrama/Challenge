package com.universidad.QI.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.universidad.QI.Enums.Role;
import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.StudentRepository;
import com.universidad.QI.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;

	@Transactional
	public User save(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
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
	public void deleteById(String id) throws Exception {

		Optional<User> optional = userRepository.findById(id);

		if (optional.isPresent()) {
			if(optional.get().getRole().toString() == "STUDENT") {
				studentService.delete(studentService.findById(optional.get().getId()));
			}
			
			userRepository.delete(optional.get());
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user != null) {
			List<GrantedAuthority> permiso = new ArrayList<>();
			//Asigno el permiso del usuario
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+user.getRole());
			
			permiso.add(p);
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			
			HttpSession session = attr.getRequest().getSession(true);
			
			session.setAttribute("userSession", user);
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), permiso);
		}
		
		return null;
	}

	
	
}
