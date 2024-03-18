import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONObject;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final Image imageIcon = new ImageIcon( Main.getPath("rsc/SuperEarthLogo.png") ).getImage();
	
	//Components
	private JPanel panels = new JPanel( new CardLayout() );
	private BackPanel pnlNone = new BackPanel();
	private BackPanel pnlMain = new BackPanel();
	private BackPanel pnlGetReady = new BackPanel();
	private BackPanel pnlPlay = new BackPanel();
	private BackPanel pnlRoundClear = new BackPanel();
	private BackPanel pnlGameOver = new BackPanel();
	
	//	Main Menu
	private TextLabel lblTitle = new TextLabel("STRATAGEM HERO", 24, JLabel.CENTER, Font.BOLD);;
	private TextLabel lblSubtitle = new TextLabel("Enter any Stratagem Input to Start!", 12, JLabel.CENTER, Font.BOLD);
	
	//	Get Ready Menu
	private TextLabel lblGetReady = new TextLabel("GET READY", 24, JLabel.CENTER, Font.BOLD);
	private TextLabel lblRound = new TextLabel("Round", 10, JLabel.CENTER, Font.BOLD);
	private TextLabel lblRoundNum = new TextLabel("1", 16, JLabel.CENTER, Font.BOLD);
	
	//	Play Menu
	private TextLabel lblPlayRound = new TextLabel("Round", 10, JLabel.LEFT, Font.BOLD);
	private TextLabel lblPlayRoundNum = new TextLabel("1", 16, JLabel.LEFT, Font.BOLD);
	private TextLabel lblScore = new TextLabel("SCORE", 10, JLabel.RIGHT, Font.BOLD);
	private TextLabel lblScoreNum = new TextLabel("0", 16, JLabel.RIGHT, Font.BOLD);
	private TextLabel lblStratagemName = new TextLabel("", 10, JLabel.CENTER, Font.BOLD);
	private CommandPanel pnlCommand = new CommandPanel();
	private ImagePanel[] aryPnlStratagem = new ImagePanel[6];
	private ProgressBar progressBar = new ProgressBar(1.0d);
	
	//Round Clear Menu
	private TextLabel lblRoundBonus = new TextLabel("Round Bonus", 10, JLabel.LEFT, Font.BOLD);
	private TextLabel lblRoundBonusNum = new TextLabel("0", 16, JLabel.RIGHT, Font.BOLD);
	private TextLabel lblTimeBonus = new TextLabel("Time Bonus", 10, JLabel.LEFT, Font.BOLD);
	private TextLabel lblTimeBonusNum = new TextLabel("0", 16, JLabel.RIGHT, Font.BOLD);
	private TextLabel lblPerfectBonus = new TextLabel("Perfect Bonus", 10, JLabel.LEFT, Font.BOLD);
	private TextLabel lblPerfectBonusNum = new TextLabel("0", 16, JLabel.RIGHT, Font.BOLD);
	private TextLabel lblTotalScore = new TextLabel("Total Score", 10, JLabel.LEFT, Font.BOLD);
	private TextLabel lblTotalScoreNum = new TextLabel("0", 16, JLabel.RIGHT, Font.BOLD);
	
	//	Game Over Menu
	private TextLabel lblGameOver = new TextLabel("GAME OVER", 24, JLabel.CENTER, Font.BOLD);
	private TextLabel lblFinalScore = new TextLabel("YOUR FINAL SCORE", 10, JLabel.CENTER, Font.BOLD);
	private TextLabel lblFinalScoreNum = new TextLabel("0", 16, JLabel.CENTER, Font.BOLD);
	
	//Variables
	private double sizeMul = 1.0d;
	private HashMap<String, JPanel> dictMenu = new HashMap<>();
	private String strMenu = "Main";
	private int round = 1;
	private int score = 0;
<<<<<<< HEAD
	private final double MAX_TIME = 10.0d;
=======
	private JSONObject[] aryStratagem;
	private int stratagemIndex = 0;
	private int commandIndex = 0;
	private final double MAX_TIME = 12.0d;
