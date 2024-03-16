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
	private BackPanel pnlNone = new BackPanel();
	private BackPanel pnlMain = new BackPanel();
	private BackPanel pnlGetReady = new BackPanel();
	private BackPanel pnlPlay = new BackPanel();
	
	//	Main Menu
	private TextLabel lblTitle = new TextLabel("STRATAGEM HERO", 24, JLabel.CENTER, Font.BOLD);;
	private TextLabel lblSubtitle = new TextLabel("Enter any Stratagem Input to Start!", 12, JLabel.CENTER, Font.BOLD);
	
	//	Get Ready Menu
	private TextLabel lblGetReady = new TextLabel("GET READY", 24, JLabel.CENTER, Font.BOLD);
	private TextLabel lblRound = new TextLabel("Round", 10, JLabel.CENTER, Font.BOLD);
	private TextLabel lblRoundNum = new TextLabel("1", 16, JLabel.CENTER, Font.BOLD);
	
	//	Play Menu
	private TextLabel lblRound2 = new TextLabel("Round", 10, JLabel.CENTER, Font.BOLD);
	private TextLabel lblRoundNum2 = new TextLabel("1", 16, JLabel.CENTER, Font.BOLD);
	private TextLabel lblScore = new TextLabel("SCORE", 10, JLabel.CENTER, Font.BOLD);
	private TextLabel lblScoreNum = new TextLabel("0", 16, JLabel.CENTER, Font.BOLD);
	private TextLabel lblStratagemName = new TextLabel("Stratagem", 10, JLabel.CENTER, Font.BOLD);
	
	//Settings
	private static final String FONT_NAME = "dialog";
	
	//Variables
	private double sizeMul = 1.0d;
	private HashMap<String, JPanel> dictMenu = new HashMap<>();
	private String strMenu = "Main";
	private int round = 1;
	private Timer timer;
	private TimerTask ttReady = new TimerTask() {
		@Override
		public void run() {
			timer.cancel();
			System.out.println("Delay!");
			setMenu("Play");
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//Initialize
		setMinimumSize( new Dimension(500, 250) );
		addKeyListener(this);
		addComponentListener( new FrameListener() );
		timer = new Timer();
		
		//Draw Screen
		//	None Screen
		panels.add(pnlNone, "None");
		
		//	Main Screen
		pnlMain.changeLayout( new GridBagLayout() );
		pnlMain.addComp( new JLabel(), Main.getGbc(0, 0, 1, 0.2) );
		
		lblTitle.setForeground(Color.WHITE);
		pnlMain.addComp( lblTitle, Main.getGbc(0, 1, 1, 0.6) );
		
		lblSubtitle.setForeground(Color.YELLOW);
		pnlMain.addComp( lblSubtitle, Main.getGbc(0, 2, 1, 0.2) );
		
		addMenu(panels, pnlMain, "Main");
		
		//	Get Ready Screen
		pnlGetReady.changeLayout( new GridBagLayout() );
		pnlGetReady.addComp( new JLabel(), Main.getGbc(0, 0, 1, 0.24) );
		
		lblGetReady.setForeground(Color.WHITE);
		pnlGetReady.addComp( lblGetReady, Main.getGbc(0, 1, 1, 0.52) );
		
		lblRound.setForeground(Color.WHITE);
		pnlGetReady.addComp( lblRound, Main.getGbc(0, 2, 1, 0.09) );
		
		lblRoundNum.setForeground(Color.YELLOW);
		pnlGetReady.addComp( lblRoundNum, Main.getGbc(0, 3, 1, 0.15) );
		
		addMenu(panels, pnlGetReady, "GetReady");
		
		//	Play Screen
		pnlPlay.changeLayout( new GridBagLayout() );
		
		//1st Row
		pnlPlay.addComp( new TestPanel(Color.red), Main.getGbc(0, 0, 0.25, 0.5, 1, 3) );
		pnlPlay.addComp( new JLabel(), Main.getGbc(1, 0, 0.15, 0.1, 5, 1) );
		
		//2nd Row
		Image img = new ImageIcon("rsc/StratagemIcon/anti_personnel_minefield.png").getImage();
		for (int i=0; i<5; i++) {
			ImagePanel imgPanel = new ImagePanel(img);
			pnlPlay.addComp( imgPanel, Main.getGbc(1+i, 1, 0.15, 0.3) );
		}
		
		//3rd Row
		pnlPlay.addComp( new JLabel(), Main.getGbc(1, 2, 0.15, 0.1, 5, 1) );
		
		//4th Row
		lblStratagemName.setOpaque(true);
		lblStratagemName.setBackground(Color.YELLOW);
		pnlPlay.addComp( lblStratagemName, Main.getGbc(0, 3, 1.0, 0.15, 6, 1) );
		
		//5th Row
		pnlPlay.addComp( new TestPanel(Color.white), Main.getGbc(0, 4, 1.0, 0.3, 6, 1) );
		
		//6th Row
		pnlPlay.addComp( new TestPanel(Color.yellow), Main.getGbc(0, 5, 1.0, 0.05, 6, 1) );
		
		addMenu(panels, pnlPlay, "Play");
		
		add(panels);
		setMenu("Main");
		
		setSize(1000, 500);
		setLocationRelativeTo(null); //Move Frame to Middle
	} //Constructor
	
	private class TestPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public TestPanel(Color c) {
			setBackground(c);
		}
	}
	
	
	//Getters
	public double getSizeMul() {
		return sizeMul;
	}
	
	
	private GridBagConstraints getGbc(int x, int y, int width, int height, double weightX, double weightY) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = weightX;
		gbc.weighty = weightY;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		return gbc;
	}
	
	private GridBagConstraints getGbc(int x, int y, int width, int height) {
		return getGbc(x, y, width, height, 1.0, 1.0);
	}
	
	private void addMenu(JPanel parentPnl, JPanel childPnl, String name) {
		parentPnl.add(childPnl, name);
		dictMenu.put(name, childPnl);
	}
	
	private void setMenu(String menu) {
		strMenu = menu;
		CardLayout layout = (CardLayout)panels.getLayout();
		if (dictMenu.get(menu) == null) {
			layout.show(panels, "None");
			return;
		}
		layout.show(panels, menu);
		
		switch (menu) {
			case "Ready":
				//lblRoundNum.setText( Integer.toString(round) );
				break;
			case "Play":
				timer = new Timer();
				timer.schedule(ttInterval, 0, 100);
				break;
		}
	}
	
	
	//ComponentAdapter
	private class FrameListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			double widthMul = getWidth() * 0.002;
			double heightMul = getHeight() * 0.004;
			sizeMul = Math.min(widthMul, heightMul);
			
			//Components
			lblTitle.resize(sizeMul);
			lblSubtitle.resize(sizeMul);
			lblGetReady.resize(sizeMul);
			lblRound.resize(sizeMul);
			lblRoundNum.resize(sizeMul);
			lblStratagemName.resize(sizeMul);
		}
	}
	
	//KeyListener
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		switch (strMenu) {
			case "Main":
				CardLayout layout = (CardLayout)panels.getLayout();
				layout.next(panels);
				//setMenu("GetReady");
				//timer = new Timer();
				//timer.schedule(ttReady, 3000);
				break;
			case "Play":
				break;
		}
	} //keyPressed()

	@Override
	public void keyReleased(KeyEvent e) { }
	
} //Frame Class