package fr.needforcode.equipe;

import fr.needforcode.circuit.Terrain;
import fr.needforcode.course.Course;
import fr.needforcode.course.ParticipationEquipeException;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.pilote.ChampDeVision;
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

	/**
	 * Retourne le champs de vision de la voiture associée à l'équipe.
	 * @return Le champs de vision de la voiture.
	 */
	protected ChampDeVision getChampsDeVision() {
		ChampDeVision cdv = null;
		try {
			cdv = this.course.getChampDeVision(this);
		} catch (ParticipationEquipeException e) {
			e.printStackTrace();
		}
		
		return cdv;
	}
	
	/**
	 * Retourne l'origine absolue de la matrice du champ de vision
	 * @return Vecteur position de l'origine de la matrice
	 */
	protected Vecteur getOrigineChampDeVision(){
		return this.getChampsDeVision().getOrigine();
	}
	
	/**
	 * Retourne la position de la voiture de l'équipe
	 * @return position de la voiture de l'équipe
	 * @throws ParticipationEquipeException
	 */
	protected Vecteur getPositionVoiture() throws ParticipationEquipeException{
		return this.course.getVoiture(this).getPosition();
	}
	
	/**
	 * Retourne la direction de la voiture de l'équipe
	 * @return la direction de la voiture de l'équipe
	 * @throws ParticipationEquipeException
	 */
	protected Vecteur getDirectionVoiture() throws ParticipationEquipeException{
		return this.course.getVoiture(this).getDirection();
	}
	
	/**
	 * Retourne la vitesse de la voiture de l'équipe
	 * @return vitesse de la voiture de l'équipe
	 * @throws ParticipationEquipeException
	 */
	protected double getVitesseVoiture() throws ParticipationEquipeException{
		return this.course.getVoiture(this).getVitesse();
	}
	
	/**
	 * Retourne vrai si la voiture de l'équipe dérape, faux sinon.
	 * @return dérapage de la voiture?
	 * @throws ParticipationEquipeException
	 */
	protected boolean isDerapageVoiture() throws ParticipationEquipeException{
		return this.course.getVoiture(this).getDerapage();
	}
	
	/**
	 * Retourne la hauteur du circuit
	 * @return hauteur du circuit
	 */
	protected int getHeightCircuit(){
		return this.course.getCircuit().getHeight();
	}
	
	/**
	 * Retourne la largeur du circuit
	 * @return largeur du circuit
	 */
	protected int getWidthCircuit(){
		return this.course.getCircuit().getWidth();
	}
	
	/**
	 * Methode permettant de déterminer l'angle entre la voiture et 
	 * un pixel du champ de vision actuel de la voiture.
	 * @param int x Coordonnées x du pixel dans la matrice champs de visions
	 * @param int y Coordonnées y du pixel dans la matrice du champs de vision
	 * @return Angle en radian vers la position du pixel choisis
	 */
	public double goToPixel(int x, int y){
		Terrain[][] cdv = this.getChampsDeVision().getMatrice();
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
	
	/**
	 * Getter de la variable nom.
	 * @return Nom de l'équipe
	 */
	public String getNom() {
		return this.nom;
	}
}
