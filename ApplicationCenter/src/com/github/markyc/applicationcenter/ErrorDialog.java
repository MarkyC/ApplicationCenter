package com.github.markyc.applicationcenter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ErrorDialog extends JDialog {

	private static final long serialVersionUID = 8490043350623409764L;

	public ErrorDialog(String message) {
		JOptionPane.showMessageDialog( this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
