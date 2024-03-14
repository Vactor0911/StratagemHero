import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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


class MyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Image imgSuperEarth = new ImageIcon("rsc/SuperEarthEmblem.png").getImage();
	private final Image coloredImage;
	private final int WIDTH;
	private final int HEIGHT;
	private final Color COLOR_SUPER_EARTH = Color.GRAY;
	private final Color COLOR_BACKGROUND = Color.DARK_GRAY;
	private final Color COLOR_LINE = Color.WHITE;
	
	public MyPanel() {
		WIDTH = imgSuperEarth.getWidth(null);
		HEIGHT = imgSuperEarth.getHeight(null);
		coloredImage = ColorizeFilter.colorizeImg(imgSuperEarth, COLOR_SUPER_EARTH);
	}
	
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
	private int weight;
	private double multiplier;
	
	public TextLabel(String text, int align, int weight, double multiplier) {
		super(text, align);
		this.text = text;
		this.weight = weight;
		this.multiplier = multiplier;
		addComponentListener( new LabelListener() );
	}
	
	public TextLabel(String text, int weight, double multiplier) {
		this(text, JLabel.LEFT, weight, multiplier);
	}
	
	public TextLabel(String text, int weight) {
		this(text, JLabel.LEFT, weight, 1);
	}
	
	public TextLabel(String text) {
		this(text, JLabel.LEFT, Font.PLAIN, 1);
	}
	
	private class LabelListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			int fontSize = (int)(getWidth() * multiplier);
			Font font = new Font(FONT_NAME, weight, fontSize);
			setFont(font);
		}
	} //LabelListener Class
	
}