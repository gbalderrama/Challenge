package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity 
public class Teacher extends User{

	@OneToMany
	private List<Course> course;
	

	public Teacher() {
		super();
	}

	public Teacher(List<Course> cursos) {
		super();
		this.course = cursos;
	}

	public List<Course> getCursos() {
		return course;
	}

	public void setCursos(List<Course> cursos) {
		this.course = cursos;
	}
	
}
