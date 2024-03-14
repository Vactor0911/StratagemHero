import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class Frame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	//Components
	private JPanel panels = new JPanel( new CardLayout() );
	private MyPanel pnlNone = new MyPanel();
	private MyPanel pnlMain = new MyPanel();
	private MyPanel pnlGetReady = new MyPanel();
	private MyPanel pnlPlay = new MyPanel();
	
	//	Main Menu
	private TextLabel lblTitle = new TextLabel("STRATAGEM HERO", JLabel.CENTER, Font.BOLD, 0.065);
	private TextLabel lblSubtitle = new TextLabel("Enter any Stratagem Input to Start!", JLabel.CENTER, Font.BOLD, 0.03);
	
	//	Get Ready Menu
	private TextLabel lblGetReady = new TextLabel("GET READY", JLabel.CENTER, Font.BOLD, 0.065);
	private TextLabel lblRound = new TextLabel("Round", JLabel.CENTER, Font.BOLD, 0.025);
	private TextLabel lblRoundNum = new TextLabel("1", JLabel.CENTER, Font.BOLD, 0.05);
	
	//Settings
	private static final String FONT_NAME = "dialog";
	
	//Variables
	private HashMap<String, JPanel> dictMenu = new HashMap<>();
	private String strMenu = "Main";
	private int round = 1;
	private Timer timer;
	private TimerTask ttDelay = new TimerTask() {
		@Override
		public void run() {
			setMenu("Play");
			timer.cancel();
		}
	};
	private TimerTask ttInterval = new TimerTask() {
		@Override
		public void run() {
			System.out.println("Timer!");
		}
	};

	public Frame() {
		setTitle("Stratagem Hero");
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null); //Move Frame to Middle
		
		//Initialize
		setMinimumSize( new Dimension(1000, 500) );
		addKeyListener(this);
		timer = new Timer();
		
		//Draw Screen
		//	None Screen
		panels.add(pnlNone, "None");
		
		//	Main Screen
		pnlMain.setLayout( new GridLayout(5, 1) );
		pnlMain.add( new JLabel() );
		pnlMain.add( new JLabel() );
		lblTitle.setForeground(Color.WHITE);
		pnlMain.add(lblTitle);
		lblSubtitle.setForeground(Color.YELLOW);
		pnlMain.add(lblSubtitle);
		pnlMain.add( new JLabel() );
		
		addMenu(panels, pnlMain, "Main");
		
		//	Get Ready Screen
		pnlGetReady.setLayout( new GridLayout(5, 1) );
		pnlGetReady.add( new JLabel() );
		pnlGetReady.add( new JLabel() );
		lblGetReady.setForeground(Color.WHITE);
		pnlGetReady.add(lblGetReady);
		
		JPanel pnl2 = new JPanel();
		pnl2.setLayout( new GridLayout(2, 1) );
		pnl2.setOpaque(false);
		lblRound.setForeground(Color.WHITE);
		pnl2.add(lblRound);
		lblRoundNum.setForeground(Color.YELLOW);
		pnl2.add(lblRoundNum);
		pnlGetReady.add(pnl2);
		
		pnlGetReady.add( new JLabel() );
		
		addMenu(panels, pnlGetReady, "GetReady");
		
		//	Play Screen
		
		add(panels);
		setMenu("Main");
		
		//Resize
		setSize(1001, 500);
		setSize(1000, 500);
	} //Constructor
	
	private void addMenu(JPanel parentPnl, JPanel childPnl, String name) {
		parentPnl.add(childPnl, name);
		dictMenu.put(name, childPnl);
	}
	
	private void setMenu(String menu) {
		CardLayout layout = (CardLayout)panels.getLayout();
		if (dictMenu.get(menu) == null) {
			layout.show(panels, "None");
			return;
		}
		layout.show(panels, menu);
	}
		
	//KeyListener
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		switch (strMenu) {
			case "Main":
				strMenu = "GetReady";
				timer.schedule(ttDelay, 3000, 3000);
				break;
			case "Play":
				break;
		}
		setMenu(strMenu);
	} //keyPressed()

	@Override
	public void keyReleased(KeyEvent e) { }
	
} //Frame Class