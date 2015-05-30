package fr.needforcode.course;

/**
 * Exception sur la participation d'une équipe à une course. 
 * @author Christophe
 */
public class ParticipationEquipeException extends Exception {

	private static final long serialVersionUID = -2742055326469428852L;

	public ParticipationEquipeException(String message) {
		super(message);
	}

}
