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
 * Implémentation d'un circuit.
 * @author Camille
 *
 */
public class CircuitImpl implements CircuitModifiable {
	/**
	 * matrice Terrain, contient le circuit logique.
	 */
	private Terrain[][] matrice;
	
	/**
	 * Position du point de départ.
	 */
	private Vecteur ptDepart;
	
	/**
	 * Sens de départ des voiture (0,1) par défaut.
	 */
	private Vecteur sensDepart;
	
	/**
	 * Sens d'arrivé (0,1) par défaut.
	 */
	private Vecteur sensArrivee;
	
	/**
	 * Liste de Vecteur correspondant à toutes les
	 * position des pixels EndLine.
	 */
	private ArrayList<Vecteur> listeArrivees;
	
	/**
	 * Liste de Jalon mis en place sur le circuit.
	 */
	private ArrayList<Jalon> listeJalons;
	
	/**
	 * Liste de voiture ainsi que leur postion, non utilisé.
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
	 * Curseur utilisé pour la création des Jalons.
	 */
	private Vecteur curseur;
	
	/**
	 * Constructeur paramétré utlisé par la factory
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
	 * Méthode next utilisé lors de la génération de Jalon.
	 * Cette methode déplace le curseur le long du bord intérieur du circuit en fonction des orientations
	 * de Jalons
	 * @param curseur se déplacant le lond du bord
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
	 * méthode créant tous les jalons à l'instanciation d'un Circuit.
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
	 * Rtourne vrai si une voiture contenue dans listeVoitures se trouve à la position v
	 * n'est pas utilisé
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
