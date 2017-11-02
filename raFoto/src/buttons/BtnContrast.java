package buttons;

import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class BtnContrast extends AbstractAction{
	
	public BtnContrast() {
		try {
			this.putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/contrast.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.putValue(Action.NAME, "Contrast");
		this.putValue(Action.SHORT_DESCRIPTION, "Contrast");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
