package com.github.markyc.applicationcenter;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdmitPanel extends JPanel implements CardPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1871018853543598682L;
	public static final String CARD_NAME = "admitpanel";

	public AdmitPanel() {
		JLabel label = new JLabel(AdmitPanel.class.getSimpleName());
		this.add(label);
	}

	@Override
	public String getCardName() {
		return CARD_NAME;
	}
}
