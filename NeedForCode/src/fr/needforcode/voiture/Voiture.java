package fr.needforcode.voiture;

import fr.needforcode.geometrie.*;
import fr.needforcode.pilote.Pilote;

/**
 * @author Omar Ben Bella
 * @version 1.0
 */

public interface Voiture {
	
	/**
	 * M�thodes de pilotage
	 */	
	public void piloter(Commande c) throws VoitureException;
	
	public double getMaxTurnSansDerapage();
	
	/**
	 * M�thodes d'observations
	 * 
	 */
	public double getVitesse();
	
	public Vecteur getPosition();
	
	public Vecteur getDirection();
	
	public double getBraquage();
	
	public boolean getDerapage();
	
	public Pilote getPilote();

}
