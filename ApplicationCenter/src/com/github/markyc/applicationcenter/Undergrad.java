package com.github.markyc.applicationcenter;

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

}
