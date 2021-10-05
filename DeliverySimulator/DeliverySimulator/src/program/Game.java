package program;

import java.awt.Color;
import java.awt.Graphics;

import GUI.Menu;
import components.MainOffice;
/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public class Game {

	public static void main(String[] args) {
		//new Menu();
		
		MainOffice game=new MainOffice(5, 4,2);
		Thread T=new Thread(game);
		T.start();
		//game.play(60);
		
	}

}
