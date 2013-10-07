package com.github.markyc.applicationcenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public boolean hasUniversity(String name) {
		return this.universities.containsKey(name);
	}
	
	public void addUniversity(String name) {
		
		// When a university is added, the user is not initially accepted to it.
		this.addUniversity(name, false);
		
	}
	
	public void addUniversity(String name, boolean isAccepted) {
		
		this.universities.put( name, isAccepted );
		
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
		Iterator<Entry<String, Boolean>> it = this.universities.entrySet().iterator();
		while (it.hasNext()) {
			
			Entry<String, Boolean> entry = it.next();
			result += entry.getKey() + ": " + (entry.getValue() ? "accepted" : "rejected");
			if ( it.hasNext() ) result += ", ";
		}
		
		result += "]]";
		
		return result;
	}	
}
