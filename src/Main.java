import java.awt.GridBagConstraints;

public class Main {
	private static Frame frame;
	private static GridBagConstraints gbc = new GridBagConstraints();

	public static void main(String[] args) {
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
		frame = new Frame();
	}
	
	public static Frame getFrame() {
		return frame;
	}
	
	public static GridBagConstraints getGbc(int x, int y, double weightX, double weightY, int width, int height) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = weightX;
		gbc.weighty = weightY;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		return gbc;
	}
	
	public static GridBagConstraints getGbc(int x, int y, double weightX, double weightY) {
		return getGbc(x, y, weightX, weightY, 1, 1);
	}
	
	public static String getPath(String path) {
		return Main.class.getResource(path).getPath();
	}

}
