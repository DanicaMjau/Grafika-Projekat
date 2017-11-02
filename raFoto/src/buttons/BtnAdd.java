package buttons;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.MainWindow;

public class BtnAdd extends AbstractAction{
	
	private JFileChooser fileChooser=new JFileChooser();
	private FileNameExtensionFilter filter;
	private MainWindow parent;
	
	public BtnAdd(MainWindow parent){
		this.parent=parent;
		try {
			this.putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/file.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.putValue(Action.NAME, "Add");
		this.putValue(Action.SHORT_DESCRIPTION, "Add");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
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

}
