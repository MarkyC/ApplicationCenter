package com.github.markyc.applicationcenter;


public class Undergrad extends Student {

	
	/*public Undergrad(Student student, double highSchoolAverage) {
		
		// create from an existing Student
		this( student.getName(), student.getProgram(), highSchoolAverage );
		this.setUniversities( student.getUniversities() );
	}*/

	public Undergrad(String name, String program, double average) {
		super(name, program, average);

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result =  getName() + ", average=" + getAverage() 
				+ ", " + getProgram() + ", Undergrad, ";
		
		if (this.universityOne != null) 
			result += this.universityOne + "-" + (this.universityOneAccept ? "admitted" : "rejected") ;
		if (this.universityTwo != null) 
			result += ", " + this.universityTwo + "-" + (this.universityTwoAccept ? "admitted" : "rejected") ;
		if (this.universityThree != null) 
			result += ", " + this.universityThree + "-" + (this.universityThreeAccept ? "admitted" : "rejected") ;
		
		return result;
	}	
	
	

}
