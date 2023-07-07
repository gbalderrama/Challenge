package com.universidad.QI.models.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Course {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy ="uuid2")
	private String id;
	private String name;
	private String shift;
	@OneToMany
	private List<Teacher> teachers;
	@OneToMany
	private List<Student> students;
	
	public Course() {
		
	}
	
	public Course(String id, String name, String shift, List<Teacher> teachers, List<Student> students) {
		super();
		this.id = id;
		this.name = name;
		this.shift = shift;
		this.teachers = teachers;
		this.students = students;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	
	
	
}