>>>>>>> fbd1545c969db09f9b9bcf51105b9b79e6cf7740
	private final double MAX_TIME_MUL = 1 / MAX_TIME;
	private double time = 0.0d;
	private Timer timer;
	//	Stratagems
	private Stratagem stratagem = new Stratagem();
	private JSONObject[] aryStratagem;
	private int stratagemIndex = 0;
	private boolean flagStratagemPerfect = true;
	private int stratagemPerfect = 0;
	

	public Frame() {
		setTitle("Stratagem Hero");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//Initialize
		setIconImage(imageIcon);
		setMinimumSize( new Dimension(500, 250) );
		addKeyListener( new InputListener() );
		addComponentListener( new FrameListener() );
		timer = new Timer();
		for (int i=0; i<aryPnlStratagem.length; i++) {
			aryPnlStratagem[i] = new ImagePanel();
		}
		aryPnlStratagem[0].setBorder( BorderFactory.createLineBorder(Color.YELLOW, 3) );
		
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
		
		//		1st Row
		pnlPlay.addComp( aryPnlStratagem[0], Main.getGbc(0, 0, 0.25, 0.5, 1, 3) );
		pnlPlay.addComp( new JLabel(), Main.getGbc(1, 0, 0.15, 0.125, 5, 1) );
		//		2nd Row
		for (int i=1; i<6; i++) {
			pnlPlay.addComp( aryPnlStratagem[i], Main.getGbc(i, 1, 0.15, 0.25) );
		}
		//		3rd Row
		pnlPlay.addComp( new JLabel(), Main.getGbc(1, 2, 0.15, 0.125, 5, 1) );
		//		4th Row
		lblStratagemName.setOpaque(true);
		lblStratagemName.setBackground(Color.YELLOW);
		pnlPlay.addComp( lblStratagemName, Main.getGbc(0, 3, 1.0, 0.15, 6, 1) );
		//		5th Row
		pnlPlay.addComp( pnlCommand, Main.getGbc(0, 4, 1.0, 0.2, 6, 1) );
		//		6th Row
		pnlPlay.addComp( new JLabel(), Main.getGbc(0, 5, 1.0, 0.1, 6, 1) );
		//		7th Row
		pnlPlay.addComp( progressBar, Main.getGbc(0, 6, 1.0, 0.05, 6, 1) );
		//		West
		JPanel pnlWest = pnlPlay.getPanel(BackPanel.WEST);
		pnlWest.setLayout( new GridBagLayout() );
		lblPlayRound.setForeground(Color.WHITE);
		pnlWest.add( new JLabel(), Main.getGbc(0, 0, 1.0, 1.0, 1, 3) );
		pnlWest.add( lblPlayRound, Main.getGbc(1, 0, 1.0, 0.09) );
		lblPlayRoundNum.setForeground(Color.YELLOW);
		pnlWest.add( lblPlayRoundNum, Main.getGbc(1, 1, 1.0, 0.21) );
		pnlWest.add( new JLabel(), Main.getGbc(1, 2, 1.0, 0.7) );
		//		East
		JPanel pnlEast = pnlPlay.getPanel(BackPanel.EAST);
		pnlEast.setLayout( new GridBagLayout() );
		lblScoreNum.setForeground(Color.YELLOW);
		pnlEast.add( lblScoreNum, Main.getGbc(0, 0, 1.0, 0.21) );
		lblScore.setForeground(Color.WHITE);
		pnlEast.add( lblScore, Main.getGbc(0, 1, 1.0, 0.09) );
		pnlEast.add( new JLabel(), Main.getGbc(0, 2, 1.0, 0.7) );
		pnlEast.add( new JLabel(), Main.getGbc(1, 0, 1.0, 1.0, 1, 3) );
		
		addMenu(panels, pnlPlay, "Play");
		
		//	Round Clear Screen
		pnlRoundClear.changeLayout( new GridBagLayout() );
		
		//		1st Row
		lblRoundBonus.setForeground(Color.WHITE);
		pnlRoundClear.addComp( lblRoundBonus, Main.getGbc(0, 0, 0.5, 0.25) );
		lblRoundBonusNum.setForeground(Color.YELLOW);
		pnlRoundClear.addComp( lblRoundBonusNum, Main.getGbc(1, 0, 0.5, 0.25) );
		//		2nd Row
		lblTimeBonus.setForeground(Color.WHITE);
		pnlRoundClear.addComp( lblTimeBonus, Main.getGbc(0, 1, 0.5, 0.25) );
		lblTimeBonusNum.setForeground(Color.YELLOW);
		pnlRoundClear.addComp( lblTimeBonusNum, Main.getGbc(1, 1, 0.5, 0.25) );
		//		3rd Row
		lblPerfectBonus.setForeground(Color.WHITE);
		pnlRoundClear.addComp( lblPerfectBonus, Main.getGbc(0, 2, 0.5, 0.25) );
		lblPerfectBonusNum.setForeground(Color.YELLOW);
		pnlRoundClear.addComp( lblPerfectBonusNum, Main.getGbc(1, 2, 0.5, 0.25) );
		//		4th Row
		lblTotalScore.setForeground(Color.WHITE);
		pnlRoundClear.addComp( lblTotalScore, Main.getGbc(0, 3, 0.5, 0.25) );
		lblTotalScoreNum.setForeground(Color.YELLOW);
		pnlRoundClear.addComp( lblTotalScoreNum, Main.getGbc(1, 3, 0.5, 0.25) );
		
		addMenu(panels, pnlRoundClear, "RoundClear");
		
		//	Game Over Screen
		pnlGameOver.changeLayout( new GridBagLayout() );
		pnlGameOver.addComp( new JLabel(), Main.getGbc(0, 0, 1, 0.24) );
		
		lblGameOver.setForeground(Color.WHITE);
		pnlGameOver.addComp( lblGameOver, Main.getGbc(0, 1, 1, 0.52) );
		
		lblFinalScore.setForeground(Color.WHITE);
		pnlGameOver.addComp( lblFinalScore, Main.getGbc(0, 2, 1, 0.09) );
		
		lblFinalScoreNum.setForeground(Color.YELLOW);
		pnlGameOver.addComp( lblFinalScoreNum, Main.getGbc(0, 3, 1, 0.15) );
		
		addMenu(panels, pnlGameOver, "GameOver");
		
		add(panels);
		setMenu("Main");
		
		setSize(1000, 500);
		setLocationRelativeTo(null); //Move Frame to Middle
	} //Constructor
	
	
	//Getters
	public double getSizeMul() {
		return sizeMul;
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
			case "Main":
				scheduleTimer(null, 0, 0);
				round = 1;
				score = 0;
				break;
			case "GetReady":
				scheduleTimer("GetReady", 3000, 3000);
				lblRoundNum.setText( Integer.toString(round) );
				break;
			case "Play":
				time = MAX_TIME; //reset time
				scheduleTimer("Play", 0, 10);
				resetStratagem();
				break;
			case "RoundClear":
				TextLabel[] aryText = {lblRoundBonus, lblRoundBonusNum, lblTimeBonus, lblTimeBonusNum, lblPerfectBonus, lblPerfectBonusNum,
						lblTotalScore, lblTotalScoreNum};
				for (TextLabel k : aryText) {
					k.setText("");
				}
				
				scheduleTimer("RoundClear", 500, 500);
				time = MAX_TIME;
				resetStratagem();
				break;
			case "GameOver":
				lblFinalScoreNum.setText( Integer.toString(score) );
				scheduleTimer("GameOver", 6000, 6000);
		}
	} //setMenu()
	
	private void scheduleTimer(String timerName, long delay, long period) {
		timer.cancel();
		timer.purge();
		timer = new Timer();
		
		if (timerName == null || timerName == "") {
			return;
		}
		
		TimerTask timerTask;
		switch (timerName) {
			case "GetReady":
				timerTask = new TimerTask() {
					@Override
					public void run() {
						setMenu("Play");
					}
				};
				break;
			case "Play":
				timerTask = new TimerTask() {
					@Override
					public void run() {
						time -= 0.01d;
						double progress = time * MAX_TIME_MUL;
						progressBar.setProgress(progress);
						
						if (time <= 0) {
							setMenu("GameOver");
						}
					}
				};
				break;
			case "RoundClear":
				int roundBonus = 75 + round * 25;
				int timeBonus = (int)(time) * 10;
				int perfectBonus = stratagemPerfect * 20;
				int totalScore = score + roundBonus + timeBonus + perfectBonus;
				score = totalScore;
				timerTask = new TimerTask() {
					int repeatTime = 0;
					TextLabel[] aryText = {lblRoundBonus, lblRoundBonusNum, lblTimeBonus, lblTimeBonusNum, lblPerfectBonus, lblPerfectBonusNum,
							lblTotalScore, lblTotalScoreNum};
					String[] aryString = {"Round Bonus", Integer.toString(roundBonus), "Time Bonus", Integer.toString(timeBonus),
							"Perfect Bonus", Integer.toString(perfectBonus), "Total Score", Integer.toString(totalScore)};
					
					@Override
					public void run() {
						if (repeatTime < 4) {
							aryText[repeatTime * 2].setText( aryString[repeatTime * 2] );
							aryText[repeatTime * 2 + 1].setText( aryString[repeatTime * 2 + 1] );
						}
						else if (repeatTime >= 9) {
							round++;
							setMenu("GetReady");
							return;
						}
						repeatTime++;
					}
				};
				break;
			case "GameOver":
				timerTask = new TimerTask() {
					@Override
					public void run() {
						setMenu("Main");
					}
				};
				break;
			default:
				return;
		}
		timer.scheduleAtFixedRate(timerTask, delay, period);
	} //scheduleTimer()
	
	
	private void resetStratagem() {
		aryStratagem = stratagem.getRandStratagem(5 + round); //Get stratagem queue
		lblPlayRoundNum.setText( Integer.toString(round) ); //Reload round
		lblScoreNum.setText( Integer.toString(score) ); //Reload score
		
		//reset variables & draw queue
		stratagemIndex = 0;
		flagStratagemPerfect = true;
		stratagemPerfect = 0;
		drawStratagems();
	}
	
	private void nextStratagem() {
		time += 1.0d;
		score += aryStratagem[stratagemIndex].getString("command").length() * 5;
		lblScoreNum.setText( Integer.toString(score) ); //Reload score
		flagStratagemPerfect = true;
		stratagemIndex++;
		
		if (stratagemIndex >= aryStratagem.length) {
			setMenu("RoundClear");
			return;
		}
		
		drawStratagems();
	} //nextStratagem()
	
	private void drawStratagems() {
		Image image = new ImageIcon( Main.getPath( aryStratagem[stratagemIndex].getString("image") ) ).getImage();
		aryPnlStratagem[0].setImage(image);
		lblStratagemName.setText( aryStratagem[stratagemIndex].getString("name") ); //Set label text
		pnlCommand.setCommand( aryStratagem[stratagemIndex].getString("command") ); //Set Command Arrow Image
		
		for (int i=1; i<6; i++) {
			int fixedIndex = i + stratagemIndex;
			//Out of index
			if (fixedIndex >= aryStratagem.length) { //Out of index
				image = null;
			}
			else {
				image = new ImageIcon( Main.getPath( aryStratagem[fixedIndex].getString("image") ) ).getImage();
			}
			aryPnlStratagem[i].setImage(image);
		}
	}
	
