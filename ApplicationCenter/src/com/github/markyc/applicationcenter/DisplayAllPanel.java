package com.github.markyc.applicationcenter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplayAllPanel extends JPanel implements CardPanel {

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
		this.field = new JTextArea("No Students currently in the database.");
		this.field.setEditable( false );
		this.field.setFont(new Font( "Serif", Font.ITALIC, 12 )); // 12pt Serif Font in italic
		
		this.add( new JScrollPane( this.field ), BorderLayout.CENTER );
	}

	@Override
	public String getCardName() {
		return CARD_NAME;
	}

	public void setStudents(List<Student> students) {
		
		// Create text out of Students in the List
		String text = "";
		for (Student s : students) text += s + "\n";
		
		// Set the text of the field to the Students from the List
		this.field.setText(text);
		
		// Call for redraw
		this.revalidate();
		this.repaint(); 
	}

}
