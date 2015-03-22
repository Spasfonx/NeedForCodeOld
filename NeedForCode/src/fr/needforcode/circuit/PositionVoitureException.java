package fr.needforcode.circuit;

/**
 * Exception retourné en cas de conflit de position de voiture
 * @author camille
 *
 */
public class PositionVoitureException extends Exception {

	public PositionVoitureException(String message) {
		super(message);
	}

}
