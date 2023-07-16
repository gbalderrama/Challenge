package com.universidad.QI.models.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends User{

	@OneToMany(mappedBy = "teacher")
	private List<Course> course;
	


	
}
