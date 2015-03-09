package fr.needforcode.circuit;

import java.util.ArrayList;
import java.util.HashMap;



import java.util.Map.Entry;

import fr.needforcode.equipe.Equipe;
//import fr.needforcode.algo.Dijkstra;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.pilote.champsDeVision;
import fr.needforcode.voiture.Voiture;

/**
 * Implémentation d'un circuit
 * @author Camille
 *
 */
public class CircuitImpl implements CircuitModifiable {
	private Terrain[][] matrice;
	private Vecteur ptDepart;
	private Vecteur sensDepart;
	private Vecteur sensArrivee;
	private ArrayList<Vecteur> listeArrivees;
	private ArrayList<Jalon> listeJalons;
	private HashMap<Voiture,Vecteur> listeVoitures;
	//private Dijkstra dijkstra;
	private String name; /* nécessaire pour sauvegarder */
	private Vecteur curseur;
	
	/**
	 * Constructeur paramétré
	 * @param matrice terrain
	 * @param ptDepart
	 * @param sensDepart
	 * @param sensArrivee
	 * @param listeArrivees
	 * @param name
	 */
	public CircuitImpl(Terrain[][] matrice, Vecteur ptDepart,Vecteur sensDepart, Vecteur sensArrivee,ArrayList<Vecteur> listeArrivees, String name, ArrayList<Jalon> listeJalons) {
		this.matrice = matrice;
		this.ptDepart = ptDepart;
		this.sensDepart = sensDepart;
		this.sensArrivee = sensArrivee;
		this.listeArrivees = listeArrivees;
		this.name = name;
		this.listeJalons = listeJalons;
		//dijkstra = new Dijkstra(this);
		//System.out.println(this.listeArrivees.get(0).toString() + " TypeDep : " + this.getTerrain(this.listeArrivees.get(0)));
		this.curseur = vTools.addition(this.listeArrivees.get(0),new Vecteur(-1,0));
		makeJalons();
	}
	
//	public void majDijkstra() {
//		dijkstra = new Dijkstra(this);
//	}
	
	private void next(Vecteur curseur){
		if(vTools.calculOrientation(this,curseur) == OrientationJalon.DROITE || vTools.calculOrientation(this,curseur) == OrientationJalon.BASDROITE){
			curseur.autoAdd(new Vecteur(-1,0));
			if(vTools.calculOrientation(this,curseur) == OrientationJalon.NULL){
				curseur.autoAdd(new Vecteur(0,1));
			}
		}
		else {
			if(vTools.calculOrientation(this,curseur) == OrientationJalon.GAUCHE || vTools.calculOrientation(this,curseur) == OrientationJalon.HAUTGAUCHE){
				curseur.autoAdd(new Vecteur(1,0));
				if(vTools.calculOrientation(this,curseur) == OrientationJalon.NULL){
					curseur.autoAdd(new Vecteur(0,-1));
				}
				
			}
			else {
				if(vTools.calculOrientation(this,curseur) == OrientationJalon.HAUT || vTools.calculOrientation(this,curseur) == OrientationJalon.HAUTDROITE){
					curseur.autoAdd(new Vecteur(0,-1));
					if(vTools.calculOrientation(this,curseur) == OrientationJalon.NULL){
						curseur.autoAdd(new Vecteur(-1,0));
					}
					
				}
				else {
					curseur.autoAdd(new Vecteur(0,1));
					if(vTools.calculOrientation(this,curseur) == OrientationJalon.NULL){
						curseur.autoAdd(new Vecteur(1,0));
					}
				}
			}
		}

	}
	
	private void makeJalons(){
		//System.out.println(curseur.toString() + " Type : " + this.getTerrain(curseur));
		//System.out.println(vTools.calculOrientation(this,curseur));
		Vecteur origine = curseur.cloneAsVecteur();
		next(curseur);
		//System.out.println(curseur.toString() + " Type : " + this.getTerrain(curseur));
		//int cpt = 0;
		while(!curseur.equals(origine)){
			//System.out.println(curseur.toString() + " Type : " + this.getTerrain(curseur));
			//cpt++;
			this.listeJalons.add(new Jalon(this,curseur));
			next(curseur);
		}
	}
	//getter et setters :
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
	
	@Override
	public Terrain[][] getChampsDeVision(Voiture v,HashMap<Equipe,Voiture> listeVoitures){
		return v.getPilote().getChampsDeVision(listeVoitures);
		
	}
	
	/**
	 * Rtourne vrai si une voiture contenue dans listeVoitures se trouve à la position v
	 */
	@Override
	public boolean isCar(Vecteur v, HashMap<Equipe,Voiture> listeVoitures){
		for(Entry<Equipe, Voiture> entry : listeVoitures.entrySet()) {
			Voiture voiture = entry.getValue();
			for(int i = 0;i<voiture.getPositionGraphique().length;i++){
				for(int j = 0;j<voiture.getPositionGraphique()[0].length;j++){
					if(voiture.getPositionGraphique()[i][j].equalsArrondi(v)){
							return true;
					}
				}
			}
		}
		return false;	
	}
}