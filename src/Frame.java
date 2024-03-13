import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	//Images
	
	//Components
	private BackgroundPanel backgroundPanel = new BackgroundPanel();
	
	private String strMenu = "Start";

	public Frame() {
		setTitle("Stratagem Hero");
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//Initialize
		setMinimumSize( new Dimension(1000, 500) );
		addKeyListener(this);
		
		//Move Frame to Middle
		Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)(resolution.getWidth() * 0.5) - (int)(getWidth() * 0.5);
		int y = (int)(resolution.getHeight() * 0.5) - (int)(getHeight() * 0.5);
		setLocation(x, y);
		
		//Initial Screen
		add(backgroundPanel, BorderLayout.CENTER);
	}
	
	//setMenu
	public void setMenu(String menu) {
		if (menu == strMenu) {
			return;
		}
		strMenu = menu;
		getContentPane().removeAll();
		add(backgroundPanel, BorderLayout.CENTER);
		System.out.println("Menu >> " + menu);
		
		switch (menu) {
			case "Main": //Main menu
				break;
			default: //Invalid menu name
				return;
		}
		
		repaint();
	} //setMenu
		
	//KeyListener
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		switch (strMenu) {
			case "Start":
				setMenu("Main");
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }
}

//ImagePanel Class
class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;
	private Color bgColor;
	int width, height;
	
	public ImagePanel(Image image, Color bgColor) {
		this.image = image;
		this.bgColor = bgColor;
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public ImagePanel(Image image) {
		this(image, null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (bgColor != null) {
			g.setColor(bgColor);
			g.fillRect( 0, 0, getWidth(), getHeight() );
		}
		
		double scaleX = (double)this.getWidth() / (double)width;
		double scaleY = (double)this.getHeight() / (double)height;
		double scale = Math.min(scaleX, scaleY);
		
		int scaledWidth = (int)Math.ceil(width * scale);
		int scaledHeight = (int)Math.ceil(height * scale);
		int x = (int)(this.getWidth() * 0.5 - scaledWidth * 0.5);
		int y = (int)(this.getHeight() * 0.5 - scaledHeight * 0.5);
		
		g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
	}
} //ImagePanel Class

class BackgroundPanel extends ImagePanel {
	private static final long serialVersionUID = 1L;
	private static final Image imgSuperEarth = new ImageIcon("rsc/SuperEarthLogo.png").getImage();

	public BackgroundPanel() {
		super(imgSuperEarth, Color.GRAY);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, 50, getWidth(), 5);
	}
}