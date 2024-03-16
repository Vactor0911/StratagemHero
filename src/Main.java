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
	
	public static GridBagConstraints getGbc(int x, int y, double weightX, double weightY) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = weightX;
		gbc.weighty = weightY;
		return gbc;
	}

}
