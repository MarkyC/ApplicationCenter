package com.github.markyc.applicationcenter;

import java.util.HashMap;
import java.util.Map;

public class Student {

	private String name;
	private String program;
	
	// This maps a University Name to whether the Student has been accepted.
	private Map<String, Boolean> universities;

	public Student(String name, String program) {
		
		this.name = name;
		this.program = program;
		
		this.universities = new HashMap<String, Boolean>();
	}
	
	public void addUniversity(String name) {
		
		// When a university is added, the user is not initially accepted to it.
		this.universities.put( name, false );
		
	}
	
	public void removeUniversity(String name) {
		
		this.universities.remove(name);
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the program
	 */
	public String getProgram() {
		return program;
	}

	/**
	 * @param program the program to set
	 */
	public void setProgram(String program) {
		this.program = program;
	}

	
}
