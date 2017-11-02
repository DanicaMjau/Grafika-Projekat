package buttons;

import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class BtnResize extends AbstractAction{

	public BtnResize(){
		try {
			this.putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/full-screen.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.putValue(Action.NAME, "Resize");
		this.putValue(Action.SHORT_DESCRIPTION, "Resize");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
