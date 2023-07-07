package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Student extends User{
	
	@OneToMany
	private List<Course> cursos;

	public Student(List<Course> cursos) {
		super();
		this.cursos = cursos;
	}

	public Student() {
		super();
	}

	public List<Course> getCursos() {
		return cursos;
	}

	public void setCursos(List<Course> cursos) {
		this.cursos = cursos;
	}

}
