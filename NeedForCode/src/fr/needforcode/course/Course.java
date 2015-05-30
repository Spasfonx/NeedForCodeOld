package fr.needforcode.course;

import java.util.HashMap;
import java.util.Map.Entry;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Jalon;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.pilote.ChampDeVision;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureException;
import fr.needforcode.voiture.factory.MiageCarFactory;
import fr.needforcode.voiture.factory.VoitureFactory;


/**
 * Course, �l�ment principal du d�roulement du jeu, carrefour des classes Circuit, Voiture et Equipe.
 * NOTE : Non termin�e
 * @author Christophe
 */
public class Course {
	
	/**
	 * <Equipe, Tour auquel elle se trouve>
	 */
	private HashMap<Equipe, Integer> listeEquipes;
	
	/**
	 * <Equipe, Voiture associ�e>
	 */
	private HashMap<Equipe, Voiture> listeVoitures;
	
	/**
	 * Circuit sur lequel se d�roule la course.
	 */
	private Circuit circuit;
	
	/**
	 * Etat de la course.
	 */
	private EtatCourse etatCourse;
	
	/**
	 * Factory des voitures.
	 */
	private VoitureFactory factory;
	
	/**
	 * Nombre de tour total.
	 */
	private int lapTotal;

	/**
	 * Constructeur Course.
	 * @param c - Circuit sur lequel la course va se d�rouler
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
	 * M�thode qui pilote toutes les voitures.
	 * @throws VoitureException Si lev�e par une voiture
	 */
	public void avancer() throws VoitureException {
		for(Entry<Equipe, Voiture> entry : listeVoitures.entrySet()) {
			if(!carIsOut(entry.getValue())){
				//TODO: Eliminer la voiture(l'�quipe)
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
	
	/**
	 * V�rifie si la voiture pass�e en param�tre a d�passer la ligne d'arriv�e.
	 * @param v - Voiture concern�e
	 * @return Bool�en, vrai si la voiture a d�pass� la ligne d'arriv�e.
	 */
	public boolean checkEndLine(Voiture v){
		Terrain t = circuit.getTerrain(
				(int) v.getPosition().getX(), 
				(int) v.getPosition().getY()
			);
		
		if (t == Terrain.EndLine 
				&& vTools.scalaire(circuit.getDirectionArrivee(), v.getDirection()) > 0 && allJalonOk(v)) {
			return true;
		}
		return false;
	}
	
	/**
	 * V�rifie si la voiture est pass�e par tous les jalons.
	 * NOTE : M�thode non termin�e.
	 * @param v - Voiture concern�e
	 * @return Bool�en, vrai si la voiture est pass�e par tous les jalons
	 */
	@Deprecated
	public boolean allJalonOk(Voiture v){
		boolean ret = false;
		for(Jalon j : circuit.getListeJalons()){
			
		}
		return true;
	}
	
	/**
	 * V�rifie si la voiture pass�e en param�tre se trouve sur un terrain
	 * pratiquable (donc n'est pas hors circuit).
	 * @param v - Voiture concern�e
	 * @return Bool�en, vrai si la voiture est hors circuit
	 */
	public boolean carIsOut(Voiture v){
		Terrain t = circuit.getTerrain(
				(int) v.getPosition().getX(), 
				(int) v.getPosition().getY()
			);
		
		if (!TerrainTools.isRunnable(t))
			return false;
		else
			return true;
	}
	
	/**
	 * Ajoute une �quipe � la course et lui affecte une voiture.
	 * @param e - Equipe � ajouter
	 * @throws CourseRunningException - Si on essaye d'ajouter une �quipe � une course d�j� lanc�e.  
	 */
	public void addEquipe(Equipe e) throws CourseRunningException {
		if (this.etatCourse != EtatCourse.PREPARE)
			throw new CourseRunningException("Impossible de rajouter une �quipe lorsque la course est en cours de d�roulement");
		
		this.listeEquipes.put(e, 0);
		this.listeVoitures.put(e, factory.build());
		//this.incrementStartPosition += 10; //incr�mente la StartPosition de la prochaine voiture ajout�
		/* On affecte l'�quipe � cette course */
		e.setCourse(this);
	}
	
	/**
	 * Retire une �quipe de la course.
	 * @param e - Equipe � retirer
	 * @throws CourseRunningException Si on essaye de retirer l'�quipe d'une course d�j� lanc�e.
	 */
	public void removeEquipe(Equipe e) throws CourseRunningException {
		if (this.etatCourse != EtatCourse.PREPARE)
			throw new CourseRunningException("Impossible de supprimer une �quipe lorsque la course est en cours de d�roulement");
		//this.incrementStartPosition -= 10;
		this.listeEquipes.remove(e);
		this.listeVoitures.remove(e);
		
		/* L'�quipe n'est plus dans cette course */
		e.setCourse(null);
	}
	
	/**
	 * Modifie l'�tat de la course.
	 * @param e - Etat de la course.
	 */
	public void setEtatCourse(EtatCourse e) {
		this.etatCourse = e;
	}
	
	/**
	 * Retourne la voiture associ�e � l'�quipe donn�e en param�tre.
	 * @param e - L'�quipe concern�e
	 * @return La voiture associ�e � l'�quipe donn�e en param�tre 
	 * @throws ParticipationEquipeException Si l'�quipe ne participe pas � cette course
	 */
	public Voiture getVoiture(Equipe e) throws ParticipationEquipeException {
		if (!listeVoitures.containsKey(e))
			throw new ParticipationEquipeException("Cette �quipe ne participe pas � la course");
		
		return listeVoitures.get(e);
	}
	
	/**
	 * Retourne le champs de vision de la voiture de l'�quipe pass�e en param�tres.
	 * @param e - L'�quipe concern�e
	 * @return Champs de vision de la voiture
	 * @throws ParticipationEquipeException Si l'�quipe ne participe pas � la course
	 */
	public ChampDeVision getChampDeVision(Equipe e) throws ParticipationEquipeException {
		if (!listeVoitures.containsKey(e))
			throw new ParticipationEquipeException("Cette �quipe ne participe pas � la course");
		
		return this.circuit.getChampDeVision(this.listeVoitures.get(e));	
	}
	
	
	
	/**
	 * Retourne le circuit sur laquelle la course se d�roule.
	 * @return Circuit sur laquelle la course se d�roule
	 */
	public Circuit getCircuit() {
		return circuit;
	}

	/**
	 * Retourne l'�tat dans lequel se trouve la course.
	 * @return Etat de la course
	 */
	public EtatCourse getEtat() {
		return this.etatCourse;
	}

	/**
	 * Retourne la liste des Equipe avec leurs voitures associ�es.
	 * @return Dictionnaire compos� des �quipes avec leurs voitures
	 */
	public HashMap<Equipe, Voiture> getListeVoitures() {
		return this.listeVoitures;
	}
	
}

