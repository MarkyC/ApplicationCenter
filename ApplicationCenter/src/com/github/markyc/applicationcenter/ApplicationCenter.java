/* Marco Cirillo 210272037 */

package com.github.markyc.applicationcenter;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.HeadlessException;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ApplicationCenter extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7447217375705120304L;
	private NavigationPanel navPanel;
	private JPanel contentPanel;

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
        cards.add(new InputPanel(), InputPanel.CARD_NAME );
        cards.add(new AdmitPanel(), AdmitPanel.CARD_NAME );
        cards.add(new DisplayPanel(), DisplayPanel.CARD_NAME );
        cards.add(new DisplayAllPanel(), DisplayAllPanel.CARD_NAME );

        return cards;
	}

}
