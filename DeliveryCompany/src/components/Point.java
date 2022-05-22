package components;

import java.io.Serializable;

/**
 * This class represents a point in the post system panel standard packages (one
 * at a time)
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Package
 * @see Truck
 */
public class Point implements Serializable {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the x axis position
	 */
	private int x;
	/**
	 * the y axis position
	 */
	private int y;

	/**
	 * Class constructor
	 * @param x the x axis position
	 * @param y the y axis position
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;

	}

	/**
	 * Get method for the x axis position
	 * @return the x axis position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get method for the y axis position
	 * @return the y axis position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set method for the x axis position
	 * @param x the x axis position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * SGet method for the y axis position
	 * @param y the y axis position
	 */
	public void setY(int y) {
		this.y = y;
	}
}
