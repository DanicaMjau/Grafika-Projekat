package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.ImageViewer;

public class MainWindow extends JFrame{
	
	private ToolFilter tbFilter;
	private JPanel panel=new JPanel();
	private JPanel paneltb=new JPanel();
	private JPanel panelmenu=new JPanel();
	private ToolMenu tbMenu;
	private BufferedImage image;
	private WritableRaster raster;
	private JInternalFrame frameimg=new JInternalFrame();
	
	public MainWindow(){
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700,700);
		this.setTitle("raFoto");
		this.setLocationRelativeTo(null);
		tbMenu=new ToolMenu(this);
		this.setJMenuBar(tbMenu);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/camera.png")));
		
		tbFilter=new ToolFilter(this);
		
		
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.black);
		this.setContentPane(panel);
		
		paneltb.setPreferredSize(new Dimension(50, this.getHeight()));
		paneltb.setBackground(Color.GRAY);
		paneltb.add(tbFilter);
		panel.add(paneltb,BorderLayout.EAST);
		
		this.add(frameimg);
		
		
		this.setVisible(true);
	}

	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setPhoto(BufferedImage photo){
		try {
			image=photo;
			raster=image.getRaster();
			frameimg.setClosed(true);
			frameimg=ImageViewer.showImageWindow(util.Util.rasterToImage(raster),"Doge");
			this.add(frameimg)
;		} catch (Exception e) {
			if(image==null){
				JOptionPane.showMessageDialog(this,"Image couldn't load.","Image error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
