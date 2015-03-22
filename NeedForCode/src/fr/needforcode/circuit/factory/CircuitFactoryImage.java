package fr.needforcode.circuit.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.CircuitImpl;
import fr.needforcode.circuit.Jalon;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.geometrie.Vecteur;

/**
 * Class CircuitFactoryImage
 * Idem CircuitFactory mais se base sur une image.
 * Utilisé pour la génération de trk à partir d'une image.
 * @author camille
 *
 */
public class CircuitFactoryImage {
	
	private String filename;
	
	public CircuitFactoryImage(String filename) {
		this.filename = filename;
	}
	
	public Circuit build() {
		Terrain[][] matrice;
		Vecteur ptDepart = null;
		Vecteur sensDepart = new Vecteur(0, 1);
		Vecteur sensArrivee = new Vecteur(0, 1);
		ArrayList<Vecteur> listeArrivees = new ArrayList<Vecteur>();
		ArrayList<Jalon> listeJalons = new ArrayList<Jalon>();
		BufferedImage im = null;
		File file = new File(filename);
		String filenameWithOutExt = file.getName();
		int pos = filenameWithOutExt.lastIndexOf(".");
	
		if (pos > 0) {
			filenameWithOutExt = filenameWithOutExt.substring(0, pos);
		}
		
		try {
			im = ImageIO.read(file);
		} catch (IOException e1) {
				e1.printStackTrace();
		}
		matrice = new Terrain[im.getHeight()][im.getWidth()];
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[0].length; j++) {
				matrice[i][j] = TerrainTools.terrainFromColor(im.getRGB(j, i));
				if (matrice[i][j] == Terrain.StartPoint)
					ptDepart = new Vecteur(i, j);
				if (matrice[i][j] == Terrain.EndLine)
					listeArrivees.add(new Vecteur(i, j));
			}
		}
		return new CircuitImpl(matrice, ptDepart, sensDepart, sensArrivee,listeArrivees, filenameWithOutExt, listeJalons);
	}
}
