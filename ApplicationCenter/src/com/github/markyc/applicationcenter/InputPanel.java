package com.github.markyc.applicationcenter;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InputPanel extends JPanel implements CardPanel {

	public static final String CARD_NAME = InputPanel.class.getSimpleName();
	
	private static final String INPUT 		= "Input";
	
	private static final String NAME 		= "Name: ";
	private static final String PROGRAM 	= "Program: ";
	private static final String AVG_MARK 	= "Average Mark: ";
	private static final String DEGREE 		= "Degree: ";
	private static final String UNIVERSITY 	= "Universities: ";
	private static final String SUBMIT 		= "Submit";

	private static final String STUDENTS_CREATED = " Students have been created";
	
	/* Errors */
	private static final String NO_NAME = "Your name cannot be empty";
	private static final String NO_PROGRAM = "Your program cannot be empty";
	private static final String AVERAGE_ERROR = "Your average mark must be a number greater than 0";
	private static final String NO_UNIVERSITIES = "You have not selected any universities to apply to.";
	
	public static final String[] GRAD_TYPES = { 
		Undergrad.class.getSimpleName(), 
		Postgrad.MASTER,
		Postgrad.PHD
	};
	
	public static final String[] UNIVERSITIES = { 
		"Toronto", 
		"York", 
		"Western", 
		"Brock", 
		"Guelph", 
		"Waterloo", 
		"McGill", 
		"Concordia", 
		"Laval", 
		"Macmaster"
	};
	
	
	private static final int COMPONENT_HEIGHT 	= 30;
	private static final int FIELD_WIDTH 		= 200;
	private static final int LABEL_WIDTH 		= 100;
	
	private static final long serialVersionUID = 8563373557574559578L;

	private JPanel contentPanel;

	/** Holds all the students */
	private List<Student> students;
	/**  This is used to grab the values from all the fields we wish to save when the user clicks submit */
	private List<JComponent> fields;
	/** Holds listeners that are added to the class */
	private List<ChangeListener> listeners;
	
	public InputPanel() {
		
		super();
		
		/* Build panels, adding the JTextFields to the list of fields */
		this.fields = new ArrayList<JComponent>();
		this.students = new ArrayList<Student>();
		this.listeners = new ArrayList<ChangeListener>();
		this.contentPanel = this.createContentPanel();

		/* Add a ScrollPane in case the view overflows */
		JScrollPane scroll = new JScrollPane( this.contentPanel );
		scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), INPUT));
		this.setLayout(new GridLayout( 1,1 ));
		this.add(scroll);
	}

	/**
	 * Builds the panel that will be shown to the user, comprised of several smaller panels
	 * @return the panel that will be shown to the user
	 */
	private JPanel createContentPanel() {
		List<JPanel> panels = new ArrayList<JPanel>();	
		
		/* Create Fields */
		panels.add( createTextPanel(NAME) );
		panels.add( createTextPanel(PROGRAM) );
		
		panels.add( createTextPanel(AVG_MARK) );
		
		/* Add listener for numbers and periods to the average mark JTextField */
		for ( JComponent c : this.fields )
			if (c.getName() == AVG_MARK)
				c.addKeyListener(new DigitDecimalListener());

		/* Degree should not be a text field, it should be a JComboBox
		 * that can be edited like a JTextField. This is so the user
		 * can specify a degree in error, and have a way to revert back
		 * to the default "Undergrad" option, and we don't have to check
		 * if they entered typos like "undergrad" or "ndergrad" themselves. 
		 */
		// panels.add( createTextPanel(DEGREE) );
		panels.add( createDegreePanel(DEGREE) );
		
		panels.add( createListPanel(UNIVERSITY, UNIVERSITIES) );
		
		panels.add( createSubmitPanel(SUBMIT) );
		
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout( result, BoxLayout.Y_AXIS ));
		for (JPanel p : panels)
			result.add(p);
		
		return result;
	}

	private JPanel createSubmitPanel(String name) {
		
		JLabel label = new JLabel(this.students.size() + STUDENTS_CREATED);
		label.setName(name);
		label.setPreferredSize(new Dimension( LABEL_WIDTH + FIELD_WIDTH, COMPONENT_HEIGHT ));
		label.setHorizontalAlignment(SwingConstants.CENTER);

		
		/* this label must also be updated when clicking submit */
		this.fields.add(label);
		
		JButton submit = new JButton(name);
		submit.addActionListener(new SubmitListener());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		panel.add( submit );
		panel.add( label );

		return panel;
	}

	private JPanel createListPanel(String name, String[] listValues) {
		final JList<String> list = new JList<String>( listValues );
		list.setName( name );
		list.setVisibleRowCount( 6 );
		list.setSelectionModel(new MaxIndexSelectionModel( list, 3 ));
		
		/* Add to list of fields we are watching */
		this.fields.add(list);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension ( FIELD_WIDTH, scrollPane.getPreferredSize().height ));

		
		JPanel panel = new JPanel();
		panel.add( createLabel(name) );
		panel.add(scrollPane);
		
		return panel;
	}

	private JPanel createDegreePanel(String name) {
		
		JComboBox<String> box 	= new JComboBox<String>(GRAD_TYPES);
		box.setName(name);
		//box.setEditable(true);
		box.setPreferredSize(new Dimension ( FIELD_WIDTH, COMPONENT_HEIGHT ));
		
		/* Add to list of fields we are watching */
		this.fields.add(box);
		
		JPanel panel = new JPanel();
		panel.add( createLabel(name) );
		panel.add(box);
		
		return panel;
	}

	private JPanel createTextPanel(String text) {
		JTextField field 	= createJTextField(text);
		
		/* Add to list of fields we are watching */
		this.fields.add(field);
		
		JPanel panel 		= new JPanel();
		panel.add( createLabel(text) );
		panel.add( field );
		
		return panel;
	}
	
	private static JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension( LABEL_WIDTH, COMPONENT_HEIGHT ));
		return label;
	}
	
	private static JTextField createJTextField(String name) {
		JTextField f = new JTextField();
		f.setName(name);
		f.setPreferredSize(new Dimension ( FIELD_WIDTH, COMPONENT_HEIGHT ));
		return f;
	}
	
	@Override
	public String getCardName() {
		return CARD_NAME;
	}
	
	public void addStudent(Student student) throws IllegalArgumentException {
		
		// Student names must be unique. This is because of a bug in JComboBox where
		// getSelectedIndex() returns the first index that contains the name of
		// the selected item: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4133743
		// I don't agree with this, because it forces all items in JComboBox to
		// be unique, but c'est la vie.
		for ( Student s : this.students ) 
			if ( s.getName().equals( student.getName() ) )
				throw new IllegalArgumentException("There is already a student with this name in the system.");


		this.students.add(student);
		
		for (ChangeListener l : this.listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	public void addListener(ChangeListener c) {
		this.listeners.add(c);
	}
	
	public List<Student> getStudents() {
		return this.students;
	}
	
	public void setStudents(List<Student> students) {/* We don't set the Students of the InputPanel */}

	final static class DigitDecimalListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			
			JTextField f = (JTextField) e.getSource();

			// Only accept numbers and decimals in the average mark JTextField
			if ( (!Character.isDigit( e.getKeyChar() )) && (e.getKeyChar() != '.') ) {
				e.consume();
				
				
				// Set and display a helpful tooltip
				f.setToolTipText("Only numbers and decimals allowed");
				try {
		            e.getComponent().dispatchEvent(new KeyEvent(e.getComponent(), KeyEvent.KEY_PRESSED,
		                    System.currentTimeMillis(), InputEvent.CTRL_MASK,
		                    KeyEvent.VK_F1, KeyEvent.CHAR_UNDEFINED));
		        } catch (Exception ex) {/* No need to do anything on Exception */}
			} 
			
			// Only one period allowed in a decimal number
			if (e.getKeyChar() == '.') {
				String text = f.getText();
				if  ( ( text.indexOf('.') != -1 ) && ( text.indexOf('.') != text.length() ) ) {
					e.consume();
				}
			}
			
		}


		@Override public void keyPressed(KeyEvent e) { }
		@Override public void keyReleased(KeyEvent e) { }
	}
	
	final static class MaxIndexSelectionModel extends DefaultListSelectionModel
	{
		
		private static final long serialVersionUID = -3856631894669326784L;
		private JList<?> list;
	    private int maxCount;

	    private MaxIndexSelectionModel(JList<?> list, int maxCount) {
	        this.list = list;
	        this.maxCount = maxCount;
	    }

	    @Override
	    public void setSelectionInterval(int index0, int index1) {
	    	
	        // if the user selects the index backwards, lets run this method with the args switched
	        if (index1 < index0) {
	            setSelectionInterval(index1, index0);
	        	return;
	        }
	    	
	    	// limit selections to maxCount
	        if (index1 - index0 >= maxCount) {
	            index1 = index0 + maxCount - 1;
	        }
	        super.setSelectionInterval(index0, index1);
	    }

	    @Override
	    public void addSelectionInterval(int index0, int index1)
	    {
	        // if the user selects the index backwards, lets run this method with the args switched
	        if (index1 < index0) {
	            addSelectionInterval(index1, index0);
	        	return;
	        }
	    	
	        int selectionLength = list.getSelectedIndices().length;
	        
	        // Don't select if greater than maxCount values are selected
	        if (selectionLength >= maxCount)
	            return;

	        if (index1 - index0 >= maxCount - selectionLength) {
	            index1 = index0 + maxCount - 1 - selectionLength;
	        }
	        
	        super.addSelectionInterval(index0, index1);
	    }
	}
	
	private class SubmitListener implements ActionListener {

		private String name;
		private String program;
		private double avgMark;
		private String degree;
		private List<String> universities;
		private JLabel label;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// iterate over fields to get the user inputted data
			for (JComponent comp : InputPanel.this.fields) {
				
				if (comp instanceof JTextField) {
				// Name, program, marks of the Student
					
					JTextField f = (JTextField) comp;
					switch(f.getName()) {
					
					case NAME: 		
						name 	= f.getText(); 
						
						if ( "".equals(name) ) {
							new ErrorDialog( NO_NAME );
							return;
						}
						
						break;
						
					case PROGRAM: 	
						program = f.getText();
						
						if ( "".equals(program) ) {
							new ErrorDialog( NO_PROGRAM );
							return;
						}
						
						
						break;
						
					case AVG_MARK: 	
						try {
							avgMark = Double.parseDouble(f.getText()); 
							if ( avgMark <= 0 ) throw new IllegalArgumentException();
							break;
						} catch (Exception ex) {
							new ErrorDialog( AVERAGE_ERROR );
							return;
						}
					
					}
					
				} else if (comp instanceof JComboBox<?>) {
				// Degree the Student is obtaining
					
					@SuppressWarnings("unchecked")
					JComboBox<String> box = (JComboBox<String>) comp;
					degree 	= (String) box.getSelectedItem();
					
				} else if ( comp instanceof JList<?> ) {
				// Student university selection	
					
					@SuppressWarnings("unchecked")
					JList<String> l = (JList<String>) comp;
					universities = l.getSelectedValuesList();
					
					if ( universities.size() < 1 ) {
					// Student has not selected any universities
						new ErrorDialog( NO_UNIVERSITIES );
						return;
					}
					
				} else if ( comp instanceof JLabel ) {
				// Updates the amount of Students in the List
					
					label = (JLabel) comp;
				}
			}
			
			// Create student based on provided info
			Student s;
			if ( Undergrad.class.getSimpleName().equals(degree) ) {
				s = new Undergrad(name, program, avgMark);
			} else {
				s = new Postgrad(name, program, degree, avgMark);
			}
			
			// add selected universities
			for (String uni : universities)
				if ( !s.hasUniversity(uni) ) s.addUniversity(uni);
			
			// add to list
			try {
				InputPanel.this.addStudent(s);
			} catch (IllegalArgumentException ex) {
				new ErrorDialog(ex.getMessage());
				return;
			}
			
			// update label to reflect the new student
			label.setText(InputPanel.this.students.size() + STUDENTS_CREATED);
		}
				
	}
}
