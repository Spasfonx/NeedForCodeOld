package fr.needforcode.equipe;

import fr.needforcode.voiture.Commande;
import fr.needforcode.voiture.Voiture;


/**
 * Classe abstraite Equipe.
 * @author Christophe
 */
public abstract class Equipe {
	
	private String nom;
	
	public Equipe(String n){
		this.nom 	 = n;
	}
	
	public abstract Commande run();

}
