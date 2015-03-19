package fr.needforcode.equipe;

import fr.needforcode.circuit.Terrain;
import fr.needforcode.course.Course;
import fr.needforcode.course.ParticipationEquipeException;
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

	/**
	 * Retourne le champs de vision de la voiture associ�e � l'�quipe.
	 * @return Matrice de type Terrain[][] contenant le champs de vision de la voiture.
	 */
	protected Terrain[][] getChampsDeVision() {
		Terrain[][] cdv = null;
		try {
			cdv = this.course.getChampsDeVision(this);
		} catch (ParticipationEquipeException e) {
			e.printStackTrace();
		}
		
		return cdv;
	}
	
	/**
	 * Methode permettant de d�terminer l'angle entre la voiture et un pixel du champ de vision actuel de la voiture.
	 * @param int x Coordonn�es x du pixel dans la matrice champs de visions
	 * @param int y Coordonn�es y du pixel dans la matrice du champs de vision
	 * @return Angle en radian vers la position du pixel choisis
	 */
	public double goToPixel(Terrain[][] cdv, int x, int y){
		//Terrain[][] cdv = this.getChampsDeVision();
		double yMax = cdv[0].length;
		if (y<yMax/2){
			//System.out.println(Math.atan(x/((yMax/2)-y)));
			return -(Math.toRadians(90)-Math.atan(x/((yMax/2)-y)));
		}else if(y>yMax/2){
			//System.out.println(Math.atan(x/(y-(yMax/2))));
			return (Math.toRadians(90)-Math.atan(x/(y-(yMax/2))));
			
		}else{
			return 0.0;
		}
	}
	
	//TODO: getVitesse() pour le joueur
	//TODO: getDirection() pour le joueur
	//TODO: getPosition() pour le joueur
	//TODO: getOrigineChampsDeVision() pour le joueur
	
	/**
	 * Getter de la variable nom.
	 * @return Nom de l'�quipe
	 */
	public String getNom() {
		return this.nom;
	}
}
