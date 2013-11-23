package com.github.markyc.applicationcenter;

import java.util.Comparator;

public class Student {

	private String name;
	private String program;
	
	// This maps a University Name to whether the Student has been accepted.
	//protected Map<String, Boolean> universities;
	protected String universityOne;
	protected String universityTwo;
	protected String universityThree;
	protected boolean universityOneAccept;
	protected boolean universityTwoAccept;
	protected boolean universityThreeAccept;
	private double average;
	
	public Student() {
		// empty name and program
		this("", "", 0);
	}

	public Student(String name, String program, double average) {
		
		this.name = name;
		this.program = program;
		this.average = average;
		
		//this.universities = new HashMap<String, Boolean>();
		this.universityOneAccept = false;
		this.universityTwoAccept = false;
		this.universityThreeAccept = false;
	}
 
	public static Comparator<Student> AverageComparator = new Comparator<Student>() {
 
	    public int compare(Student a, Student b) {
	    	
	    	int avgA = (int) a.getAverage();
	    	int avgB = (int) b.getAverage();
 
	    	return ( avgA < avgB ? -1 : ( avgA == avgB ? 0 : 1 ) );
	    }
 
	};
	
	/*public boolean hasUniversity(String name) {
		return this.universities.containsKey(name);
	}*/
	
	public void addUniversity(String name) {
				
		// When a university is added, the user is not initially accepted to it.
		this.addUniversity(name, false);
		
	}
	
	public void addUniversity(String name, boolean isAccepted) {
		
		//this.universities.put( name, isAccepted );
		
		if (( this.universityOne == null ) || ( this.universityOne.equals(name) )) {
			this.universityOne = name;
			this.universityOneAccept = isAccepted;
		} else if (( this.universityTwo == null ) || ( this.universityTwo.equals(name) )) {
			this.universityTwo = name;
			this.universityTwoAccept = isAccepted;
		} else if (( this.universityThree == null ) || ( this.universityThree.equals(name) )) {
			this.universityThree = name;
			this.universityThreeAccept = isAccepted;
		} else {
			throw new RuntimeException("Too many universities chosen!");
		}
		
		
	}
	
	/*public void removeUniversity(String name) {
		
		this.universities.remove(name);
		
	}*/
	
	/**
	 * @return the universities
	 */
	//public Map<String, Boolean> getUniversities() {
	public String[] getUniversities() {
		if ( this.universityTwo == null ) {
			return new String[] { this.universityOne };
		} else if ( this.universityThree == null ) {
			return new String[] {this.universityOne, this.universityTwo};
		} 
			
		return new String[] {this.universityOne, this.universityTwo, this.universityThree};
	}
	
	public boolean[] getUniversitiesAccept() {
		return new boolean[] {this.universityOneAccept, this.universityTwoAccept, this.universityThreeAccept};
	}


	/**
	 * @param universities the universities to set
	 */
	/*public void setUniversities(Map<String, Boolean> universities) {
		this.universities = universities;
	}*/

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
	
	/**
	 * @return the average
	 */
	public double getAverage() {
		return average;
	}

	/**
	 * @param undergradAverage the undergradAverage to set
	 */
	public void setAverage(double average) {
		this.average = average;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result =  "Student [name=" + getName() + ", average=" + getAverage() 
				+ ", program=" + getProgram() + ", universities=[";
		
		
		if (this.universityOne != null) 
			result += this.universityOne + ": " + (this.universityOneAccept ? "accepted" : "rejected") ;
		if (this.universityTwo != null) 
			result += ", " + this.universityTwo + ": " + (this.universityTwoAccept ? "accepted" : "rejected") ;
		if (this.universityThree != null) 
			result += ", " + this.universityThree + ": " + (this.universityThreeAccept ? "accepted" : "rejected") ;
		
		result += "]]";
		
		return result;
	}	
}
