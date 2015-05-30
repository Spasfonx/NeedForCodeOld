package fr.needforcode.pilote;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;


/**
 * Classe ChampDeVision.
 * Représente la zone visible par le Pilote.
 * @author camille
 */
public class ChampDeVision {
	/**
	 * Matrice de terrain comprenant le Terrain visible dans le champ de vision.
	 */
	private Terrain[][] matrice;
	
	/**
	 * Position absolue de l'origine (0;0) de la matrice.
	 */
	private Vecteur origine;
	
	/**
	 * Abscisse, ordonnée, profondeur minimum et largeur minimum du champ de vision.
	 */
	int x,y,yMin,xMin;
	
	/**
	 * Circuit sur lequel le champ de vision se trouve.
	 */
	private Circuit circuit;
	
	/**
	 * Voiture pour laquelle on a un champ de vision.
	 */
	private Voiture voiture;


	
	/**
	 * Constructeur paramétré
	 * @param v
	 * @param c
	 */
	public ChampDeVision(Voiture v, Circuit c){
		 
		this.circuit = c;
		this.voiture = v;
		this.x = (int) this.voiture.getVitesse() * 300;
		this.y = (int) this.voiture.getVitesse() * 300;
		this.matrice = new Terrain[x][y]; 
		this.yMin = 200;
		this.xMin = 100;
		
	}
	
	/**
	 * rafraichi le champs de vision en prenant.
	 * ne prend pas en compte les autres voiture (à developper)
	 * @param listeVoitures 
	 */
	public void  refreshChampDeVision(){
		//profondeur du champs de vision, dépend de la vitesse
		if((int) ((1 - this.voiture.getVitesse()) * 300)< this.xMin)
			this.x = xMin;
		else
			this.x = (int) ((1 - this.voiture.getVitesse()) * 300);
		
		//largeur du cdv, + ou - constant (=largeur du circuit, bords du circuit toujours apparants)
		if((int) ((1 - this.voiture.getVitesse()) * 300)< this.yMin)
			this.y = yMin;
		else
			this.y = (int) ((1 - this.voiture.getVitesse()) * 300);
		
		//champs de vision de taille x par y
		this.matrice = new Terrain[x][y];  
		
		//Vecteur base du champs de vision, c.a.d perpendiculaire à la direction de la voiture et d'unité 1
		Vecteur base = vTools.directionOrthogonaleNormale(this.voiture.getDirection());

		//Vecteur origine (point (0,0) du cdv)
		this.origine = vTools.addition(this.voiture.getPosition(),vTools.prodDouble(base, (int)(-(y+1) / 2)));
		
		//Vecteur curseur qui represente un point du cdv, qui se déplacera afin de remplir notre cdv
		Vecteur curseur = this.origine.cloneAsVecteur();
	
		//Vecteur normal (unité 1) de la direction de la voiture, utilisé pour déplacer le curseur
		Vecteur normDirection = vTools.normalisation(this.voiture.getDirection());

		//Parcours du terrain devant notre voiture et remplissage du cdv
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				try{
					matrice[i][j] = this.circuit.getTerrain(curseur);
				}
				catch(java.lang.ArrayIndexOutOfBoundsException ex){
					//si le curseur est en dehors du circuit, on indique un Terrain.Out dans le cdv
					matrice[i][j] = Terrain.Out;
				}
				//on avance le curseur d'une position(colonne) sur la meme ligne
				curseur.autoAdd(base);
			}
			//on remet le curseur à l'origine (premiere colonne)
			curseur = this.origine.cloneAsVecteur();
			//on place le curseur sur la ligne suivante (au dessus)
			curseur.autoAdd(vTools.prodDouble(normDirection,i+1));
			//on recommence
		}
	}
	
	public Vecteur getOrigine(){
		return this.origine;
	}
	
	public Terrain[][] getMatrice(){
		return this.matrice;
	}
	
	
}
