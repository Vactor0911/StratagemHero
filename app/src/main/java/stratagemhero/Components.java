package stratagemhero;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class BackPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Image imgSuperEarth = new ImageIcon(Main.getPath("/SuperEarthEmblem.png")).getImage();
    private final Image coloredImage;
    private final int WIDTH;
    private final int HEIGHT;
    private final Color COLOR_SUPER_EARTH = Color.GRAY;
    private final Color COLOR_BACKGROUND = Color.DARK_GRAY;
    private final Color COLOR_LINE = Color.WHITE;

    // ID
    static final int NORTH = 1;
    static final int SOUTH = 2;
    static final int WEST = 3;
    static final int EAST = 4;
    static final int CENTER = 5;

    // Components
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

        setLayout(new GridBagLayout());
        pnlTemp.setLayout(new GridBagLayout());

        pnlNorth.setOpaque(false);
        pnlSouth.setOpaque(false);
        pnlWest.setOpaque(false);
        pnlEast.setOpaque(false);
        pnlCenter.setOpaque(false);
        pnlTemp.setOpaque(false);

        add(pnlNorth, Main.getGbc(0, 0, 1.0, 0.25));
        add(pnlTemp, Main.getGbc(0, 1, 1.0, 0.5));
        add(pnlSouth, Main.getGbc(0, 2, 1.0, 0.25));

        pnlTemp.add(pnlWest, Main.getGbc(0, 0, 0.25, 1.0));
        pnlTemp.add(pnlCenter, Main.getGbc(1, 0, 0.5, 1.0));
        pnlTemp.add(pnlEast, Main.getGbc(2, 0, 0.25, 1.0));
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

        // Background Coloring
        g.setColor(COLOR_BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw SuperEarth Emblem
        double scaleX = (double) this.getWidth() / (double) WIDTH;
        double scaleY = (double) this.getHeight() / (double) HEIGHT;
        double scale = Math.min(scaleX, scaleY) * 0.5;

        int scaledWidth = (int) Math.ceil(WIDTH * scale);
        int scaledHeight = (int) Math.ceil(HEIGHT * scale);
        int x = (int) (this.getWidth() * 0.5 - scaledWidth * 0.5);
        int y = (int) (this.getHeight() * 0.5 - scaledHeight * 0.5);

        g.drawImage(coloredImage, x, y, scaledWidth, scaledHeight, this);

        // Draw Lines
        g.setColor(COLOR_LINE);
        int lineHeight = (int) (scaledHeight * 0.02);
        int lineY = (int) (scaledHeight * 0.25);
        g.fillRect(0, lineY - lineHeight / 2, getWidth(), lineHeight);
        g.fillRect(0, getHeight() - lineY - lineHeight / 2, getWidth(), lineHeight);
    } // paintComponent()
} // BackPanel Class

class ImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Image image;
    private Color bgColor;
    private int width = 0;
    private int height = 0;

    public ImagePanel(Image image, Color bgColor) {
        if (image == null) {
            return;
        }

        this.image = image;
        this.bgColor = bgColor;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public ImagePanel(Image image) {
        this(image, null);
        setOpaque(false);
    }

    public ImagePanel() {
        this(null, null);
        setOpaque(false);
    }

    public void setImage(Image img) {
        this.image = img;

        if (img == null) {
            width = 0;
            height = 0;
        } else {
            width = image.getWidth(null);
            height = image.getHeight(null);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgColor != null) {
            g.setColor(bgColor);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        double scaleX = (double) this.getWidth() / (double) width;
        double scaleY = (double) this.getHeight() / (double) height;
        double scale = Math.min(scaleX, scaleY);

        int scaledWidth = (int) Math.ceil(width * scale);
        int scaledHeight = (int) Math.ceil(height * scale);
        int x = (int) (this.getWidth() * 0.5 - scaledWidth * 0.5);
        int y = (int) (this.getHeight() * 0.5 - scaledHeight * 0.5);

        g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
    } // paintComponent()
} // ImagePanel Class

class TextLabel extends JLabel {
    private static final long serialVersionUID = 1L;
    private final String FONT_NAME = "dialog";
    private int size;
    private int weight;
    private Dimension dim = new Dimension(0, 0);

    public TextLabel(String text, int size, int align, int weight) {
        super(text, align);
        this.size = size;
        this.weight = weight;
        setOpaque(false);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        double sizeMul = Main.getFrame().getSizeMul();
        if (Double.isNaN(sizeMul) || sizeMul <= 0) {
            return;
        }

        double fontSize = (double) size * sizeMul;
        Font font = new Font(FONT_NAME, weight, (int) fontSize);
        setFont(font);
    }
} // TextLabel Class

class ProgressBar extends JPanel {
    private static final long serialVersionUID = 1L;
    private double progress = 0.0d;

    public ProgressBar(double progress) {
        this.progress = progress;
        setBackground(Color.LIGHT_GRAY);
        repaint();
    }

    public ProgressBar() {
        this(1.0d);
    }

    public void setProgress(double progress) {
        this.progress = Math.max(progress, 0.0);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.YELLOW);
        int width = (int) Math.round((double) getWidth() * progress);
        g.fillRect(0, 0, width, getHeight());
    }
} // ProgressBar Class

class CommandPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Image imgUp = new ImageIcon(Main.getPath("/Arrows/arrow_up.png")).getImage();
    private final Image imgDown = new ImageIcon(Main.getPath("/Arrows/arrow_down.png")).getImage();
    private final Image imgLeft = new ImageIcon(Main.getPath("/Arrows/arrow_left.png")).getImage();
    private final Image imgRight = new ImageIcon(Main.getPath("/Arrows/arrow_right.png")).getImage();
    private final HashMap<Character, Image[]> dictArrow = new HashMap<>();
    private final int width = imgUp.getWidth(null);
    private final int height = imgUp.getHeight(null);
    private String command = "";
    private int index = 0;

    public CommandPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        // Get Colored Image (White & Yellow)
        char[] aryKey = { 'U', 'D', 'L', 'R' };
        Image[] aryImage = { imgUp, imgDown, imgLeft, imgRight };
        for (int i = 0; i < aryKey.length; i++) {
            Image image = aryImage[i];
            Image imageWhite = ColorizeFilter.colorizeImg(image, Color.WHITE);
            Image imageYellow = ColorizeFilter.colorizeImg(image, Color.YELLOW);
            Image[] aryColoredImage = { imageWhite, imageYellow };
            dictArrow.put(aryKey[i], aryColoredImage);
        }
    }

    // Getter
    public int getCmdIndex() {
        return index;
    }

    public char getNextCmd() {
        return command.charAt(index);
    }

    public void setCommand(String command) {
        this.command = command;
        resetCmdIndex();
    }

    public boolean nextCmdIndex() {
        index++;

        if (index >= command.length()) {
            index = 0;
            return true;
        }

        repaint();
        return false;
    }

    public void resetCmdIndex() {
        index = 0;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        double scaleX = (double) this.getWidth() / (double) (width * command.length());
        double scaleY = (double) this.getHeight() / (double) height;
        double scale = Math.min(scaleX, scaleY);

        int totalWidth = (int) Math.ceil(width * command.length() * scale);
        int scaledWidth = (int) Math.ceil(width * scale);
        int scaledHeight = (int) Math.ceil(height * scale);
        int x = (int) (this.getWidth() * 0.5 - totalWidth * 0.5);
        int y = (int) (this.getHeight() * 0.5 - scaledHeight * 0.5);

        for (int i = 0; i < command.length(); i++) {
            Image image;
            if (i > index - 1) {
                image = dictArrow.get(command.charAt(i))[0];
            } else {
                image = dictArrow.get(command.charAt(i))[1];
            }
            g.drawImage(image, x + scaledWidth * i, y, scaledWidth, scaledHeight, this);
        }
    }
} // CommandPanel Class
