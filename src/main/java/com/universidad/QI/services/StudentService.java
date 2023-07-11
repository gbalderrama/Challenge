package com.universidad.QI.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Student;
import com.universidad.QI.models.entity.Teacher;
import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.CourseRepository;
import com.universidad.QI.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;
	
	
	@Transactional
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	@Transactional
	public Student save(User user) {
		Student student = new Student();
		student.setId(user.getId());
		student.setName(user.getName());
		student.setLastname(user.getLastname());
		student.setUsername(user.getUsername());
		student.setPassword(user.getPassword());
		student.setRole(user.getRole());
		return studentRepository.save(student);
	}

	@Transactional
	public List<Student> saveByCourse(List<Student> students) {
		
		List<Student> devuelvo = new ArrayList<Student>();
		
		for (Student alumno : students) {
			Optional<Student> optional = studentRepository.findById(alumno.getId());
			if (optional.isPresent()) {
				alumno = optional.get();
			}
			devuelvo.add(alumno);
			save(alumno);
		}
		return devuelvo;
	}
	
	@Transactional
	public void addCursePerUser(Course course){
		Optional<Course> optional = courseRepository.findById(course.getId());
		
		if(optional.isPresent()) {
			course = optional.get();
			for (Student student : course.getStudents()) {
				Optional<Student> optionalS = studentRepository.findById(student.getId());
				if	(optionalS.isPresent()) {
					student = optionalS.get();
					student.getCursos().add(course);
				}
			}
		}
	}

	@Transactional
	public void delete(Student student) {
		studentRepository.delete(student);
	}

	public List<Student> listAllStudents() {
		return studentRepository.findAll();
	}
	
	
	public List<Student> listAvailable(String id) {
		Optional<Course> optional = courseRepository.findById(id);
		List<Student> lista = new ArrayList<>();
		
		if(optional.isPresent()) {
		
			
			lista = studentRepository.studentAvailable(optional.get().getId());
		}
		return lista;
	}



}