<<<<<<< HEAD
	
	//KeyAdapter
	private class InputListener extends KeyAdapter {
		HashMap<Integer, Character> dictKey = new HashMap<>() {
			private static final long serialVersionUID = 1L;
		{
=======
	private void resetStratagem() {
		commandIndex = 0;
		//Get stratagem queue
		aryStratagem = stratagem.getRandStratagem(10); //#TODO Difficulty Setting
		
		//reset index & draw queue
		setStratagemIndex(0);
	}
	
	private void nextStratagem() {
		time += 1.0d;
		score += aryStratagem[stratagemIndex].getString("command").length() * 5;
		commandIndex = 0;
		setStratagemIndex(stratagemIndex + 1);
		//TODO Add Round Clear
	}
	
	
	//KeyAdapter
	private class InputListener extends KeyAdapter {
		HashMap<Integer, Character> dictKey = new HashMap<>() {{
>>>>>>> fbd1545c969db09f9b9bcf51105b9b79e6cf7740
			put(KeyEvent.VK_W, 'U');
			put(KeyEvent.VK_S, 'D');
			put(KeyEvent.VK_A, 'L');
			put(KeyEvent.VK_D, 'R');
			put(KeyEvent.VK_UP, 'U');
			put(KeyEvent.VK_DOWN, 'D');
			put(KeyEvent.VK_LEFT, 'L');
			put(KeyEvent.VK_RIGHT, 'R');
			put(KeyEvent.VK_KP_UP, 'U');
			put(KeyEvent.VK_KP_DOWN, 'D');
			put(KeyEvent.VK_KP_LEFT, 'L');
			put(KeyEvent.VK_KP_RIGHT, 'R');
			put(KeyEvent.VK_NUMPAD8, 'U');
			put(KeyEvent.VK_NUMPAD5, 'D');
			put(KeyEvent.VK_NUMPAD4, 'L');
			put(KeyEvent.VK_NUMPAD6, 'R');
		}};
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch (strMenu) {
				case "Main":
					setMenu("GetReady");
					break;
				case "Play":
					if (dictKey.get( e.getKeyCode() ) == null) {
						break;
					}
<<<<<<< HEAD
					if (dictKey.get( e.getKeyCode() ) == pnlCommand.getNextCmd() ) { //Correct Command
						if ( pnlCommand.nextCmdIndex() ) {
							stratagemPerfect += ( flagStratagemPerfect ? 1 : 0 );
							nextStratagem();
							break;
						}
					}
					else { //Wrong Command
						flagStratagemPerfect = false;
						pnlCommand.resetCmdIndex();
=======
					String fullCommand = aryStratagem[stratagemIndex].getString("command");
					char nextCommand = fullCommand.charAt(commandIndex);
					if (dictKey.get( e.getKeyCode() ) == nextCommand) { //Correct Command
						commandIndex++;
						
						if (commandIndex >= fullCommand.length() ) {
							nextStratagem();
							break;
						}
						pnlCommand.setIndex(commandIndex);
					}
					else { //Wrong Command
						commandIndex = 0;
						pnlCommand.setIndex(commandIndex);
>>>>>>> fbd1545c969db09f9b9bcf51105b9b79e6cf7740
					}
					break;
			}
		} //keyPressed()
	}
	
	
	//ComponentAdapter
	private class FrameListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			double widthMul = getWidth() * 0.002;
			double heightMul = getHeight() * 0.004;
			sizeMul = Math.min(widthMul, heightMul);
		}
	}
} //Frame Class