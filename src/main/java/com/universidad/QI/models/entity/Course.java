package com.universidad.QI.models.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.universidad.QI.Enums.Role;
import com.universidad.QI.Enums.Shift;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Course {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy ="uuid2")
	private String id;
	private String name;
	@Enumerated(EnumType.STRING)
	private Shift shift;
	 
	@ManyToOne
	private Teacher teacher;
	
	@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
	private List<Student> students;
	
	public Course() {
		
	}
	
	public Course(String id, String name, Shift shift, Teacher teacher, List<Student> students) {
		super();
		this.id = id;
		this.name = name;
		this.shift = shift;
		this.teacher = teacher;
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

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	
	
	
}
