package com.github.markyc.applicationcenter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdmitPanel extends JPanel implements CardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID 	= -1871018853543598682L;
	public static final String CARD_NAME 		= "admitpanel";
	
	private static final String SELECT_STUDENT 		= "Please select a student to view their admission status";
	private static final String SELECT_STUDENT_LIST = "Please select a student from the list above";
	private static final String[] NO_STUDENTS 		= { "There are no students currently in the database" };
	
	private static final String ACCEPT = "Accept";
	private static final String REJECT = "Reject";

	
	/* Combo box index is students index + 1 
	 * because the first combobox item is "please select a student..." */
	private List<Student> students;
	private JComboBox<String> comboBox;
	
	private JPanel studentInfoPanel;
	
	Map<ButtonGroup, String> fields;
	private Student currentStudent;
	
	private List<ChangeListener> listeners;
	

	public AdmitPanel() {
		super();
		
		this.students 	= new ArrayList<Student>();
		this.fields 	= new HashMap<ButtonGroup, String>();
		this.listeners	= new ArrayList<ChangeListener>();
		
		this.comboBox 	= new JComboBox<String>(NO_STUDENTS);
		this.comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JComboBox<?> box = (JComboBox<?>) e.getSource(); 
				
				// attempt to show the student, showing an empty ("please select a student...") panel on error
				try {
					AdmitPanel.this.showStudent(students.get( box.getSelectedIndex() - 1 ));
				} catch (Exception ex) {
					AdmitPanel.this.createEmptyInfoPanel();
				}
				
			}
			
		});
				
		this.studentInfoPanel = new JPanel();
		this.createEmptyInfoPanel();
		
		this.setLayout(new BorderLayout());
		this.add( this.comboBox, BorderLayout.NORTH );
		this.add( this.studentInfoPanel, BorderLayout.CENTER );
	}

	private JPanel createEmptyInfoPanel() {
		
		// Remove all previous Components from the panel
		this.studentInfoPanel.removeAll();
		
		this.studentInfoPanel.setLayout(new BoxLayout( this.studentInfoPanel, BoxLayout.X_AXIS ));
		
		this.studentInfoPanel.add(Box.createHorizontalGlue());
		this.studentInfoPanel.add( new JLabel(SELECT_STUDENT_LIST) );
		this.studentInfoPanel.add(Box.createHorizontalGlue());
		
		// Force the window to redraw itself to reflect the new changes
		this.revalidate();
		this.repaint();
		
		return this.studentInfoPanel;
	}
	
	private void showStudent(Student student) {
		
		this.currentStudent = student;
		
		// Remove all previous Components from the panel
		this.studentInfoPanel.removeAll();
		this.studentInfoPanel.setLayout(new BorderLayout());
		
		/* name and graduate type panel */
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));

		// The students name, in large Font
		JLabel nameLabel = new JLabel(student.getName());
		nameLabel.setFont( nameLabel.getFont().deriveFont(24f) );

		// Shows whether the Student is an Undergrad or Postgrad
		// If the Student is Postgrad, (Master) or (PHD) will be show
		String gradType = student.getClass().getSimpleName();
		if (student instanceof Postgrad) gradType += " (" + ((Postgrad) student).getDegree() + ")";
		JLabel gradLabel = new JLabel(gradType);
		gradLabel.setFont( gradLabel.getFont().deriveFont(16f));
		gradLabel.setForeground(Color.GRAY);

		
		// Force the window to redraw itself to reflect the new changes
		namePanel.add(nameLabel);
		namePanel.add(Box.createHorizontalStrut(10));
		namePanel.add(gradLabel);
				
		/* University choice panel */
		
		JPanel universityPanel = new JPanel();
		universityPanel.setLayout(new GridLayout(3,1));
		
		Iterator<String> it = student.getUniversities().keySet().iterator();
		while (it.hasNext()) {
			
			String university = it.next();
			boolean accepted = student.getUniversities().get(university);
			
			JPanel p = new JPanel();
			p.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), 
					university
			));
			p.setLayout(new GridLayout( 2, 1 ));
			
			ButtonGroup group = new ButtonGroup();
			JRadioButton accept = new JRadioButton(ACCEPT);
			accept.setActionCommand(ACCEPT);
			JRadioButton reject = new JRadioButton(REJECT);
			reject.setActionCommand(REJECT);

						
			group.add(accept);
			group.add(reject);
			this.fields.put(group, university);
			
			if (accepted) 	accept.setSelected(true); 
			else 			reject.setSelected(true);
			
			p.add(accept);
			p.add(reject);
			
			universityPanel.add(p);
			
		}
		
		/* submit button */
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Iterator<Entry<ButtonGroup, String>> it = AdmitPanel.this.fields.entrySet().iterator();
				while (it.hasNext()) {
					
					Entry<ButtonGroup, String> entry = it.next();
					entry.getKey().getSelection().getActionCommand();
					
					Student s = AdmitPanel.this.currentStudent;
					s.addUniversity(entry.getValue(), ACCEPT.equals(entry.getKey().getSelection().getActionCommand()));
				}
				
				for (ChangeListener l : AdmitPanel.this.listeners) {
					l.stateChanged(new ChangeEvent(this));
				}
			}
			
		});
		
		// add everything to the panel
		this.studentInfoPanel.add( namePanel, 		BorderLayout.NORTH );
		this.studentInfoPanel.add( universityPanel, BorderLayout.CENTER );		
		this.studentInfoPanel.add( submit, 			BorderLayout.SOUTH );		

		// force a redraw of the screen
		this.revalidate();
		this.repaint();
	}

	@Override
	public String getCardName() {
		return CARD_NAME;
	}

	/**
	 * Sets the Students this panel contains 
	 * @param students a List of Students who have been inputted into the system
	 */
	public void setStudents(List<Student> students) {
		
		// Update students variable
		this.students = students;
		
		// get names of all students and store in a string array
		// The first combobox item is helper text asking the user to select a Student
		String studentNames[] = new String[this.students.size() + 1];
		studentNames[0] = SELECT_STUDENT;
		for ( int i = 1; i < studentNames.length; i++ ) studentNames[i] = this.students.get(i-1).getName();
		
		// Set model for the combobox to be the students names
		this.comboBox.setModel(new DefaultComboBoxModel<String>(studentNames));
		
	}

	public List<Student> getStudents() {
		return this.students;
	}
	
	public void addListener(ChangeListener c) {
		this.listeners.add(c);
	}
	
}
