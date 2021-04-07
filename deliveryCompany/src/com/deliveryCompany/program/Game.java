package com.deliveryCompany.program;

import com.deliveryCompany.components.MainOffice;
import com.deliveryCompany.components.Package;
import com.deliveryCompany.components.Truck;

/**
 * The class that operates the whole delivery system simulation
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail
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