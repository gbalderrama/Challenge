package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.Entity;

@Entity 
public class Teacher extends User{

	private List<Course> cursos;
	
}
