package com.github.markyc.applicationcenter;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayAllPanel extends JPanel implements CardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6800780775354459318L;
	public static final String CARD_NAME = "displayallpanel";
	
	public DisplayAllPanel() {
		this.add(new JLabel(DisplayAllPanel.class.getSimpleName()));
	}

	@Override
	public String getCardName() {
		return CARD_NAME;
	}

}
