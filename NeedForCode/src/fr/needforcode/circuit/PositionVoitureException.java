package fr.needforcode.circuit;

/**
 * Exception retourn� en cas de conflit de position de voiture
 * @author camille
 *
 */
public class PositionVoitureException extends Exception {

	private static final long serialVersionUID = 9132047301525411287L;

	public PositionVoitureException(String message) {
		super(message);
	}

}
