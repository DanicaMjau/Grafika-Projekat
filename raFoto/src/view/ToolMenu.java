package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ToolMenu extends JMenuBar{
	private JMenu file = new JMenu("File");
	private JMenu edit = new JMenu("Edit");
	private JMenu about = new JMenu("About");
	private JMenu help = new JMenu("Help");
	private JMenuItem newPhoto=new JMenuItem("New photo");
	private JFileChooser fileChooser=new JFileChooser();
	private FileNameExtensionFilter filter;
	
	public ToolMenu(MainWindow parent){
		this.setBackground(Color.GRAY);
		
		file.setForeground(Color.PINK);
		file.setFont(new Font("SansSerif", Font.BOLD, 16));
		file.add(newPhoto);
		newPhoto.setForeground(Color.PINK);
		newPhoto.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	fileChooser.setDialogTitle("Choose photo");
				filter=new FileNameExtensionFilter("png images", "png");
				fileChooser.setFileFilter(filter);
				fileChooser.getCurrentDirectory().toString();
				int result = fileChooser.showOpenDialog(parent);
				if (result == JFileChooser.APPROVE_OPTION) {
		             File file = fileChooser.getSelectedFile();
		             try {
		            	 parent.setPhoto(util.Util.loadImage(file.getAbsolutePath()));
		             } catch (Exception ee) {
		                 ee.printStackTrace();
		             }
		         }
		    }
		});
		edit.setForeground(Color.PINK);
		edit.setFont(new Font("SansSerif", Font.BOLD, 16));
		about.setForeground(Color.PINK);
		about.setFont(new Font("SansSerif", Font.BOLD, 16));
		help.setForeground(Color.PINK);
		help.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		this.add(file);
		this.add(edit);
		this.add(help);
		this.add(about);
	}

}
