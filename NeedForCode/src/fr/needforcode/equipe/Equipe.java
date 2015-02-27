package fr.needforcode.equipe;

import fr.needforcode.course.Course;
import fr.needforcode.voiture.Commande;
import fr.needforcode.voiture.Voiture;


/**
 * Classe abstraite Equipe.
 * @author Christophe
 */
public abstract class Equipe {
	
	private String nom;
	private Course course;
	
	public Equipe(String n, Course c){
		this.nom 	= n;
		this.course = c;
	}
	
	public Equipe(String n) {
		this(n, null);
	}
	
	public void setCourse(Course c) {
		this.course = c;
	}
	
	public abstract Commande run();

}
