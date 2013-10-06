package com.github.markyc.applicationcenter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class AdmitPanel extends JPanel implements CardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1871018853543598682L;
	public static final String CARD_NAME = "admitpanel";
	
	private static final String[] NO_STUDENTS = { "There are no students currently in the database" };
	private List<Student> students;
	private JComboBox<String> comboBox;

	public AdmitPanel() {
		super();
		
		this.students = new ArrayList<Student>();
		this.comboBox = new JComboBox<String>(NO_STUDENTS);
		
		this.add( this.comboBox );
	}

	@Override
	public String getCardName() {
		return CARD_NAME;
	}

	public void setStudents(List<Student> students) {

		// Update students variable
		this.students = students;
		
		// get names of all students and store in a string array
		String studentNames[] = new String[this.students.size()];
		for ( int i = 0; i < studentNames.length; i++ ) studentNames[i] = this.students.get(i).getName();
		
		// Set model for the combobox to be the students names
		this.comboBox.setModel(new DefaultComboBoxModel<String>(studentNames));
	}
}
