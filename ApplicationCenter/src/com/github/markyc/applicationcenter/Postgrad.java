package com.github.markyc.applicationcenter;


public class Postgrad extends Student {
	
	/* Valid undergradMajor values */
	public static final String MASTER = "Master";
	public static final String PHD = "PHD";
	
	private String degree;

	public Postgrad(String name, String program, String degree, double average) {
		super(name, program, average);

		this.degree = degree;
	}
	
	@Override
	public void addUniversity(String name) {
		if (PHD.equals( this.degree ))
			this.addUniversity(name, getAverage() >= 80);
		else if (MASTER.equals( this.degree )) {
			this.addUniversity(name, getAverage() >= 70);
		}
	}
	
	/**
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * @param degree the degree to set
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result =  getName() + ", average=" + getAverage() 
				+ ", " + getProgram() + ", " + getDegree() + ", ";
		
		if (this.universityOne != null) 
			result += this.universityOne + "-" + (this.universityOneAccept ? "admitted" : "rejected") ;
		if (this.universityTwo != null) 
			result += ", " + this.universityTwo + "-" + (this.universityTwoAccept ? "admitted" : "rejected") ;
		if (this.universityThree != null) 
			result += ", " + this.universityThree + "-" + (this.universityThreeAccept ? "admitted" : "rejected") ;
		
		return result;
	}	
	

}
