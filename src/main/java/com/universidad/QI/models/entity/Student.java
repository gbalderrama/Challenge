package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

@Entity
public class Student extends User{
	
	@OneToMany
	private List<Course> course;

	public Student(List<Course> cursos) {
		super();
		this.course = cursos;
	}

	public Student() {
		super();
	}

	public List<Course> getCursos() {
		return course;
	}

	public void setCursos(List<Course> cursos) {
		this.course = cursos;
	}

}
