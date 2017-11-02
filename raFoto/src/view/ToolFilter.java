package view;

import java.awt.Color;
import java.awt.Container;

import javax.management.remote.JMXAuthenticator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JWindow;

import buttons.BtnAdd;
import buttons.BtnBlur;
import buttons.BtnBrightness;
import buttons.BtnContrast;
import buttons.BtnFlip;
import buttons.BtnNegative;
import buttons.BtnResize;
import buttons.BtnRotate;
import buttons.BtnSharpen;

public class ToolFilter extends JToolBar {
	private BtnBrightness btnBri=new BtnBrightness();
	private BtnContrast btnCon=new BtnContrast();
	private BtnFlip btnFlip=new BtnFlip();
	private BtnNegative btnNeg=new BtnNegative();
	private BtnRotate btnRot=new BtnRotate();
	private BtnSharpen btnSharp=new BtnSharpen();
	private BtnAdd btnAdd;
	private BtnBlur btnBlur=new BtnBlur();
	private BtnResize btnRes=new BtnResize();
	private MainWindow parent;
	
	public ToolFilter(MainWindow parent) {
		super(JToolBar.VERTICAL);
		this.setBackground(Color.PINK);
		this.setToolTipText("Filteri");
		this.setFloatable(false);
		this.parent=parent;
		
		btnAdd=new BtnAdd(parent);
		
		this.add(btnAdd);
		this.add(btnRes);
		this.add(btnBri);
		this.add(btnCon);
		this.add(btnFlip);
		this.add(btnNeg);
		this.add(btnRot);
		this.add(btnSharp);
		this.add(btnBlur);
	}
}
