package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Student extends User {
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Student_course",
	joinColumns = {
			@JoinColumn(name = "student_id", referencedColumnName = "id")
	}, inverseJoinColumns = {
			@JoinColumn(name = "course_id", referencedColumnName = "id")
	})
	private List<Course> courses;

	public Student() {
		super();
	}

	public Student(List<Course> course) {
		super();
		this.courses = course;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> course) {
		this.courses = course;
	}

}
