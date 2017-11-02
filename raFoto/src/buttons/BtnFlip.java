package buttons;

import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class BtnFlip extends AbstractAction{
	
	public BtnFlip(){
		try {
			this.putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/mirror.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.putValue(Action.NAME, "Flip");
		this.putValue(Action.SHORT_DESCRIPTION, "Flip");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
