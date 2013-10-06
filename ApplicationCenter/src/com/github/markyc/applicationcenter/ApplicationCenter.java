/* Marco Cirillo 210272037 */

package com.github.markyc.applicationcenter;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JPanel;
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
	private AdmitPanel admitPanel;
	private InputPanel inputPanel;
	
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
		
		this.inputPanel = new InputPanel();
		this.inputPanel.addListener(this);
		this.admitPanel = new AdmitPanel();
		
        cards.add( this.inputPanel, 		InputPanel.CARD_NAME );
        cards.add( this.admitPanel, 		AdmitPanel.CARD_NAME );
        cards.add( new DisplayPanel(), 		DisplayPanel.CARD_NAME );
        cards.add( new DisplayAllPanel(),	DisplayAllPanel.CARD_NAME );

        return cards;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if ( e.getSource() instanceof InputPanel ) {
			this.admitPanel.setStudents(((InputPanel) e.getSource()).getStudents());
		}
		
	}

}
