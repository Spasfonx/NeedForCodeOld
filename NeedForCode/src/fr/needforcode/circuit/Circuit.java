package fr.needforcode.circuit;

import java.util.ArrayList;
import java.util.HashMap;

import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.pilote.champsDeVision;
import fr.needforcode.voiture.Voiture;

/**
 * Interface circuit!
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
	
	public Terrain[][] getChampsDeVision(Voiture v,HashMap<Equipe,Voiture> listeVoitures);
	
	//public boolean isCar(Vecteur pos, HashMap<Equipe,Voiture> listeVoitures);
	
	public ArrayList<Jalon> getListeJalons();
	
	public int getLargeurCircuit();
	
	public void setLargeurCircuit(int largeurCircuit);
}