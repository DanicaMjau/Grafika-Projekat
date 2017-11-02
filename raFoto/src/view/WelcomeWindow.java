package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class WelcomeWindow extends JFrame{
	
	public WelcomeWindow() {
		this.setTitle("raFoto");
		this.setSize(500, 500);
		this.setResizable(false);
		
		this.setLayout(new BorderLayout());
		
		this.setVisible(true);
	}

}
