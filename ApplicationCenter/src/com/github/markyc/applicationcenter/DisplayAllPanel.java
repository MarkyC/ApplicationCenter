package com.github.markyc.applicationcenter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplayAllPanel extends JPanel implements CardPanel {

	private static final String NO_STUDENTS = "No Students currently in the database.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6800780775354459318L;
	public static final String CARD_NAME = "displayallpanel";
	private JTextArea field;
	
	public DisplayAllPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		// the only field in this window displays the toString() of all Students
		// it is populated via the setStudents() method
		this.field = new JTextArea( NO_STUDENTS );
		this.field.setEditable( false );
		this.field.setFont(new Font( "Serif", Font.ITALIC, 12 )); // 12pt Serif Font in italic
		
		this.add( new JScrollPane( this.field ), BorderLayout.CENTER );
	}

	@Override
	public String getCardName() {
		return CARD_NAME;
	}

	//public void setStudents(List<Student> students) {
	public void setStudents(Student[] students, int numStudents) {
		
		// BubbleSort is a static inner class
		if (numStudents > 1) {
			students = BubbleSort.sort(students, Student.AverageComparator);
		}
		
		// Create text out of Students in the List
		String text = "";
		for ( int i = 0; i < numStudents; i++ ) {
			Student s = students[i]; 
			text += s + "\n";
		}
		
		// Set the text of the field to the Students from the List
		this.field.setText(text);
		
		// Call for redraw
		this.revalidate();
		this.repaint(); 
	}
	
	public static class BubbleSort {
		public static <T> T[] sort(T[] objs, Comparator<T> compare) {
			T[] result = objs;
			
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result.length; j++) {
					
					if (compare.compare( result[i], result[j] ) > 0) {
						T temp = result[i];
						result[i] = result[j];
						result[j] = temp;
					} 
				}
			}
			
			return result;
		}
	}

}
