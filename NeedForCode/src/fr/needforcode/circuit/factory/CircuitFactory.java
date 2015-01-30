package fr.needforcode.circuit.factory;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import fr.needforcode.circuit.CircuitImpl;
import fr.needforcode.circuit.CircuitModifiable;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.geometrie.Vecteur;

/**
 * DP FACTORY
 * class CircuitFactory : construit un circuit à partir d'un fichier source (.trk)
 * @author camille
 *
 */
public class CircuitFactory {
	private String filename;
	
	/**
	 * Stockage du nom du fichier source
	 * @param filename
	 */
	public CircuitFactory(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Construction du circuit
	 * @return le CircuitModifiable créer à partir du fichier source
	 */
	public CircuitModifiable build() {
		Terrain[][] matrice;
		Vecteur ptDepart = null;
		Vecteur sensDepart = new Vecteur(0, 1);
		Vecteur sensArrivee = new Vecteur(0, 1);
		ArrayList<Vecteur> listeArrivees = new ArrayList<Vecteur>();
		File file = new File(filename);
		String filenameWithOutExt = file.getName();
		int pos = filenameWithOutExt.lastIndexOf(".");
		
		if (pos > 0) {
			filenameWithOutExt = filenameWithOutExt.substring(0, pos);
		}
		try {
			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String buf = in.readLine();
			int col = Integer.parseInt(buf);
			buf = in.readLine();
			int lig = Integer.parseInt(buf);
			matrice = new Terrain[lig][col];
			int ligCourante = 0;
			
			while ((buf = in.readLine()) != null) {
				for (int j = 0; j < buf.length(); j++) {
					matrice[ligCourante][j] = TerrainTools.terrainFromChar(buf.charAt(j));
					if (matrice[ligCourante][j] == Terrain.StartPoint) {
						ptDepart = new Vecteur(ligCourante, j);
					}
					if (matrice[ligCourante][j] == Terrain.EndLine) {
						listeArrivees.add(new Vecteur(ligCourante, j));
					}
				}
				ligCourante++;
			}
			in.close();
			return new CircuitImpl(matrice, ptDepart, sensDepart, sensArrivee,listeArrivees, filenameWithOutExt);
		} 
		catch (FileNotFoundException e) {
			System.err.println("Unable to find " + filename);
		}
		catch (IOException e) {
			System.err.println("Can't open file " + filename + " for reading... Loading aborted");
		}
		catch (Exception e) {
			System.err.println("Invalid Format : " + filename + "... Loading aborted");
		}
		return null;
	}
}
