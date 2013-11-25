/* Marco Cirillo 210272037 */

package com.github.markyc.applicationcenter.submit;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import javax.swing.DefaultListSelectionModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ApplicationCenter extends JApplet implements ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7447217375705120304L;
	private NavigationPanel navPanel;
	private JPanel contentPanel;
	
	/* The input panel is where Students are created */
	private InputPanel inputPanel;
	
	/* Panels we are in charge of updating */
	private AdmitPanel admitPanel;
	private DisplayAllPanel displayAllPanel;
	private DisplayPanel displayPanel;
	
	//public List<Student> students;

	public ApplicationCenter() throws HeadlessException {
		super();
		this.setLayout( new BorderLayout() );
		
		/* Set look and feel to system default */
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {}
		
		/* Create Content Panels */
		this.contentPanel = buildContentPanel();
		this.add( this.contentPanel, BorderLayout.CENTER );
		
		/* Add Navigation */
		this.navPanel = new NavigationPanel( this.contentPanel );
		this.add( this.navPanel, BorderLayout.WEST );
		
		this.setVisible(true);
		
	}

	private JPanel buildContentPanel() {
		JPanel cards = new JPanel( new CardLayout() );
		
		this.inputPanel 		= new InputPanel();
		this.inputPanel.addListener( this );
		
		this.admitPanel 		= new AdmitPanel();
		this.admitPanel.addListener( this );
		
		this.displayPanel		= new DisplayPanel();
		this.displayAllPanel	= new DisplayAllPanel();
		
        cards.add( this.inputPanel, 		this.inputPanel.getCardName() );
        cards.add( this.admitPanel, 		this.admitPanel.getCardName() );
        cards.add( this.displayPanel, 		this.displayPanel.getCardName() );
        cards.add( this.displayAllPanel,	this.displayAllPanel.getCardName() );

        return cards;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if ( e.getSource() instanceof InputPanel ) {
			
			//List<Student> students = ((InputPanel) e.getSource()).getStudents();
			Student[] students 	= ((InputPanel) e.getSource()).getStudents();
			
			this.admitPanel.setStudents( 		students);
			this.displayPanel.setStudents( 		students);
			this.displayAllPanel.setStudents( 	students);
			
		} else if ( e.getSource() instanceof AdmitPanel ) {
						
			//List<Student> students = ((AdmitPanel) e.getSource()).getStudents();
			Student[] students 	= ((AdmitPanel) e.getSource()).getStudents();
			
			this.displayPanel.setStudents(		students);
			this.displayAllPanel.setStudents(	students);

		}
		
	}

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
	 
		public Comparator<Student> AverageComparator = new Comparator<Student>() {
	 
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

	public class Undergrad extends Student {


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

	public class NavigationPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3578891403358049247L;
		private static final String NAV_INPUT = "Input";
		private static final String NAV_ADMIT = "Admit";
		private static final String NAV_DISPLAY_ONE = "Display One";
		private static final String NAV_DISPLAY_ALL = "Display All";
		private JPanel contentPanel;
		private List<JButton> buttons;
		private CardLayout layout;
		
		public NavigationPanel(JPanel contentPanel) {
			super();
			
			/* The contentPanel is the panel this Navigation Panel controls */
			this.contentPanel = contentPanel;
			
			/* Must be a CardLayout in the contentPanel, or we will get a ClassCastException */
			this.layout = (CardLayout) contentPanel.getLayout();
			
			/* One button is created for each navigation item */
			this.buttons = createButtons();
			this.setLayout( new GridLayout ( this.buttons.size(), 0 ) );
			this.setSize(200, 200);
			
			for( JButton button : this.buttons ) {
				this.add( button );
			}
		}

		private List<JButton> createButtons() {
			List<JButton> list = new ArrayList<JButton>();
			
			// cannot use InputPanel statically because it resides in another class
			final InputPanel inputPanel = new InputPanel(); 
			
			JButton input = new JButton( NAV_INPUT );			
			input.setName(inputPanel.getCardName());
			input.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					layout.show(NavigationPanel.this.contentPanel, inputPanel.CARD_NAME);
					//setActiveButton(InputPanel.CARD_NAME);
				}
			});
			list.add(input);
			
			JButton admit = new JButton( NAV_ADMIT );
			admit.setName(AdmitPanel.CARD_NAME);
			admit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					layout.show(NavigationPanel.this.contentPanel, AdmitPanel.CARD_NAME);
					//setActiveButton(InputPanel.CARD_NAME);
				}
			});
			list.add(admit);
			
			JButton displayOne = new JButton( NAV_DISPLAY_ONE );
			displayOne.setName(DisplayPanel.CARD_NAME);
			displayOne.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					layout.show(NavigationPanel.this.contentPanel, DisplayPanel.CARD_NAME);
					//setActiveButton(InputPanel.CARD_NAME);
				}
			});
			list.add(displayOne);
			
			JButton displayAll = new JButton( NAV_DISPLAY_ALL );
			displayAll.setName(DisplayAllPanel.CARD_NAME);
			displayAll.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					layout.show(NavigationPanel.this.contentPanel, DisplayAllPanel.CARD_NAME);
					//setActiveButton(InputPanel.CARD_NAME);
				}
			});
			list.add(displayAll);
			
			return list;
		}

	}



	public interface CardPanel {
		
		public static final String CARD_NAME = "";

		public String getCardName();
		
		//public void setStudents(List<Student> students);
		public void setStudents(Student[] students);
	}

	public class InputPanel extends JPanel implements CardPanel {

		public final String CARD_NAME = InputPanel.class.getSimpleName();
		
		private static final String INPUT 		= "Input";
		
		private static final String NAME 		= "Name: ";
		private static final String PROGRAM 	= "Program: ";
		private static final String AVG_MARK 	= "Average Mark: ";
		private static final String DEGREE 		= "Degree: ";
		private static final String UNIVERSITY 	= "Universities: ";
		private static final String SUBMIT 		= "Submit";

		private static final String STUDENTS_CREATED = " Students have been created out of 100.";
		
		/* Errors */
		private static final String NO_NAME = "Your name cannot be empty";
		private static final String NO_PROGRAM = "Your program cannot be empty";
		private static final String AVERAGE_ERROR = "Your average mark must be a number greater than 0";
		private static final String NO_UNIVERSITIES = "You have not selected any universities to apply to.";
		
		public final String[] GRAD_TYPES = { 
			Undergrad.class.getSimpleName(), 
			Postgrad.MASTER,
			Postgrad.PHD
		};
		
		public final String[] UNIVERSITIES = { 
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
		//private List<Student> students;
		private Student[] students; // Changed from a nice infinite List to an ugly finite array
		private int numStudents;	// needed to keep track of the amount of Students
		/**  This is used to grab the values from all the fields we wish to save when the user clicks submit */
		private List<JComponent> fields;
		/** Holds listeners that are added to the class */
		private List<ChangeListener> listeners;
		
		public InputPanel() {
			
			super();
			
			/* Build panels, adding the JTextFields to the list of fields */
			this.fields 		= new ArrayList<JComponent>();
			this.students 		= new Student[100]; //new ArrayList<Student>();
			this.numStudents 	= 0;
			this.listeners 		= new ArrayList<ChangeListener>();
			this.contentPanel 	= this.createContentPanel();

			/* Add a ScrollPane in case the view overflows */
			JScrollPane scroll = new JScrollPane( this.contentPanel );
			scroll.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), INPUT ));
			this.setLayout(new GridLayout( 1, 1 ));
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
			
			//JLabel label = new JLabel(this.students.size() + STUDENTS_CREATED);
			JLabel label = new JLabel(this.numStudents + STUDENTS_CREATED);
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
		
		/* These should be static, but because we are submitting everything as one file, 
		 * they cannot be */
		private JLabel createLabel(String text) {
			JLabel label = new JLabel(text);
			label.setPreferredSize(new Dimension( LABEL_WIDTH, COMPONENT_HEIGHT ));
			return label;
		}
		
		private JTextField createJTextField(String name) {
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
			//for ( Student s : this.students ) 
			for ( int i = 0; i < this.numStudents; i++ ) {
				Student s = this.students[i];
				if ( s.getName().equals( student.getName() ) )
					throw new IllegalArgumentException("There is already a student with this name in the system.");
			}


			//this.students.add(student);
			if ( this.numStudents < this.students.length ) {
				this.students[numStudents++] = student;
			} else {
				throw new IllegalArgumentException("There are only 100 students allowed");
			}
			
			for (ChangeListener l : this.listeners) {
				l.stateChanged(new ChangeEvent(this));
			}
		}
		
		public void addListener(ChangeListener c) {
			this.listeners.add(c);
		}
		
		//public List<Student> getStudents() {
		public Student[] getStudents() {
			
			return Arrays.copyOf(students, this.numStudents);
		}
		
		/*public int getStudentsCount() {
			return this.numStudents;
		}*/
		
		public void setStudents(Student[] students) {/* We don't set the Students of the InputPanel */}

		/* Only accept numbers in a JTextField */
		final class DigitDecimalListener implements KeyListener {

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
		
		/* Only allow 3 choices in a JList */
		final class MaxIndexSelectionModel extends DefaultListSelectionModel
		{
			
			private static final long serialVersionUID = -3856631894669326784L;
			private JList<?> list;
		    private int maxCount;

		    private MaxIndexSelectionModel(JList<?> list, int maxCount) {
		        this.list 		= list;
		        this.maxCount 	= maxCount;
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
				for (JComponent comp : InputPanel.this.fields)
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
						
						f.setText(""); 	// empty fields
						
					} else if (comp instanceof JComboBox<?>) {
					// Degree the Student is obtaining
						
						@SuppressWarnings("unchecked")
						JComboBox<String> box = (JComboBox<String>) comp;
						degree 	= (String) box.getSelectedItem();
						
						box.setSelectedIndex(0); // reset combobox
						
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
						
						l.setSelectedIndex(0); 	// reset list selection
						
					} else if ( comp instanceof JLabel ) {
					// Updates the amount of Students in the List
						
						label = (JLabel) comp;
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
					s.addUniversity(uni); //if ( !s.hasUniversity(uni) ) s.addUniversity(uni);
				
				// add to list
				try {
					InputPanel.this.addStudent(s);
				} catch (IllegalArgumentException ex) {
					new ErrorDialog(ex.getMessage());
					return;
				}
				
				// update label to reflect the new student
				//label.setText(InputPanel.this.students.size() + STUDENTS_CREATED);
				label.setText(InputPanel.this.numStudents + STUDENTS_CREATED);
			}
					
		}
	}

	public class AdmitPanel extends JPanel implements CardPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID 	= -1871018853543598682L;
		public static final String CARD_NAME 		= "admitpanel";
		
		private static final String SELECT_STUDENT 		= "Please select a student to view their admission status";
		private static final String SELECT_STUDENT_LIST = "Please select a student from the list above";
		private final String[] NO_STUDENTS 		= { "There are no students currently in the database" };
		
		private static final String ACCEPT = "Accept";
		private static final String REJECT = "Reject";
		
		private static final String SUBMIT = "Submit";

		
		/* Combo box index is students index + 1 
		 * because the first combobox item is "please select a student..." */
		//private List<Student> students;
		private Student[] students;

		private JComboBox<String> comboBox;
		
		private JPanel studentInfoPanel;
		
		Map<ButtonGroup, String> fields;
		
		private List<ChangeListener> listeners;
		

		public AdmitPanel() {
			super();
			
			this.students 		= new Student[100];//new ArrayList<Student>();
			this.fields 		= new HashMap<ButtonGroup, String>();
			this.listeners		= new ArrayList<ChangeListener>();
			
			this.comboBox 		= new JComboBox<String>(NO_STUDENTS);
			this.comboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					final JComboBox<?> box = (JComboBox<?>) e.getSource(); 
					
					// attempt to show the student, showing an empty ("please select a student...") panel on error
					try {
						//AdmitPanel.this.showStudent(students.get( box.getSelectedIndex() - 1 ));
						AdmitPanel.this.showStudent( box.getSelectedIndex() - 1 );
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
		
		private void showStudent(int index) {
			
			final Student student = this.students[index];
			
			// remove all previous entries in the fields list
			this.fields.clear();
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
			
			/*Iterator<String> it = student.getUniversities().keySet().iterator();
			while (it.hasNext()) {*/
			
			String[] universities = student.getUniversities();
			for (int i = 0; i < universities.length; i++) {
				
				String university = universities[i];//it.next();
				boolean accepted = student.getUniversitiesAccept()[i]; //student.getUniversities().get(university);
				
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
			JButton submit = new JButton( SUBMIT );
			submit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					Iterator<Entry<ButtonGroup, String>> it = AdmitPanel.this.fields.entrySet().iterator();
					while (it.hasNext()) {
						
						Entry<ButtonGroup, String> entry = it.next();
						entry.getKey().getSelection().getActionCommand();
						
						student.addUniversity(entry.getValue(), ACCEPT.equals(entry.getKey().getSelection().getActionCommand()));
						//AdmitPanel.this.students[ AdmitPanel.this.currentStudent ] = s;

					}
					
					for (ChangeListener l : AdmitPanel.this.listeners) {
						l.stateChanged(new ChangeEvent( AdmitPanel.this ));
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
		//public void setStudents(List<Student> students) {
		public void setStudents(Student[] students) {
			
			// Update students variable
			this.students 		= students;
			
			// get names of all students and store in a string array
			// The first combobox item is helper text asking the user to select a Student
			String studentNames[] = new String[ this.students.length + 1 ]; //new String[this.students.size() + 1];
			studentNames[0] = SELECT_STUDENT;
			//for ( int i = 1; i < studentNames.length; i++ ) studentNames[i] = this.students.get(i-1).getName();
			for ( int i = 1; i <= this.students.length; i++ ) {
				studentNames[i] = this.students[ i - 1 ].getName();
			}
			
			// Set model for the combobox to be the students names
			this.comboBox.setModel(new DefaultComboBoxModel<String>(studentNames));
			
		}

		//public List<Student> getStudents() {
		public Student[] getStudents() {
			
			return this.students;
		}
		
		public void addListener(ChangeListener c) {
			this.listeners.add(c);
		}	
	}

	public class DisplayPanel extends JPanel implements CardPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5548765433681976361L;
		public static final String CARD_NAME = "displaypanel";
		
		private static final String SELECT_STUDENT_LIST = "Select a student by typing their name above";
		
		private JTextField searchField;
		private JTextArea area;
		
		//List<Student> students;
		private Student[] students;

		
		public DisplayPanel() {
			this.setLayout(new BorderLayout());
			
			// the first field in this window is a combo box containing the names of all Students
			this.searchField = new JTextField();
			this.searchField.addActionListener(new ActionListener() {
			// Handle searches
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = ((JTextField) e.getSource()).getText(); 
					
					if ( "".equals(name) ) return;	// Don't search for empty Student name
					
					// attempt to show the student, showing an empty ("please select a student...") panel on error
					for ( int i = 0; i < DisplayPanel.this.students.length; i++ ) {
						Student s = DisplayPanel.this.students[i];
						if ( s.getName().equals( name ) ) {
							DisplayPanel.this.showStudent( s );
							return;
						}
					}
						
					
					// If we get here there is no Student with that name
					new ErrorDialog("No Student named " + name + " found");
				}
				
			});
			
			
			
			// the other field in this window displays the toString() of the selected Student
			this.area = new JTextArea( SELECT_STUDENT_LIST );
			this.area.setEditable( true );
			this.area.setFont(new Font( "Arial", Font.PLAIN, 14 )); // 14pt Arial
			
			this.add( this.searchField, BorderLayout.NORTH) ;
			this.add( new JScrollPane( this.area ), BorderLayout.CENTER );
		}

		private void showStudent(Student student) {
			this.area.setText( student.toString() );
			
		}

		@Override
		public String getCardName() {
			return CARD_NAME;
		}

		//public void setStudents(List<Student> students) {
		public void setStudents(Student[] students) {
			
			this.students		= students;
		}

	}

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
		public void setStudents(Student[] students) {
			
			Student[] sorted = Arrays.copyOf(students, students.length);
			if (sorted.length > 1) {
				// BubbleSort is a static inner class
				sorted = BubbleSort.sort(sorted, students[0].AverageComparator);
			}
			
			// Create text out of Students in the List
			String text = "";
			for ( int i = 0; i < sorted.length; i++ ) {
				text += sorted[i] + "\n";
			}
			
			// Set the text of the field to the Students from the List
			this.field.setText(text);
			
			// Call for redraw
			this.revalidate();
			this.repaint(); 
		}
		
	}

	public class ErrorDialog extends JDialog {

		private static final long serialVersionUID = 8490043350623409764L;

		public ErrorDialog(String message) {
			JOptionPane.showMessageDialog( this, message, "Error", JOptionPane.ERROR_MESSAGE);
		}
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
