package com.universidad.QI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.universidad.QI.repository.CourseRepository;
import com.universidad.QI.repository.TeacherRepository;
import com.universidad.QI.repository.UserRepository;
import com.universidad.QI.services.CourseService;
import com.universidad.QI.services.TeacherService;


@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private CourseService courseService;
	
	
	@GetMapping("/{teacher_id}")
	public String teacherPanel(Model model,@PathVariable String teacher_id) {
		
		model.addAttribute("cursos", courseService.listAll());
		model.addAttribute("cursos_asignados", teacherService.findAssigned(teacher_id));
		
		return "tPanel";
	}
	
	@PostMapping("/assign")
	public void assign(Model model, String id) {
		
	}
	
	
}
