package org.agecraft.core.science;

public enum Phase {
	
	SOLID("solid"),
	LIQUID("liquid"),
	GAS("gas");
	
	public String name;
	
	Phase(String name) {
		this.name = name;
	}
}
