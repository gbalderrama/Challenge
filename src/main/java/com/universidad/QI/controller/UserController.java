package com.universidad.QI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.universidad.QI.Enums.Role;
import com.universidad.QI.Enums.Shift;
import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Student;
import com.universidad.QI.models.entity.Teacher;
import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.CourseRepository;
import com.universidad.QI.repository.StudentRepository;
import com.universidad.QI.services.CourseService;
import com.universidad.QI.services.StudentService;
import com.universidad.QI.services.TeacherService;
import com.universidad.QI.services.UserService;

@Controller
@RequestMapping("/panel")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseService courseService;

	
	//Muestro todos los usuarios de todos los roles y los cursos creados
	
	@GetMapping("")
	public String listUser(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("usuarios", userService.listAll());
		model.addAttribute("roles", Role.values());

		model.addAttribute("course", new Course());
		model.addAttribute("cursos", courseService.listAll());
		model.addAttribute("teachers", userService.listTeacher());
		model.addAttribute("students", userService.listStudent());

		model.addAttribute("shifts", Shift.values());

		return "panel.html";
	};
	
	@GetMapping("/course_users/{course_id}")
	//@ResponseBody
	public String courseUsers(Model model,@PathVariable String course_id) throws Exception {
		//Lista de todos los alumnos
		model.addAttribute("alumnos", studentService.findByCourseNotContaining(course_id));
		
		//Trae el curso a editar
		model.addAttribute("course", courseService.findByID(course_id));
		try {
			//Busca a todos los alumnos con el curso a editar
			model.addAttribute("asignados", studentService.findAllByCoursesId(course_id));
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		model.addAttribute("curso_actual", course_id);
		
		return "course_students.html";
	}
	
	

	
	//************************************************************************
	// Para editar y guardar los usuarios, traigo el usuario a modificar 
	// Envio la generacion o modificacion del usuario o curso
	@GetMapping("/edit")
	public String edit(Model model, @RequestParam(required = false) String id) {

		if (id != null) {
			Optional<User> optional = userService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("user", optional.get());
			}

		} else {
			model.addAttribute("user", new User());

		}
		model.addAttribute("cursos", courseService.listAll());
		model.addAttribute("usuarios", userService.listAll());
		model.addAttribute("roles", Role.values());
		return "panel";
	};
	
	@GetMapping("/edit_course")
	public String edit_course(Model model, @RequestParam String id) throws Exception {
				model.addAttribute("course", courseService.findByID(id));
				model.addAttribute("shifts", Shift.values());
				try {
					
					model.addAttribute("students", studentService.listAllStudents());
				} catch (Exception e) {
					model.addAttribute("error", e);
				}
				model.addAttribute("teachers", teacherService.findAll());
		

		return "edit_curso.html";
	}
	
	
	@PostMapping("/add_user")
	public String saveUser(@ModelAttribute User user) {
		if (user.getRole().equals(Role.ADMIN)) {
			userService.save(user);
		}
		if (user.getRole().equals(Role.STUDENT)) {
			studentService.save(user);
		}
		if (user.getRole().equals(Role.TEACHER)) {
			teacherService.save(user);
		}
		return "redirect:/panel";
	}
	
	@PostMapping("/add_curso")
	public String addCourse(@ModelAttribute Course course) {
		courseService.save(course);
		return "redirect:/panel";
	}
	@PostMapping("/add_student_curso/{id_course}")
	public String addStudentCourse(@RequestParam(required = true) List<String> agrega_alumnos,@PathVariable String id_course) throws Exception{
		courseService.asignarAlumnosACurso(agrega_alumnos, id_course);
		return "redirect:/panel/course_users/"+id_course;
	}
	

	
	
	
	//****************************************************************
	// Eliminacion del usuario
	
	@GetMapping("delete")
	public String delete(@RequestParam(required = true) String id) {

		userService.deleteById(id);

		return "redirect:/panel";
	}

	@GetMapping("delete_course")
	public String delete_course(@RequestParam(required = true) String id) {

		courseService.deleteById(id);

		return "redirect:/panel";
	}
	
	@PostMapping("/delete_student_from_course/{id_course}")
	public String delete_student_from_course(@RequestParam(required = true) List<String> alumnos,@PathVariable String id_course ) throws Exception {
		System.out.println(id_course);
		System.out.println(alumnos);
		courseService.eliminarCursoDeAlumnos(alumnos, id_course);
		return "redirect:/panel/course_users/"+id_course;
	}
	

	

}
