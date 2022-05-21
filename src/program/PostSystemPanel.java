package program;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import components.*;
import components.Package;

import java.util.*;

/**
 * This class represents a panel that commits visualization of the delivery company
 * 
 * @version 3.00 12 July 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see CreatePostSystemlDialog
 * @see Main
 */
public class PostSystemPanel extends JPanel implements ActionListener {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The main frame that contains the panel
	 */
	private Main frame;
	/**
	 * 
	 */
	private JPanel p1;
	/**
	 * the panel
	 */
	private JButton[] b_num;
	/**
	 * the buttons of the system
	 */
	private String[] names = { "Create system", "Start", "Stop", "Resume", "All packages info", "Branch info",
			"CloneBranch", "Restore", "Report" };
	/**
	 * the packages info panel
	 */
	private JScrollPane scrollPane;
	/**
	 * flag that indicates if the packages info table is currently displayed
	 */
	private boolean isTableVisible = false;
	/**
	 * flag that indicates if the branch packages info table is currently displayed
	 */
	private boolean isTable2Visible = false;
	/**
	 * color indicator
	 */
	private int colorInd = 0;
	/**
	 * flag that indicated the system has been started
	 */
	private boolean started = false;
	/**
	 * the main office of the delivery company
	 */
	private MainOffice game = null;
	/**
	 * the packages number of the system
	 */
	private int packagesNumber;
	/**
	 * the branches number of the system
	 */
	private int branchesNumber;
	/**
	 * the trucks number of the system
	 */
	private int trucksNumber;

