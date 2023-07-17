package com.universidad.QI.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.universidad.QI.Enums.Role;
import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Student;
import com.universidad.QI.models.entity.Teacher;
import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.StudentRepository;
import com.universidad.QI.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private CourseService courseService;

	@Transactional
	public User save(User user) {
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(hashedPassword);
		return userRepository.save(user);
	}

	public List<User> listAll() {
		return userRepository.findAll();
	}

	public List<User> listTeacher() {
		return userRepository.findAllTeachers();

	}

	public List<User> listStudent() {
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

		// PARA ELIMINAR UN USUARIO HAY QUE VERIFICAR SI ES ESTUDIANTE, PROFESOR O ADMIN
		// YA QUE SI ES ESTUDIANTE O PROFESOR PUEDEN TENER RELACIONADOS CURSOS EN LA
		// BASE DE DATOS
		// Y ES NECESARIO ELIMINAR ESA RELACION PARA LUEGO PODER ELIMINAR EL USUARIO.

		if (optional.isPresent()) {
			
			// VERIFICO STUDENT
			if (optional.get().getRole().toString() == "STUDENT") {
				Student student = studentService.findById(optional.get().getId());
				if (!student.getCourses().isEmpty()) {
					student.getCourses().clear();
					studentService.save(student);
					studentService.delete(student);
				}

			}
			// VERIFICO PROFESOR
			if (optional.get().getRole().toString() == "TEACHER") {
				Teacher teacher = teacherService.findById(optional.get().getId());

				if (!teacher.getCourse().isEmpty()) {

					for (Course c : teacher.getCourse()) {
						Course ce = courseService.findByID(c.getId());
						ce.setTeacher(null);
						courseService.save(ce);
					}
					teacher.getCourse().clear();
					teacherService.save(teacher);
					teacherService.delete(teacher);
				}
				teacherService.delete(teacher);
			}
			userRepository.deleteById(optional.get().getId());
		}
	}//FIN DELETE
	
	
	
	

}
