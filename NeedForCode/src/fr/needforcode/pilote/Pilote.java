package fr.needforcode.pilote;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.voiture.Voiture;

/**
 * Classe Pilote. Non terminée, à revoir.
 * @author camille
 *
 */
public class Pilote {
	/**
	 * Circuit sur lequel le Pilote se trouve.
	 */
	private Circuit circuit;
	
	/**
	 * Voiture que le Pilote conduit.
	 */
	private Voiture voiture;
	
	/**
	 * Champ de vision du pilote.
	 */
	private	ChampDeVision champs;
	
	/**
	 * Constructeur paramétré.
	 * @param v Voiture
	 * @param c Circuit
	 */
	public Pilote(Voiture v, Circuit c){
		this.circuit = c;
		this.voiture = v;
		this.champs = new ChampDeVision(v,c);		
	}
	
	/**
	 * Retourne le champ de vision du Pilote.
	 * @param listeVoitures Utile à la détection des autres voitures
	 * @return
	 */
	public ChampDeVision getChampDeVision(){
		this.champs.refreshChampDeVision();
		return this.champs;
	}
}
