package fr.needforcode.equipe;

import fr.needforcode.course.Course;
import fr.needforcode.voiture.Commande;

/**
 * Classe abstraite Equipe. L'�quipe est l'objet o� les joueurs d�velopperont 
 * le comportement de leur voiture. 
 * @author Christophe
 * @version 1.0
 */
public abstract class Equipe {
	
	/**
	 * Nom de l'�quipe.
	 */
	private final String nom;
	
	/**
	 * Course sur laquelle l'�quipe concours.
	 */
	private Course course;
	
	/**
	 * Constructeur Equipe. Instancie une �quipe et l'associe directement 
	 * � une course.
	 * @param n Nom de l'�quipe.
	 * @param c Course associ�e. Modifiable par la suite.
	 */
	public Equipe(String n, Course c){
		this.nom 	= n;
		this.course = c;
	}
	
	/**
	 * Constructeur Equipe. Instancie une �quipe sans l'associer � une course.
	 * @param n Nom de l'�quipe.
	 */
	public Equipe(String n) {
		this(n, null);
	}
	
	/**
	 * Associe la course sur laquelle l'�quipe va concourrir.
	 * @param c Course concern�e.
	 */
	public void setCourse(Course c) {
		this.course = c;
	}
	
	/**
	 * M�thode indispensable au fonctionnement du framework. C'est ici que
	 * l'�quipe va d�finir le <strong>comportement</strong> de leur voiture. 
	 * Le controlleur principal �xecute cette commande � chaque frame. La m�thode 
	 * retourne une commande qui d�finis la direction et l'accel�ration de la voiture.
	 * @return Commande qui d�finis la direction et l'accel�ration de la voiture.
	 */
	public abstract Commande run();

}
