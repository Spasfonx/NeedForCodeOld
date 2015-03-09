package fr.needforcode.ihm.model;

public class CircuitNotFoundException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 8184381938071571393L;

	public CircuitNotFoundException() {
		this("Le circuit spécifié n'existe pas");
	}

	public CircuitNotFoundException(String message) {
		super(message);
	}

}
