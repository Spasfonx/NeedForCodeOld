package fr.needforcode.circuit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fr.needforcode.circuit.factory.CircuitFactory;
import fr.needforcode.geometrie.Vecteur;

/**
 * Classe outils Terrain(méthodes static)
 * @author Camille
 *
 */
public class TerrainTools {

	/**
	 * Conversion char/Terrain
	 * @param c character
	 * @return Terrain correspondant au char
	 */
	public static Terrain terrainFromChar(char c) {
		switch (c) {
			case 'b':
				return Terrain.Eau;
			case '.':
				return Terrain.Route;
			case 'w':
				return Terrain.BandeBlanche;
			case 'r':
				return Terrain.BandeRouge;
			case '*':
				return Terrain.StartPoint;
			case '!':
				return Terrain.EndLine;
			case '%':
				return Terrain.Obstacle;
			case 'v':
				return Terrain.Voiture;
			case 'n':
				return Terrain.Out;
			default:
				return Terrain.Herbe;
		}
	}
	/**
	 * Conversion Terrain/char
	 * @param t Terrain
	 * @return char correspondant au Terrain
	 */
	public static char charFromTerrain(Terrain t) {
		switch (t) {
			case Route:
				return '.';
			case Herbe:
				return 'g';
			case Eau:
				return 'b';
			case Obstacle:
				return '%';
			case BandeBlanche:
				return 'w';
			case BandeRouge:
				return 'r';
			case StartPoint:
				return '*';
			case EndLine:
				return '!';
			case Out:
				return 'n';
			case Voiture:
				return 'v';
			default:
				return '.'; /* herbe */
		}
	}
	
	/**
	 * Conversion terrain/color
	 * @param colr valeur
	 * @return Terrain correspondant à la color
	 */
	public static Terrain terrainFromColor(int colr) {
		if (colr == Color.gray.getRGB())
			return Terrain.Route;
		if (colr == Color.blue.getRGB())
			return Terrain.Eau;
		if (colr == Color.black.getRGB())
			return Terrain.Obstacle;
		if (colr == Color.white.getRGB())
			return Terrain.BandeBlanche;
		if (colr == Color.red.getRGB())
			return Terrain.BandeRouge;
		if (colr == Color.magenta.getRGB())
			return Terrain.StartPoint;
		if (colr == Color.cyan.getRGB())
			return Terrain.EndLine;
		
		return Terrain.Herbe;
	}
	
	/**
	 * conversion Terrain/color
	 * @param t Terrain
	 * @return La valeur color correspondant au Terrain
	 */
	public static int colorFromTerrain(Terrain t) {
		switch (t) {
			case Route:
				return Color.gray.getRGB();
			case Herbe:
				return Color.green.darker().getRGB();
			case Eau:
				return Color.blue.getRGB();
			case Obstacle:
				return Color.black.getRGB();
			case BandeBlanche:
				return Color.white.getRGB();
			case BandeRouge:
				return Color.red.getRGB();
			case StartPoint:
				return Color.magenta.getRGB();
			case EndLine:
				return Color.cyan.getRGB();
			default:
				return (Color.orange.getRGB());
		}
	}

	/**
	 * Création d'une image tampon à partir d'un circuit
	 * @param c Circuit
	 * @return BufferedImage(image tampon) correspondant au circuit.
	 */
	public static BufferedImage imageFromCircuit(Circuit c) {
		System.out.println(c.getHeight());
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < c.getHeight(); i++) {
			for (int j = 0; j < c.getWidth(); j++) {
				im.setRGB(j, i, colorFromTerrain(c.getTerrain(i, j)));
			}
		}
		return im;
	}
	
	/**
	 * Test si le terrain peut être parcouru par la voiture
	 * @param t Terrain
	 * @return True si le terrain peut être parcouru, false sinon
	 */
	public static boolean isRunnable(Terrain t) {
		if (t == Terrain.BandeBlanche || t == Terrain.BandeRouge || t == Terrain.Route || t == Terrain.Out )
			return true;
		return false;
	}
	
	/**
	 * Créer une image png(dans le dossier /png) à partir d'un fichier source (.trk dans le dossier trk par defaut)
	 * @param filename Nom du fichier source
	 */
	public static void previsualisation(String filename) {
		CircuitFactory cF = new CircuitFactory(filename);
		Circuit c = cF.build();
		BufferedImage im = imageFromCircuit(c);
		try {
			ImageIO.write(im, "png", new File("./png/" + filename.substring(6) + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
