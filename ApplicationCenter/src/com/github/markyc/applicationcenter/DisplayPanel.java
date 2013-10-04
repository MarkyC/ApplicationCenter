package com.github.markyc.applicationcenter;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5548765433681976361L;
	public static final String CARD_NAME = "displaypanel";
	
	public DisplayPanel() {
		this.add(new JLabel(DisplayPanel.class.getSimpleName()));
	}

}
