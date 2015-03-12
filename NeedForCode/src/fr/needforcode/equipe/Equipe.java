package fr.needforcode.equipe;

import fr.needforcode.circuit.Terrain;
import fr.needforcode.course.Course;
import fr.needforcode.course.ParticipationEquipeException;
import fr.needforcode.voiture.Commande;

/**
 * Classe abstraite Equipe. L'équipe est l'objet où les joueurs développeront 
 * le comportement de leur voiture. 
 * @author Christophe
 * @version 1.0
 */
public abstract class Equipe {
	
	/**
	 * Nom de l'équipe.
	 */
	private final String nom;
	
	/**
	 * Course sur laquelle l'équipe concours.
	 */
	private Course course;
	
	/**
	 * Constructeur Equipe. Instancie une équipe et l'associe directement 
	 * à une course.
	 * @param n Nom de l'équipe.
	 * @param c Course associée. Modifiable par la suite.
	 */
	public Equipe(String n, Course c){
		this.nom 	= n;
		this.course = c;
	}
	
	/**
	 * Constructeur Equipe. Instancie une équipe sans l'associer à une course.
	 * @param n Nom de l'équipe.
	 */
	public Equipe(String n) {
		this(n, null);
	}
	
	/**
	 * Associe la course sur laquelle l'équipe va concourrir.
	 * @param c Course concernée.
	 */
	public void setCourse(Course c) {
		this.course = c;
	}
	
	/**
	 * Méthode indispensable au fonctionnement du framework. C'est ici que
	 * l'équipe va définir le <strong>comportement</strong> de leur voiture. 
	 * Le controlleur principal éxecute cette commande à chaque frame. La méthode 
	 * retourne une commande qui définis la direction et l'accelération de la voiture.
	 * @return Commande qui définis la direction et l'accelération de la voiture.
	 */
	public abstract Commande run();

	protected Terrain[][] getChampsDeVision() {
		try {
			return this.course.getChampsDeVision(this);
		} catch (ParticipationEquipeException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Methode permettant de déterminer l'angle entre la voiture et un pixel du champ de vision actuel de la voiture
	 * @param int x
	 * @param int y
	 * @return double 
	 */
	
	public double goToPixel(Terrain[][] cdv, int x, int y){
		//Terrain[][] cdv = this.getChampsDeVision();
		double yMax = cdv[0].length;
		if (y<y/2){
			return Math.toRadians(90)-Math.atan(x/((yMax/2)-y));
		}else if(y>y/2){
			return -(Math.toRadians(90)-Math.atan(x/(y-(yMax/2))));
			
		}else{
			return 0.0;
		}
	}
	
	public String getNom() {
		return this.nom;
	}
}
