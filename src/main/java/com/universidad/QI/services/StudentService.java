package com.universidad.QI.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Student student = new Student();
		student.setId(user.getId());
		student.setName(user.getName());
		student.setDni(user.getDni());
		student.setLastname(user.getLastname());
		student.setUsername(user.getUsername());
		student.setPassword(encoder.encode(user.getPassword()));
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
					Student tempStudent = optionalS.get();
					tempStudent.getCourses().add(course);
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
		
			
			//lista = studentRepository.studentAvailable(optional.get().getId());
		}
		return lista;
	}
	public List<Student> findAllById(List<String> collect) {
		
		return studentRepository.findAllById(collect);
	}
	
	
	public Student findById(String student_id) throws Exception {
		if (studentRepository.findById(student_id).isPresent()) {
			
			return studentRepository.findById(student_id).get();
		}else {
			throw new Exception("No se encuentra el estudiante indicado");
		}
	}

	//Busca todos los estudiantes de un curso
	
	public List<Student> findAllByCoursesId(String id) throws Exception{
		List<Student> lista = studentRepository.findAllByCoursesId(id);
		if(!lista.isEmpty()) {
			return lista;
		}else {
			throw new Exception("No tiene alumnos asignados");
		}
	}

	public List<Student> findByCursosContaining(Course course) throws Exception{
		Optional<Course> optional = courseRepository.findById(course.getId());
		List<Student> lista = new ArrayList<>();
		if(optional.isPresent()) {
			lista = studentRepository.findAllByCoursesId(optional.get().getId());
			return lista;
			
		}else {
			throw new Exception("no existe el curso indicado");
		}
	}
	
	public List<Student> findByCourseNotContaining(String id) throws Exception{
		Optional<Course> optional = courseRepository.findById(id);
		if(optional.isPresent()) {
			
			return studentRepository.findByCourseNotContaining(optional.get());
		}else {
			throw new Exception("El curso es invalido");
		}
	}
	
	public List<Student> saveAll(List<Student> alumnos){
		return studentRepository.saveAll(alumnos);
	}

}
