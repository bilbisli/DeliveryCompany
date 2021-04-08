package program;

import components.MainOffice;
import components.Package;
import components.Truck;

/**
 * The class that operates the whole delivery system simulation
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see		MainOffice
 */
public class Game {
	/**
	 * The main function that operates the whole delivery system simulation
	 * @param args the arguments received to main
	 */
	public static void main (String[] args) {
		MainOffice game = new MainOffice(5, 4);
		game.play(60);
	}
}