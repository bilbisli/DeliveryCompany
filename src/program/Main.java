package program;

import javax.swing.*;

/**
 * This class represents the main frame that contains the visualization of the delivery company
 * 
 * @version 3.00 12 July 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see CreatePostSystemlDialog
 * @see Main
 * @see PostSystemPanel
 */
public class Main extends JFrame {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the main panel of the system
	 */
	private PostSystemPanel panel;

	/**
	 * main function
	 * @param args arguments sent to this program
	 */
	public static void main(String[] args) {
		Main fr = new Main();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setSize(1200, 700);
		fr.setVisible(true);
	}

	/**
	 * Class constructor
	 */
	public Main() {
		super("Post tracking system");
		panel = new PostSystemPanel(this);
		add(panel);
		panel.setVisible(true);
	}

}
