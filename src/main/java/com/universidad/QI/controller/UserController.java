package com.universidad.QI.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.universidad.QI.Enums.Role;
import com.universidad.QI.Enums.Shift;
import com.universidad.QI.models.entity.Course;
import com.universidad.QI.models.entity.Student;
import com.universidad.QI.models.entity.Teacher;
import com.universidad.QI.models.entity.User;
import com.universidad.QI.repository.CourseRepository;
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
		model.addAttribute("usuarios", userService.listAll());
		model.addAttribute("roles", Role.values());
		return "panel";
	};

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

	@PostMapping("/add_curso")
	public String addCourse(@ModelAttribute Course course) {

		courseService.save(course);

		return "redirect:/panel";
	}

	@GetMapping("/edit_course")
	public String edit_course(Model model, @RequestParam String id) {
		if (id != null) {
			Optional<Course> optional = courseService.findByID(id);
			if (optional.isPresent()) {
				Course curso = optional.get();
				model.addAttribute("course", curso);
				model.addAttribute("shifts", Shift.values());
				model.addAttribute("students", studentService.listAllStudents());
				model.addAttribute("teachers", teacherService.findAll());
			}
		}

		return "edit_curso.html";
	}

}
