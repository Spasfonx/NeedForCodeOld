package fr.needforcode.course;

import java.util.HashMap;
import java.util.Map.Entry;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Jalon;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureException;
import fr.needforcode.voiture.factory.MiageCarFactory;
import fr.needforcode.voiture.factory.VoitureFactory;


/**
 * Course, élément principal du déroulement du jeu, carrefour des classes Circuit, Voiture et Equipe.
 * @author Christophe
 */
public class Course {
	
	private HashMap<Equipe, Integer> listeEquipes;
	private HashMap<Equipe, Voiture> listeVoitures;
	private Circuit circuit;
	private EtatCourse etatCourse;
	private VoitureFactory factory;
	private int lapTotal;
	//private int incrementStartPosition;

	/**
	 * Constructeur Course.
	 * @param c - Circuit sur lequel la course va se dérouler
	 * @param lt Nombre de tours
	 */
	public Course(Circuit c, int lt) {
		this.circuit 	   = c;
		this.lapTotal 	   = lt;
		this.etatCourse    = EtatCourse.PREPARE;
		this.listeEquipes  = new HashMap<Equipe, Integer>();
		this.listeVoitures = new HashMap<Equipe, Voiture>();
		this.factory 	   = new MiageCarFactory(this.circuit);
		//this.incrementStartPosition = 0;
	}
	
	/**
	 * Méthode qui pilote toutes les voitures.
	 * @throws VoitureException Si levée par une voiture
	 */
	public void avancer() throws VoitureException {
		for(Entry<Equipe, Voiture> entry : listeVoitures.entrySet()) {
			if(!checkPosition(entry.getValue())){
				//TODO: Eliminer la voiture(l'équipe)
			}
			if(checkEndLine(entry.getValue())){
				Integer tour = listeEquipes.get(entry.getKey());
				tour++;
				listeEquipes.put(entry.getKey(), tour);
				System.out.println(listeEquipes.get(entry.getKey()));

			}
			entry.getValue().piloter(entry.getKey().run());
		}
	}
	
	public boolean checkEndLine(Voiture v){
		Terrain t = circuit.getTerrain((int) v.getPosition().getX(), (int) v.getPosition().getY());
		if (t == Terrain.EndLine && vTools.scalaire(circuit.getDirectionArrivee(), v.getDirection()) > 0 && allJalonOk(v)) {
			return true;
		}
		return false;
	}
	
	public boolean allJalonOk(Voiture v){
		boolean ret = false;
		for(Jalon j : circuit.getListeJalons()){
			
		}
		return true;
	}
	
	public boolean checkPosition (Voiture v){
		Terrain t = circuit.getTerrain((int) v.getPosition().getX(), (int) v.getPosition().getY());
		if (!TerrainTools.isRunnable(t)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Ajoute une équipe à la course et lui affecte une voiture.
	 * @param e - Equipe à ajouter
	 * @throws CourseRunningException - Si on essaye d'ajouter une équipe à une course déjà lancée.  
	 */
	public void addEquipe(Equipe e) throws CourseRunningException {
		if (this.etatCourse != EtatCourse.PREPARE)
			throw new CourseRunningException("Impossible de rajouter une équipe lorsque la course est en cours de déroulement");
		
		this.listeEquipes.put(e, 0);
		this.listeVoitures.put(e, factory.build());
		//this.incrementStartPosition += 10; //incrémente la StartPosition de la prochaine voiture ajouté
		/* On affecte l'équipe à cette course */
		e.setCourse(this);
	}
	
	/**
	 * Retire une équipe de la course.
	 * @param e - Equipe à retirer
	 * @throws CourseRunningException Si on essaye de retirer l'équipe d'une course déjà lancée.
	 */
	public void removeEquipe(Equipe e) throws CourseRunningException {
		if (this.etatCourse != EtatCourse.PREPARE)
			throw new CourseRunningException("Impossible de supprimer une équipe lorsque la course est en cours de déroulement");
		//this.incrementStartPosition -= 10;
		this.listeEquipes.remove(e);
		this.listeVoitures.remove(e);
		
		/* L'équipe n'est plus dans cette course */
		e.setCourse(null);
	}
	
	/**
	 * Modifie l'état de la course.
	 * @param e - Etat de la course.
	 */
	public void setEtatCourse(EtatCourse e) {
		this.etatCourse = e;
	}
	
	/**
	 * Retourne la voiture associée à l'équipe donnée en paramètre.
	 * @param e - L'équipe concernée
	 * @return La voiture associée à l'équipe donnée en paramètre 
	 * @throws ParticipationEquipeException Si l'équipe ne participe pas à cette course
	 */
	public Voiture getVoiture(Equipe e) throws ParticipationEquipeException {
		if (!listeVoitures.containsKey(e))
			throw new ParticipationEquipeException("Cette équipe ne participe pas à la course");
		
		return listeVoitures.get(e);
	}
	
	/**
	 * Retourne le champs de vision de la voiture de l'équipe passée en paramètres.
	 * @param e - L'équipe concernée
	 * @return Matrice de type Terrain[][] représentant les pixels du champs de vision de la voiture
	 * @throws ParticipationEquipeException Si l'équipe ne participe pas à la course
	 */
	public Terrain[][] getChampsDeVision(Equipe e) throws ParticipationEquipeException {
		if (!listeVoitures.containsKey(e))
			throw new ParticipationEquipeException("Cette équipe ne participe pas à la course");
		
		return this.circuit.getChampsDeVision(this.listeVoitures.get(e), listeVoitures);
		
	}
	
	/**
	 * Retourne le circuit sur laquelle la course se déroule.
	 * @return Circuit sur laquelle la course se déroule
	 */
	public Circuit getCircuit() {
		return circuit;
	}

	/**
	 * Retourne l'état dans lequel se trouve la course.
	 * @return Etat de la course
	 */
	public EtatCourse getEtat() {
		return this.etatCourse;
	}

	/**
	 * Retourne la liste des Equipe avec leurs voitures associées.
	 * @return Dictionnaire composé des équipes avec leurs voitures
	 */
	public HashMap<Equipe, Voiture> getListeVoitures() {
		return this.listeVoitures;
	}
	
}

