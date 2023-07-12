package com.universidad.QI.Enums;



public enum Role {

	ADMIN("ADMIN"),
	TEACHER("PROFESOR"),
	STUDENT("ALUMNO");
	
	private final String rol;
	
	Role(String rol){
		this.rol = rol;
	}
	
	public String getRole() {
		return rol;
	}
	
}
