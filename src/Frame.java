import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	//Components
	private JPanel panels = new JPanel( new CardLayout() );
	private MyPanel pnlMain = new MyPanel();
	private MyPanel pnlGame = new MyPanel();

	//Settings
	private static final String FONT_NAME = "맑은 고딕";
	
	private String strMenu = "Main";

	public Frame() {
		setTitle("Stratagem Hero");
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null); //Move Frame to Middle
		
		//Initialize
		setMinimumSize( new Dimension(1000, 500) );
		addKeyListener(this);
		
		//Draw Screen
		//	Main Screen
		panels.add(pnlMain, "Main");
		
		add(panels);
	}
		
	//KeyListener
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		switch (strMenu) {
			case "Main":
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }
	
} //Frame Class