	/**
	 * class constructor
	 * @param f the main frame of the system
	 */
	public PostSystemPanel(Main f) {
		frame = f;
		isTableVisible = false;
		setBackground(new Color(255, 255, 255));
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 7, 0, 0));
		p1.setBackground(new Color(0, 150, 255));
		b_num = new JButton[names.length];

		for (int i = 0; i < names.length; i++) {
			b_num[i] = new JButton(names[i]);
			b_num[i].addActionListener(this);
			b_num[i].setBackground(Color.lightGray);
			p1.add(b_num[i]);
		}

		setLayout(new BorderLayout());
		add("South", p1);
	}

	/**
	 * creates a new delivery system to be displayed
	 * @param branches the branches number of the system
	 * @param trucks the trucks number of the system
	 * @param packages the packages number of the system
	 */
	public void createNewPostSystem(int branches, int trucks, int packages) {
		if (started)
			return;
		game = MainOffice.getInstance(branches, trucks, this, packages);
		packagesNumber = packages;
		trucksNumber = trucks;
		branchesNumber = branches;

		repaint();
	}

	/**
	 * Paint method for displaying the system on the panel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (game == null)
			return;

		Hub hub = MainOffice.getInstance().getHub();
		ArrayList<Branch> branches = hub.getBranches();

		int offset = 403 / (branchesNumber == 1 ? 1 : (branchesNumber - 1));
		int y = 100;
		int y2 = 246;
		int offset2 = 140 / (branchesNumber == 1 ? 1 : (branchesNumber - 1));

		g.setColor(new Color(0, 102, 0));
		g.fillRect(1120, 216, 40, 200);

		for (Branch br : branches) {
			br.paintComponent(g, y, y2);
			y += offset;
			y2 += offset2;
		}

		int x = 100;
		int offset3 = (1154 - 300) / (MainOffice.COSTUMERS_NUMBER * Costumer.COSTUMER_PACKAGE_NUMBER) + 15;

		for (int i = 0; i < game.getPackages().size(); ++i) {
			game.getPackages().get(i).paintComponent(g, x, offset);
			x += offset3;
		}

		for (Branch br : branches) {
			for (Truck tr : br.getTrucks()) {
				tr.paintComponent(g);
			}
		}

		for (Truck tr : hub.getTrucks()) {
			tr.paintComponent(g);
		}

	}

	/**
	 * @param ind the color indicator
	 */
	public void setColorIndex(int ind) {
		this.colorInd = ind;
		repaint();
	}

	/**
	 * Method for handling the background visualization
	 * @param num the color of background to display
	 */
	public void setBackgr(int num) {
		switch (num) {
		case 0:
			setBackground(new Color(255, 255, 255));
			break;
		case 1:
			setBackground(new Color(0, 150, 255));
			break;

		}
		repaint();
	}

	/**
	 * method that prompts the system creation dialog
	 */
	public void add() {
		CreatePostSystemlDialog dial = new CreatePostSystemlDialog(frame, this, "Create post system");
		dial.setVisible(true);
	}

	/**
	 * method to start the process of the system visualization
	 */
	public void start() {
		if (game == null || started)
			return;
		Thread t = new Thread(game);
		started = true;
		t.start();
	}

	/**
	 * method resumes the delivery system
	 */
	public void resume() {
		if (game == null)
			return;
		game.setResume();
	}

	/**
	 * method suspends the delivery system
	 */
	public void stop() {
		if (game == null)
			return;
		game.setSuspend();
	}

	/**
	 * method displays and handles the package info options
	 */
	public void info() {
		if (game == null || !started)
			return;
		if (isTable2Visible == true) {
			scrollPane.setVisible(false);
			isTable2Visible = false;
		}
		if (isTableVisible == false) {
			int i = 0;
			String[] columnNames = { "Package ID", "Sender", "Destination", "Priority", "Staus" };
			ArrayList<Package> packages = game.getPackages();
			String[][] data = new String[packages.size()][columnNames.length];
			for (Package p : packages) {
				data[i][0] = "" + p.getPackageID();
				data[i][1] = "" + p.getSenderAddress();
				data[i][2] = "" + p.getDestinationAddress();
				data[i][3] = "" + p.getPriority();
				data[i][4] = "" + p.getStatus();
				i++;
			}
			JTable table = new JTable(data, columnNames);
			scrollPane = new JScrollPane(table);
			scrollPane.setSize(450, table.getRowHeight() * (packages.size()) + 24);
			add(scrollPane, BorderLayout.CENTER);
			isTableVisible = true;
		} else
			isTableVisible = false;

		scrollPane.setVisible(isTableVisible);
		repaint();
	}

	/**
	 * method displays and handles the branch package info options
	 */
	public void branchInfo() {
		if (game == null || !started)
			return;

		if (scrollPane != null)
			scrollPane.setVisible(false);
		isTableVisible = false;
		isTable2Visible = false;
		String[] branchesStrs = new String[MainOffice.getInstance().getHub().getBranches().size() + 1];
		branchesStrs[0] = "Sorting center";
		for (int i = 1; i < branchesStrs.length; i++)
			branchesStrs[i] = "Branch " + i;
		JComboBox cb = new JComboBox(branchesStrs);
		String[] options = { "OK", "Cancel" };
		String title = "Choose branch";
		int selection = JOptionPane.showOptionDialog(null, cb, title, JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (selection == 1)
			return;
		// System.out.println(cb.getSelectedIndex());
		if (isTable2Visible == false) {
			int i = 0;
			String[] columnNames = { "Package ID", "Sender", "Destination", "Priority", "Staus" };
			Branch branch;
			List<Package> packages = null;
			int size = 0;
			if (cb.getSelectedIndex() == 0) {
				packages = MainOffice.getInstance().getHub().getPackages();
				size = packages.size();
			} else {
				packages = MainOffice.getInstance().getHub().getBranches().get(cb.getSelectedIndex() - 1).getPackages();
				size = packages.size();
				int diff = 0;
				for (Package p : packages) {
					if (p.getStatus() == Status.BRANCH_STORAGE) {
						diff++;
					}
				}
				size = size - diff / 2;
			}
			String[][] data = new String[size][columnNames.length];
			for (Package p : packages) {
				boolean flag = false;
				for (int j = 0; j < i; j++)
					if (data[j][0].equals("" + p.getPackageID())) {
						flag = true;
						break;
					}
				if (flag)
					continue;
				data[i][0] = "" + p.getPackageID();
				data[i][1] = "" + p.getSenderAddress();
				data[i][2] = "" + p.getDestinationAddress();
				data[i][3] = "" + p.getPriority();
				data[i][4] = "" + p.getStatus();
				i++;
			}
			JTable table = new JTable(data, columnNames);
			scrollPane = new JScrollPane(table);
			scrollPane.setSize(450, table.getRowHeight() * (size) + 24);
			add(scrollPane, BorderLayout.CENTER);
			isTable2Visible = true;
		} else
			isTable2Visible = false;

		scrollPane.setVisible(isTable2Visible);
		repaint();
	}

	/**
	 * method opens and displays the text file that stores all the packages tracking listing
	 */
	private void report() {
		try {
			File theFile = new File(MainOffice.TRACKING_FILE);
			if (Desktop.isDesktopSupported()) {
				Desktop system = Desktop.getDesktop();
				if (theFile.exists())
					system.open(theFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method for handling the branch cloning option of the system
	 */
	private void cloneBranch() {
		if (game == null)
			return;

		if (scrollPane != null)
			scrollPane.setVisible(false);
		isTableVisible = false;
		isTable2Visible = false;
		String[] branchesStrs = new String[MainOffice.getInstance().getHub().getBranches().size()];
		for (int i = 0; i < branchesStrs.length; i++)
			branchesStrs[i] = "Branch " + i;
		JComboBox cb = new JComboBox(branchesStrs);
		String[] options = { "OK", "Cancel" };
		String title = "Choose branch";
		int selection = JOptionPane.showOptionDialog(null, cb, title, JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (selection == 1)
			return;

		game.cloneFromPrototype(cb.getSelectedIndex());
		trucksNumber += trucksNumber / branchesNumber;
		++branchesNumber;

		repaint();
	}

	/**
	 * method handles the restoration of the system to the initial state (using memento DP)
	 */
	private void restore() {
		if (game == null)
			return;

		MainOffice.getInstance().stopSystem();
		MainOffice.getInstance().resetFile();
		MainOffice.getInstance().restore();
		game = MainOffice.getInstance();
		branchesNumber = MainOffice.getInstance().getHub().getBranches().size();
		trucksNumber = MainOffice.getInstance().getHub().getTrucks().size() - 1;

		started = false;
		start();
	}

	/**
	 * method for exiting the panel
	 */
	public void destroy() {
		System.exit(0);
	}

	/**
	 * method for handling the button actions of the system
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b_num[0])
			add();
		else if (e.getSource() == b_num[1])
			start();
		else if (e.getSource() == b_num[2])
			stop();
		else if (e.getSource() == b_num[3])
			resume();
		else if (e.getSource() == b_num[4])
			info();
		else if (e.getSource() == b_num[5])
			branchInfo();
		else if (e.getSource() == b_num[6])
			cloneBranch();
		else if (e.getSource() == b_num[7])
			restore();
		else if (e.getSource() == b_num[8])
			report();

	}

}