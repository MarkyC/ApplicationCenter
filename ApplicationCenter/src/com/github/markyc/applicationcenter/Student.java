package com.github.markyc.applicationcenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Student {

	private String name;
	private String program;
	
	// This maps a University Name to whether the Student has been accepted.
	protected Map<String, Boolean> universities;
	
	public Student() {
		// empty name and program
		this("", "");
	}

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
	 * @return the universities
	 */
	public Map<String, Boolean> getUniversities() {
		return universities;
	}

	/**
	 * @param universities the universities to set
	 */
	public void setUniversities(Map<String, Boolean> universities) {
		this.universities = universities;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result =  "Student [name=" + name + ", program=" + program
				+ ", universities=[";
		
		// Add universities in String format
		Iterator<String> it = universities.keySet().iterator();
		while (it.hasNext()) {
			result += it.next();
			if ( it.hasNext() ) result += ", ";
		}
		
		result += "]]";
		
		return result;
	}

	
}
