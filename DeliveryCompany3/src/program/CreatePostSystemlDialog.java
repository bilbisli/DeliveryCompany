package program;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * This class represents the system creation option dialog
 * 
 * @version 3.00 12 July 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see PostSystemPanel
 */
public class CreatePostSystemlDialog extends JDialog implements ActionListener {
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * p1 - the slider options sub-panel
	 * p2 - the buttons sub-panel
	 */
	private JPanel p1, p2;
	/**
	 * the buttons
	 */
	private JButton ok, cancel;
	/**
	 * the labels
	 */
	private JLabel lbl_br, lbl_tr, lbl_pk;
	/**
	 * the sliders
	 */
	private JSlider sl_br, sl_tr, sl_pk;
	/**
	 * the visualization panel
	 */
	private PostSystemPanel rs;

	/**
	 * Class constructor
	 * @param parent the main frame
	 * @param pan the visualization panel
	 * @param title the title of the dialog
	 */
	public CreatePostSystemlDialog(Main parent, PostSystemPanel pan, String title) {
		super((Main) parent, title, true);
		rs = pan;

		setSize(600, 400);

		setBackground(new Color(100, 230, 255));
		p1 = new JPanel();
		p2 = new JPanel();

		p1.setLayout(new GridLayout(6, 1, 10, 5));
		lbl_br = new JLabel("Number of branches", JLabel.CENTER);
		p1.add(lbl_br);
		lbl_tr = new JLabel("Number of trucks per branch", JLabel.CENTER);
		lbl_pk = new JLabel("Number of packages", JLabel.CENTER);

		sl_br = new JSlider(1, 10);
		sl_br.setMajorTickSpacing(1);
		sl_br.setMinorTickSpacing(1);
		sl_br.setPaintTicks(true);
		sl_br.setPaintLabels(true);
		p1.add(sl_br);

		p1.add(lbl_tr);
		sl_tr = new JSlider(1, 10);
		sl_tr.setMajorTickSpacing(1);
		sl_tr.setMinorTickSpacing(1);
		sl_tr.setPaintTicks(true);
		sl_tr.setPaintLabels(true);
		p1.add(sl_tr);

		p1.add(lbl_pk);
		sl_pk = new JSlider(2, 20);
		sl_pk.setMajorTickSpacing(2);
		sl_pk.setMinorTickSpacing(1);
		sl_pk.setPaintTicks(true);
		sl_pk.setPaintLabels(true);
		p1.add(sl_pk);

		p2.setLayout(new GridLayout(1, 2, 5, 5));
		ok = new JButton("OK");
		ok.addActionListener(this);
		ok.setBackground(Color.lightGray);
		p2.add(ok);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		cancel.setBackground(Color.lightGray);
		p2.add(cancel);

		setLayout(new BorderLayout());
		add("North", p1);
		add("South", p2);
	}

	/**
	 * Method handles the button actions
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			rs.createNewPostSystem(sl_br.getValue(), sl_tr.getValue(), sl_pk.getValue());
			setVisible(false);
		} else
			setVisible(false);
	}
}
