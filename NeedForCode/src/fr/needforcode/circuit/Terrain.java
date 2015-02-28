package fr.needforcode.circuit;

import java.awt.Color;

/**
 * Enum Terrain
 * @author Camille
 *
 */
public enum Terrain {
	Route, Herbe, Eau, Obstacle, BandeRouge, BandeBlanche, StartPoint, EndLine, m, Out, Voiture;
	
	/**
	 * Tableau de correspondance Terrain/char
	 */
	public static char[] conversion = {'.', 'g', 'b', 'o', 'r', 'w', '*', '!', 'm','n','v'};
	
	/**
	 * Tableau de correspondance Terrain/color
	 */
	public static Color[] convColor = {Color.gray, Color.green.darker(),
		Color.blue, Color.black, Color.red, Color.white,
		Color.cyan, Color.cyan, new Color(200, 150, 128),Color.darkGray};
}