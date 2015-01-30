package fr.needforcode.circuit;

import java.util.ArrayList;
//import fr.needforcode.algo.Dijkstra;
import fr.needforcode.geometrie.Vecteur;

/**
 * Impl�mentation d'un circuit
 * @author Camille
 *
 */
public class CircuitImpl implements CircuitModifiable {
	private Terrain[][] matrice;
	private Vecteur ptDepart;
	private Vecteur sensDepart;
	private Vecteur sensArrivee;
	private ArrayList<Vecteur> listeArrivees;
	//private Dijkstra dijkstra;
	private String name; /* n�cessaire pour sauvegarder */
	
	/**
	 * Constructeur param�tr�
	 * @param matrice terrain
	 * @param ptDepart
	 * @param sensDepart
	 * @param sensArrivee
	 * @param listeArrivees
	 * @param name
	 */
	public CircuitImpl(Terrain[][] matrice, Vecteur ptDepart,Vecteur sensDepart, Vecteur sensArrivee,ArrayList<Vecteur> listeArrivees, String name) {
		this.matrice = matrice;
		this.ptDepart = ptDepart;
		this.sensDepart = sensDepart;
		this.sensArrivee = sensArrivee;
		this.listeArrivees = listeArrivees;
		this.name = name;
		//dijkstra = new Dijkstra(this);
	}
	
//	public void majDijkstra() {
//		dijkstra = new Dijkstra(this);
//	}
	
	public String getName() {
		return name;
	}
	
	public Terrain getTerrain(int i, int j) {
		return matrice[i][j];
	}
	
	@Override
	public Terrain getTerrain(Vecteur p) {
		return matrice[(int) p.getX()][(int) p.getY()];
	}
	
	@Override
	public Vecteur getPointDepart() {
		return ptDepart;
	}
	
	@Override
	public Vecteur getDirectionDepart() {
		return sensDepart;
	}
	
	@Override
	public Vecteur getDirectionArrivee() {
		return sensArrivee;
	}
	
	@Override
	public int getWidth() {
		return matrice[0].length;
	}
	
	@Override
	public int getHeight() {
		return matrice.length;
	}
	
	@Override
	public ArrayList<Vecteur> getListeArrivees() {
		return listeArrivees;
	}
	
	@Override
	public void setTerrain(int i, int j, Terrain t) {
		matrice[i][j] = t;
	}
	
	@Override
	public double getDist(int i, int j) {
		//return dijkstra.getDist(i, j);
		return 0.0;
	}
}