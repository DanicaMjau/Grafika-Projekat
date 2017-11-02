package buttons;

import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class BtnRotate extends AbstractAction{
	
	public BtnRotate(){
		try {
			this.putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/rotate.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.putValue(Action.NAME, "Rotate");
		this.putValue(Action.SHORT_DESCRIPTION, "Rotate");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
