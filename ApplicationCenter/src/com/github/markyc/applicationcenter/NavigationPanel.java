package com.github.markyc.applicationcenter;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

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
		
		JButton input = new JButton( NAV_INPUT );
		input.setName(InputPanel.CARD_NAME);
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				layout.show(NavigationPanel.this.contentPanel, InputPanel.CARD_NAME);
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
