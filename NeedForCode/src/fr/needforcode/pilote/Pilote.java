package fr.needforcode.pilote;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JLabel;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;

/**
 * Classe Pilote
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
