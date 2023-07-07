package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity 
public class Teacher extends User{

	@OneToMany
	private List<Course> cursos;
	

	public Teacher() {
		super();
	}

	public Teacher(List<Course> cursos) {
		super();
		this.cursos = cursos;
	}

	public List<Course> getCursos() {
		return cursos;
	}

	public void setCursos(List<Course> cursos) {
		this.cursos = cursos;
	}
	
}
