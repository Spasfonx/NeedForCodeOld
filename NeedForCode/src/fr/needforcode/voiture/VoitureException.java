package fr.needforcode.voiture;

public class VoitureException extends Exception {

	private static final long serialVersionUID = 5593187398181439818L;

	public VoitureException() {
		super("Commande non valide");
	}

}