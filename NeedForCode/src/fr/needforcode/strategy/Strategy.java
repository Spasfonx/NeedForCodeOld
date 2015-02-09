package fr.needforcode.strategy;

import java.io.Serializable;
import fr.needforcode.circuit.Circuit;
import fr.needforcode.voiture.Commande;
import fr.needforcode.voiture.Voiture;

/**
 * Brouillon
 * @author s11745823
 *
 */
public interface Strategy extends Serializable{
	public Commande getCommande();
	public void init(Voiture v, Circuit c);
}