package com.github.markyc.applicationcenter;

import java.util.Iterator;
import java.util.Map.Entry;

public class Postgrad extends Student {
	
	/* Valid undergradMajor values */
	public static final String MASTER = "Master";
	public static final String PHD = "PHD";
	
	private String degree;
	private double undergradAverage;
	
	public Postgrad(Student student, String undergradMajor, double undergradAverage) {
		
		// create from an existing Student
		this( student.getName(), student.getProgram(), undergradMajor, undergradAverage );
		this.setUniversities( student.getUniversities() );
	}

	public Postgrad(String name, String program, String degree, double undergradAverage) {
		super(name, program);

		this.degree = degree;
		this.undergradAverage = undergradAverage;
	}
	
	@Override
	public void addUniversity(String name) {
		if (PHD.equals( this.degree ))
			this.addUniversity(name, undergradAverage >= 80);
		else if (MASTER.equals( this.degree )) {
			this.addUniversity(name, undergradAverage >= 70);
		}
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
				", degree=" + degree + ", undergradAverage=" + undergradAverage + ", universities=[";
		
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
