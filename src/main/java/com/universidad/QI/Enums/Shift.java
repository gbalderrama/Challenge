package com.universidad.QI.Enums;


public enum Shift {
	
	MAÑANA("MAÑANA"),
	TARDE("TARDE"),
	NOCHE("NOCHE");
	
	private final String turno;
	
	Shift(String turno){
		this.turno = turno;
	}
	
	public String getShift() {
		return turno;
	}

}
