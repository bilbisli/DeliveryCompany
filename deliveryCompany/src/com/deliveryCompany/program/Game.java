package com.deliveryCompany.program;

import com.deliveryCompany.components.MainOffice;

public class Game {
	/**
	 * @param args
	 */
	public static void main (String[] args) {
		MainOffice game = new MainOffice(5, 4);
		game.play(60);
	}
}