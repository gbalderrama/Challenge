package com.universidad.QI.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Student;
import com.universidad.QI.repository.CourseRepository;
import com.universidad.QI.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private StudentService studentService;

	public List<Course> listAll() {
		return courseRepository.findAll();
	}

	@Transactional
	public Course save(Course course) {
//		if (course.getStudents() != null) {
//
//			asignarAlumnosACurso(course);
//		}
		return courseRepository.save(course);
	}

	@Transactional
	public void asignarAlumnosACurso(List<String> alumnos, String course_id)throws Exception {
		// Obtener el curso existente desde la base de datos
		Optional<Course> optional = courseRepository.findById(course_id);

		// Cargar la lista de alumnos a agregar seleccionados desde el repositorio
		// ESTOS SON LOS ALUMNOS SELECCIONADOS
		List<Student> alumnosSeleccionados = studentService.findAllById(alumnos);

		// ESTA ES LA LISTA STRING DE LOS ID DE LOS ALUMNOS SELECCIONADOS
//		List<String> alumnosId = alumnosSeleccionados.stream()
//				.map(Student::getId)
//				.map(String::valueOf)
//                .collect(Collectors.toList());
		// Actualizar la lista de alumnos del curso
		if (optional.isPresent()) {
			Course curso = optional.get();
			for (Student a : alumnosSeleccionados) {

				System.out.println(a.getCourses());
				a.getCourses().add(curso);
				System.out.println(a.getCourses());
				studentService.save(a);

			}
			courseRepository.save(curso);
		}else {
			throw new Exception("No existe el curso seleccionado para agregar los estudiantes");
		}
		// Guardar el curso actualizado en la base de datos
	}

	@Transactional
	public void eliminarCursoDeAlumnos(List<String> alumnos, String course_id) throws Exception {
		// Obtén el curso que deseas eliminar
		Optional<Course> cursoOptional = courseRepository.findById(course_id);
		System.out.println("llega");
		// Obtengo la lista de strings de ID de los estudiantes.
//		List<String> alumnosId = course.getStudents().stream()
//				.map(Student::getId)
//				.map(String::valueOf)
//                .collect(Collectors.toList());

		// Aca obtengo la lista de estudiantes a eliminar
		List<Student> studentsToDelete = studentService.findAllById(alumnos);

		if (cursoOptional.isPresent()) {
			// Curso con estudiantes actuales
			Course curso = cursoOptional.get();

			// Obtén la lista de alumnos que tienen el curso en su lista de cursos del repo
			// actual
			List<Student> alumnos_actuales = studentService.findByCursosContaining(curso);

			// Elimina el curso de la lista de cursos de cada alumno
			for (Student alumno : alumnos_actuales) {
				alumno.getCourses().removeIf(c -> c.getId().equals(curso.getId()) && studentsToDelete.contains(alumno));
			}

			// Guarda los cambios actualizados en el repositorio de alumnos
			studentService.saveAll(alumnos_actuales);
		}
	}

	@Transactional
	public List<Course> saveByStudent(List<Course> courses) {
		List<Course> devuelvo = new ArrayList<>();

		for (Course curso : courses) {
			Optional<Course> optional = courseRepository.findById(curso.getId());
			if (optional.isPresent()) {
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

		if (optional.isPresent()) {
			courseRepository.delete(optional.get());
		}
	}

	public Course findByID(String id) throws Exception {
		Optional<Course> optional = courseRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new Exception("Curso no encontrado");
		}
	}

}
