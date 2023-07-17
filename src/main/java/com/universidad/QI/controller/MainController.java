package com.universidad.QI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.universidad.QI.repository.CourseRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	CourseRepository courseRepository;

	@GetMapping({ "/", "index", "", "home" })
	public String index(@RequestParam(required = false) HttpSession session, Model model) {
		if(session != null) {
			User logueado = (User) session.getAttribute("userSession");
			model.addAttribute("logueado", logueado);
			if (logueado.getRoles().toString().equals("ADMIN")) {
				return "redirect:/panel";
			}
		}
		

		

		return "index";
	}

	@GetMapping("/cursos")
	public String cursos(Model model) {
		model.addAttribute("cursos", courseRepository.findAll());
		return "cursos.html";
	}

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, Model model) {
		System.out.println("llega al login");
		if (error != null) {
			model.addAttribute("error", "Usuario o contraseña invalido");
		}
		return "login";
	}

}
