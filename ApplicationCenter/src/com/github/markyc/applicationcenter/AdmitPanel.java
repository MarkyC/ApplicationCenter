package com.github.markyc.applicationcenter;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdmitPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1871018853543598682L;
	public static final String CARD_NAME = "admitpanel";

	public AdmitPanel() {
		JLabel label = new JLabel(AdmitPanel.class.getSimpleName());
		this.add(label);
	}
}
