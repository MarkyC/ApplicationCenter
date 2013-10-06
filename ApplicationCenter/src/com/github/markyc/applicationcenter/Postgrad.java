package com.github.markyc.applicationcenter;

import java.util.Iterator;

public class Postgrad extends Student {
	
	/* Valid undergradMajor values */
	public static final String MASTER = "master";
	public static final String PHD = "phd";
	
	private String undergradMajor;
	private double undergradAverage;
	
	public Postgrad(Student student, String undergradMajor, double undergradAverage) {
		
		// create from an existing Student
		this( student.getName(), student.getProgram(), undergradMajor, undergradAverage );
		this.setUniversities( student.getUniversities() );
	}

	public Postgrad(String name, String program, String undergradMajor, double undergradAverage) {
		super(name, program);

		this.undergradMajor = undergradMajor;
		this.undergradAverage = undergradAverage;
	}

	/**
	 * @return the undergradMajor
	 */
	public String getUndergradMajor() {
		return undergradMajor;
	}

	/**
	 * @param undergradMajor the undergradMajor to set
	 */
	public void setUndergradMajor(String undergradMajor) {
		this.undergradMajor = undergradMajor;
	}

	/**
	 * @return the undergradAverage
	 */
	public double getUndergradAverage() {
		return undergradAverage;
	}

	/**
	 * @param undergradAverage the undergradAverage to set
	 */
	public void setUndergradAverage(double undergradAverage) {
		this.undergradAverage = undergradAverage;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "Postgrad [" + "name=" + getName() + ", program=" + getProgram() + 
				", undergradMajor=" + undergradMajor + ", undergradAverage=" + undergradAverage + ", universities=[";
		
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
