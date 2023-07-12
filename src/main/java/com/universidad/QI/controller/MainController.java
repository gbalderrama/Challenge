package com.universidad.QI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.universidad.QI.repository.CourseRepository;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	CourseRepository courseRepository;
	
	
	@GetMapping({"/","index","","home"})
	public String index() {
		return "index";
	}

	@GetMapping("/cursos")
	public String cursos(Model model) {
		model.addAttribute("cursos", courseRepository.findAll());
	return "cursos.html";
	}
	
	
	
}

