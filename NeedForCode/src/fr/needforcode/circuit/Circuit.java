package fr.needforcode.circuit;

import java.util.ArrayList;
import fr.needforcode.geometrie.Vecteur;

/**
 * Interface circuit
 * @author Camille
 *
 */
public interface Circuit {
	
	public Terrain getTerrain(int i, int j);
	
	public Terrain getTerrain(Vecteur p);
	
	public Vecteur getPointDepart();
	
	public Vecteur getDirectionDepart();
	
	public Vecteur getDirectionArrivee();
	
	public int getWidth();
	
	public int getHeight();
	
	public ArrayList<Vecteur> getListeArrivees();
	
	public double getDist(int i, int j);
	
	public String getName();
	
}