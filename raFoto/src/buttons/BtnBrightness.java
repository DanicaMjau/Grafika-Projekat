package buttons;

import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class BtnBrightness extends AbstractAction{
	
	public BtnBrightness(){
		try {
			this.putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/brightness.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.putValue(Action.NAME, "Brightness");
		this.putValue(Action.SHORT_DESCRIPTION, "Brightness");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
