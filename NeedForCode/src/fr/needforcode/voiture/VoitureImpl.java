package fr.needforcode.voiture;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.pilote.Pilote;

/**
 * @author Omar Ben Bella
 * @version 1.0
 */

public class VoitureImpl implements Voiture {

	private Vecteur position;
	private Vecteur direction;
	private double vitesse;
	private boolean derapage;
	private double vmax;
	private double braquage;
	private double vitesse_sortie_derapage;
	private Vecteur direction_derapage;
	private double sens_derapage;
	private Pilote driver;
	
	
	/**
	 * Commande (dépend de c) : alpha_c * c
	 */
	private double alpha_c;
	
	/**
	 * Frottements (négative) : alpha_f mv + beta_f m
	 */
	private double alpha_f, beta_f;
	
	/**
	 * Inertie (positive) : alpha_derapage * mv
	 */
	private double alpha_derapage;
	
	/**
	 * Exemple d'utilisation : entre 20 et 30% de la vmax, 
	 * on peut tourner sans déraper de 80% de la capacité de braquage
	 */
	private final double[] tabVitesse = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1. };
	private final double[] tabTurn = { 1., 1., 0.8, 0.7, 0.6, 0.4, 0.3, 0.2, 0.1, 0.075 };


	/**
	  * Constructeur de la classe VoitureImpl avec l'ensemble des paramètres
	  */
	public VoitureImpl(double vmax, double braquage, double alpha_c, 
			double alpha_f, double beta_f, double alpha_derapage, 
			double vitesse, Vecteur position, Vecteur direction,
			double vitesse_sortie_derapage,Circuit track) {
		this.position = position;
		this.direction = direction;
		this.vitesse = vitesse;
		this.vmax = vmax;
		this.alpha_c = alpha_c;
		this.braquage = braquage;
		this.alpha_f = alpha_f;
		this.beta_f = beta_f;
		this.alpha_derapage = alpha_derapage;
		this.vitesse_sortie_derapage = vitesse_sortie_derapage;
		this.derapage = false;
		this.driver = new Pilote(this,track);

	}
	
	/**
	  * Constructeur de la classe VoitureImpl avec un modèle de voiture déjà instancié
	  */
	public VoitureImpl(VoitureImpl model) {
		this.position = model.position.cloneAsVecteur();
		this.direction = model.direction.cloneAsVecteur();
		this.vitesse = model.vitesse;
		this.vmax = model.vmax;
		this.alpha_c = model.alpha_c;
		this.braquage = model.braquage;
		this.alpha_f = model.alpha_f;
		this.beta_f = model.beta_f;
		this.alpha_derapage = model.alpha_derapage;
		this.vitesse_sortie_derapage = model.vitesse_sortie_derapage;
		derapage = model.derapage;

	}

	/**
	  * Méthode de pilotage détectant si le dérapage est actif et faisant appel à la méthode appropriée
	  * 
	  * @param a Commande c
	  */
	@Override
	public void piloter(Commande c) throws VoitureException {
		c.controlCommand();

		if (!derapage && detection_derapage(c)) {
			debut_derapage(c);
			System.out.println("debut_derapage");
		}
		if (derapage) {
			driveAvecDerapage(c);
		} else {
			driveSansDerapage(c);
		}
	}
	
	/**
	  * Méthode calculant la rotation maximale que la voiture peut prendre sans déraper en fonction de sa vitesse actuelle
	  * 
	  * @return un double du tableau tabTurn
	  */
	@Override
	public double getMaxTurnSansDerapage() {
		for (int i = 0; i < tabVitesse.length; i++) {
			if (vitesse < tabVitesse[i] * vmax) {
				return tabTurn[i];
			}
		}
		return tabTurn[tabTurn.length - 1];
	}

	@Override
	public double getVitesse() {
		return vitesse;
	}

	@Override
	public Vecteur getPosition() {
		return position;
	}

	@Override
	public Vecteur getDirection() {
		return direction;
	}

	@Override
	public boolean getDerapage() {
		return derapage;
	}

	public double getBraquage() {
		return braquage;
	}
	
	/**
	  * Méthode calculant la positon de la voiture selon les paramètres physiques liés au dérapage de la voiture
	  * 
	  * @param a Commande c
	  */
	private void driveAvecDerapage(Commande c) {
		// freinage quelque soit la commande
		vitesse -= alpha_derapage;
		vitesse = Math.max(0., vitesse);
		// maj de la direction
		vTools.rotation(direction, Math.signum(c.getTurn())
				* getMaxTurnSansDerapage() * braquage * 0.5);
		vTools.rotation(direction_derapage, sens_derapage
				* getMaxTurnSansDerapage() * braquage * 1.2);
		// avance un peu selon
		position.autoAdd(vTools.prodDouble(direction, vitesse));
		if (vitesse < vitesse_sortie_derapage) {
			fin_derapage();
			System.out.println("fin_derapage");
		}
	}
	
	/**
	  * Méthode calculant la positon de la voiture selon les paramètres physiques de frotement, inertie et commande
	  * 
	  * @param a Commande c
	  */
	private void driveSansDerapage(Commande c) {
		// approche normale
		// 1) gestion du volant
		vTools.rotation(direction, (c.getTurn() * braquage));
		
		// 2.1) gestion des frottements
		vitesse -= alpha_f;
		vitesse -= beta_f * vitesse;
		// 2.2) gestion de l'acceleration/freinage
		vitesse += c.getAcc() * alpha_c;
		// 2.3) garanties, bornes...
		vTools.normalisation(direction);
		vitesse = Math.max(0., vitesse); // pas de vitesse nÃ©gative
		vitesse = Math.min(vmax, vitesse);
		// 3) mise Ã  jour de la position
		position.autoAdd(vTools.prodDouble(direction, vitesse));
	}
	
	/**
	  * Méthode détectant si la commande passée en paramètre fait déraper la voiture
	  * 
	  * @param a Commande c
	  */
	private boolean detection_derapage(Commande c) {
		if (Double.compare(Math.abs(c.getTurn()), getMaxTurnSansDerapage()) > 0) {
			return true;
		}
		return false;
	}

	/**
	  * Méthode arrêtant le dérapage de la voiture
	  */
	private void fin_derapage() {
		derapage = false;
		direction = direction_derapage.cloneAsVecteur();
	}

	/**
	  * Méthode débutant le dérapage de la voiture
	  */
	private void debut_derapage(Commande c) {
		derapage = true;
		sens_derapage = Math.signum(c.getTurn());
		direction_derapage = direction.cloneAsVecteur();
	}

}