import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.*;
import java.io.Serializable;
import javax.swing.*;


class ColorizeFilter extends RGBImageFilter implements Serializable {
	private static final long serialVersionUID = 1L;
    private int color;

    public ColorizeFilter(int color) {
        this.color = color;
        canFilterIndexColorModel = true;
    }
    
    public static Image colorizeImg(Image img, Color color) {
		RGBImageFilter filter = new ColorizeFilter( color.getRGB() );
		ImageProducer producer = new FilteredImageSource(img.getSource(), filter);
		Image coloredImg = Toolkit.getDefaultToolkit().createImage(producer);
		return coloredImg;
	}

    @Override
    public int filterRGB(int x, int y, int rgb) {
        int alpha = (rgb >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
} //ColorizeFilter Class


class BackPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image imgSuperEarth = new ImageIcon("rsc/SuperEarthEmblem.png").getImage();
	private final Image coloredImage;
	private final int WIDTH;
	private final int HEIGHT;
	private final Color COLOR_SUPER_EARTH = Color.GRAY;
	private final Color COLOR_BACKGROUND = Color.DARK_GRAY;
	private final Color COLOR_LINE = Color.WHITE;
	
	//ID
	static final int NORTH = 1;
	static final int SOUTH = 2;
	static final int WEST = 3;
	static final int EAST = 4;
	static final int CENTER = 5;
	
	//Components
	private JPanel pnlNorth = new JPanel();
	private JPanel pnlSouth = new JPanel();
	private JPanel pnlEast = new JPanel();
	private JPanel pnlWest = new JPanel();
	private JPanel pnlTemp = new JPanel();
	private JPanel pnlCenter = new JPanel();
	
	public BackPanel() {
		WIDTH = imgSuperEarth.getWidth(null);
		HEIGHT = imgSuperEarth.getHeight(null);
		coloredImage = ColorizeFilter.colorizeImg(imgSuperEarth, COLOR_SUPER_EARTH);
		
		setLayout( new GridBagLayout() );
		pnlTemp.setLayout( new GridBagLayout() );
		
		pnlNorth.setBackground(Color.red);
		pnlSouth.setBackground(Color.blue);
		pnlWest.setBackground(Color.yellow);
		pnlEast.setBackground(Color.green);
		
        pnlNorth.setOpaque(false);
        pnlSouth.setOpaque(false);
        pnlWest.setOpaque(false);
        pnlEast.setOpaque(false);
		pnlCenter.setOpaque(false);
		pnlTemp.setOpaque(false);
		
		add( pnlNorth, Main.getGbc(0, 0, 1.0, 0.25) );
		add( pnlTemp, Main.getGbc(0, 1, 1.0, 0.5) );
		add( pnlSouth, Main.getGbc(0, 2, 1.0, 0.25) );
		
		pnlTemp.add( pnlWest, Main.getGbc(0, 0, 0.25, 1.0) );
		pnlTemp.add( pnlCenter, Main.getGbc(1, 0, 0.5, 1.0) );
		pnlTemp.add( pnlEast, Main.getGbc(2, 0, 0.25, 1.0) );
	}
	
	public JPanel getPanel(int id) {
		switch (id) {
			case NORTH:
				return pnlNorth;
			case SOUTH:
				return pnlSouth;
			case WEST:
				return pnlWest;
			case EAST:
				return pnlEast;
			case CENTER:
				return pnlCenter;
			default:
				return null;
		}
	}
	
	public void addComp(Component c) {
		pnlCenter.add(c);
	}
	
	public void addComp(Component c, Object constraints) {
		pnlCenter.add(c, constraints);
	}
	
	public void changeLayout(LayoutManager lm) {
		pnlCenter.setLayout(lm);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Background Coloring
		g.setColor(COLOR_BACKGROUND);
		g.fillRect( 0, 0, getWidth(), getHeight() );
		
		//Draw SuperEarth Emblem
		double scaleX = (double)this.getWidth() / (double)WIDTH;
		double scaleY = (double)this.getHeight() / (double)HEIGHT;
		double scale = Math.min(scaleX, scaleY) * 0.5;
		
		int scaledWidth = (int)Math.ceil(WIDTH * scale);
		int scaledHeight = (int)Math.ceil(HEIGHT * scale);
		int x = (int)(this.getWidth() * 0.5 - scaledWidth * 0.5);
		int y = (int)(this.getHeight() * 0.5 - scaledHeight * 0.5);
		
		g.drawImage(coloredImage, x, y, scaledWidth, scaledHeight, this);
		
		//Draw Lines
		g.setColor(COLOR_LINE);
		int lineHeight = (int)(scaledHeight * 0.02);
		int lineY = (int)(scaledHeight * 0.25);
		g.fillRect(0, lineY - lineHeight / 2, getWidth(), lineHeight);
		g.fillRect(0, getHeight() - lineY - lineHeight / 2, getWidth(), lineHeight);
	} //paintComponent()
} //MyPanel Class


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
		
		g.setColor(bgColor);
		g.fillRect( 0, 0, getWidth(), getHeight() );
		
		double scaleX = (double)this.getWidth() / (double)width;
		double scaleY = (double)this.getHeight() / (double)height;
		double scale = Math.min(scaleX, scaleY);
		
		int scaledWidth = (int)Math.ceil(width * scale);
		int scaledHeight = (int)Math.ceil(height * scale);
		int x = (int)(this.getWidth() * 0.5 - scaledWidth * 0.5);
		int y = (int)(this.getHeight() * 0.5 - scaledHeight * 0.5);
		
		g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
		
		g.setColor(Color.red);
		g.fillRect(x, y, scaledWidth, scaledHeight);
	} //paintComponent()
} //ImagePanel Class


class TextLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private final String FONT_NAME = "dialog";
	private String text;
	private int size;
	private int weight;
	private Dimension dim = new Dimension(0, 0);
	
	public TextLabel(String text, int size, int align, int weight) {
		super(text, align);
		this.text = text;
		this.size = size;
		this.weight = weight;
		setOpaque(false);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
	}
	
	public void resize(double sizeMul) {
		if (Double.isNaN(sizeMul) || sizeMul <= 0) {
			return;
		}
		
		double fontSize = (double)size * sizeMul;
		Font font = new Font(FONT_NAME, weight, (int)fontSize);
		setFont(font);
	}
} //TextLabel Class