package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Teacher extends User{

	@OneToMany(mappedBy = "teacher")
	@Getter @Setter private List<Course> course;

	public Teacher(List<Course> course) {
		super();
		this.course = course;
	}
	public Teacher() {
		
	}
	/**
	 * @return the course
	 */
	public List<Course> getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(List<Course> course) {
		this.course = course;
	}
	


	
}
