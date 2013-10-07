package com.github.markyc.applicationcenter;

import java.util.Iterator;
import java.util.Map.Entry;

public class Undergrad extends Student {

	private double highSchoolAverage;
	
	public Undergrad(Student student, double highSchoolAverage) {
		
		// create from an existing Student
		this( student.getName(), student.getProgram(), highSchoolAverage );
		this.setUniversities( student.getUniversities() );
	}

	public Undergrad(String name, String program, double highSchoolAverage) {
		super(name, program);

		this.highSchoolAverage = highSchoolAverage;
	}

	/**
	 * @return the highSchoolAverage
	 */
	public double getHighSchoolAverage() {
		return highSchoolAverage;
	}

	/**
	 * @param highSchoolAverage the highSchoolAverage to set
	 */
	public void setHighSchoolAverage(double highSchoolAverage) {
		this.highSchoolAverage = highSchoolAverage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "Undergrad [" + "name=" + getName() + ", program=" + getProgram() + 
				", highSchoolAverage=" + highSchoolAverage + ", universities=[";
		
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
