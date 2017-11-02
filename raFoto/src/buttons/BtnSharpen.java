package buttons;

import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class BtnSharpen extends AbstractAction{

	public BtnSharpen(){
		try {
			this.putValue(Action.SMALL_ICON, new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/icons/sharpener.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.putValue(Action.NAME, "Sharpen");
		this.putValue(Action.SHORT_DESCRIPTION, "Sharpen");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
