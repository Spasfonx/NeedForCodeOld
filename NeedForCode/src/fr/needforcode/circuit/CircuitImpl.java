package fr.needforcode.circuit;

import java.util.ArrayList;
import java.util.HashMap;



import java.util.Map.Entry;

import fr.needforcode.equipe.Equipe;
//import fr.needforcode.algo.Dijkstra;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.pilote.ChampDeVision;
import fr.needforcode.voiture.Voiture;

/**
 * Impl�mentation d'un circuit.
 * @author Camille
 *
 */
public class CircuitImpl implements CircuitModifiable {
	/**
	 * matrice Terrain, contient le circuit logique.
	 */
	private Terrain[][] matrice;
	
	/**
	 * Position du point de d�part.
	 */
	private Vecteur ptDepart;
	
	/**
	 * Sens de d�part des voiture (0,1) par d�faut.
	 */
	private Vecteur sensDepart;
	
	/**
	 * Sens d'arriv� (0,1) par d�faut.
	 */
	private Vecteur sensArrivee;
	
	/**
	 * Liste de Vecteur correspondant � toutes les
	 * position des pixels EndLine.
	 */
	private ArrayList<Vecteur> listeArrivees;
	
	/**
	 * Liste de Jalon mis en place sur le circuit.
	 */
	private ArrayList<Jalon> listeJalons;
	
	/**
	 * Liste de voiture ainsi que leur postion, non utilis�.
	 * 
	 */
	@Deprecated
	private HashMap<Voiture,Vecteur> listeVoitures;
	
	/**
	 * Largeur en pixel du circuit.
	 */
	private int largeurCircuit;

	/**
	 * Nom du circuit.
	 */
	private String name; 
	
	/**
	 * Curseur utilis� pour la cr�ation des Jalons.
	 */
	private Vecteur curseur;
	
	/**
	 * Constructeur param�tr� utlis� par la factory
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
		this.largeurCircuit = listeArrivees.size();
		this.curseur = vTools.addition(this.listeArrivees.get(0),new Vecteur(-1,1));
		makeJalons();
	}
	
	/**
	 * M�thode next utilis� lors de la g�n�ration de Jalon.
	 * Cette methode d�place le curseur le long du bord int�rieur du circuit en fonction des orientations
	 * de Jalons
	 * @param curseur se d�placant le lond du bord
	 */
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
	
	/**
	 * m�thode cr�ant tous les jalons � l'instanciation d'un Circuit.
	 */
	private void makeJalons(){
		int cpt = 0;
		int num = 0;
		Vecteur origine = curseur.cloneAsVecteur();
		next(curseur);
		while(!curseur.equalsArrondi(origine,1000)){
			Jalon j = new Jalon(this,curseur,num);
			if(!j.getListeVecteurs().isEmpty() && j.getListeVecteurs().size() < this.largeurCircuit * 1 && cpt % 5 == 0){
				if(num == 0){
					this.listeJalons.add(j);
					num++;
				}
				else{
					if(!vTools.croiserJalons(j,this.listeJalons)){
						this.listeJalons.add(j);
						num++;
					}
				}
			}
			cpt++;
			next(curseur);
		}	
	}

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
	
	/**
	 * Retourne le champs de vision de la voiture v.
	 */
	@Override
	public ChampDeVision getChampDeVision(Voiture v){
		return v.getPilote().getChampDeVision();
	}
	
	@Override
	public int getLargeurCircuit() {
		return largeurCircuit;
	}

	@Override
	public void setLargeurCircuit(int largeurCircuit) {
		this.largeurCircuit = largeurCircuit;
	}

	/**
	 * Rtourne vrai si une voiture contenue dans listeVoitures se trouve � la position v
	 * n'est pas utilis�
	 */
	@Deprecated
	public boolean isCar(Vecteur v, HashMap<Equipe,Voiture> listeVoitures){
		for(Entry<Equipe, Voiture> entry : listeVoitures.entrySet()) {
			Voiture voiture = entry.getValue();
			for(int i = 0;i<voiture.getPositionGraphique().length;i++){
				for(int j = 0;j<voiture.getPositionGraphique()[0].length;j++){
					if(voiture.getPositionGraphique()[i][j].equalsArrondi(v,1000)){
							return true;
					}
				}
			}
		}
		return false;	
	}
	
	@Override
	public ArrayList<Jalon> getListeJalons() {
		return listeJalons;
	}
}
