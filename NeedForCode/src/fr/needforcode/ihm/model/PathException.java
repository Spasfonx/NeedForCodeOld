package fr.needforcode.ihm.model;

public class PathException extends Exception {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -6397659099234262112L;

	/**
	 * Constructeur de PathException.
	 * @param message Le message de l'exception
	 */
	public PathException(String message) {
		super(message);
	}
	
	/**
	 * Constructeur de PathException.
	 */
	public PathException() {
		this("Le chemin spécifié n'est pas un chemin correct");
	}

}
