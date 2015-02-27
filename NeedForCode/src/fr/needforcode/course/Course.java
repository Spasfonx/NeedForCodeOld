package fr.needforcode.course;

import java.util.HashMap;
import java.util.Map.Entry;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureException;
import fr.needforcode.voiture.factory.MiageCarFactory;
import fr.needforcode.voiture.factory.VoitureFactory;


/**
 * Course, �l�ment principal du d�roulement du jeu, carrefour des classes Circuit, Voiture et Equipe.
 * @author Christophe
 */
public class Course {
	
	private HashMap<Equipe, Integer> listeEquipes;
	private HashMap<Equipe, Voiture> listeVoitures;
	private Circuit circuit;
	private EtatCourse etatCourse;
	private VoitureFactory factory;
	private int lapTotal;

	/**
	 * Constructeur Course.
	 * @param c Circuit sur lequel la course va se d�rouler
	 * @param lt Nombre de tours
	 */
	public Course(Circuit c, int lt) {
		this.circuit 	   = c;
		this.lapTotal 	   = lt;
		this.etatCourse    = EtatCourse.PREPARE;
		this.listeEquipes  = new HashMap<Equipe, Integer>();
		this.listeVoitures = new HashMap<Equipe, Voiture>();
		this.factory 	   = new MiageCarFactory(this.circuit);
	}
	
	/**
	 * M�thode qui pilote toutes les voitures � chaque frame.
	 * @throws VoitureException Si lev�e par une voiture
	 */
	public void avancer() throws VoitureException {
		for(Entry<Equipe, Voiture> entry : listeVoitures.entrySet()) {
			entry.getValue().piloter(
					entry.getKey().run()
				);
		}
	}
	
	public void addEquipe(Equipe e) throws CourseRunningException {
		if (this.etatCourse != EtatCourse.PREPARE)
			throw new CourseRunningException("Impossible de rajouter une �quipe lorsque la course est en cours de d�roulement");
		
		this.listeEquipes.put(e, 0);
		this.listeVoitures.put(e, factory.build());
		
		/* On affecte l'�quipe � cette course */
		e.setCourse(this);
	}
	
	public void removeEquipe(Equipe e) throws CourseRunningException {
		if (this.etatCourse != EtatCourse.PREPARE)
			throw new CourseRunningException("Impossible de supprimer une �quipe lorsque la course est en cours de d�roulement");
		
		this.listeEquipes.remove(e);
		this.listeVoitures.remove(e);
		
		/* L'�quipe n'est plus dans cette course */
		e.setCourse(null);
	}
	
	public Voiture getVoiture(Equipe e) throws ParticipationEquipeException {
		if (!listeVoitures.containsKey(e))
			throw new ParticipationEquipeException("Cette �quipe ne participe pas � la course");
		
		return listeVoitures.get(e);
	}
	
	public Circuit getCircuit() {
		return circuit;
	}
	
}

