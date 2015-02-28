package fr.needforcode.pilote;

import java.util.HashMap;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;


/**
 * 
 * @author camille
 * classe champs de vision
 */
public class champsDeVision {
	public Terrain[][] champsDeVision;
	int x,y,yMin;
	private Circuit circuit;
	private Voiture voiture;
	//private Vecteur directionCache;
	//private Vecteur positionCache;

	
	/**
	 * Constructeur paramétré
	 * @param v
	 * @param c
	 */
	public champsDeVision(Voiture v, Circuit c){
		 
		this.circuit = c;
		this.voiture = v;
		this.x = (int) this.voiture.getVitesse() * 300;
		this.y = (int) this.voiture.getVitesse() * 300;
		this.champsDeVision = new Terrain[x][y]; 
		//this.directionCache = v.getDirection();
		//this.positionCache = v.getPosition();
		this.yMin = 200;
		
	}
	
	/**
	 * 
	 * @param listeVoitures rafraichi le champs de vision en prenant en compte les autres voitures
	 */
	public void  refreshChampsDeVision(HashMap<Equipe,Voiture> listeVoitures){
		//profondeur du champs de vision, dépend de la vitesse
		this.x = (int) ((1 - this.voiture.getVitesse()) * 300);
		
		//largeur du cdv, + ou - constant (=largeur du circuit, bords du circuit toujours apparants)
		if((int) ((1 - this.voiture.getVitesse()) * 300)< this.yMin)
			this.y = yMin;
		else
			this.y = (int) ((1 - this.voiture.getVitesse()) * 300);
		
		//champs de vision de taille x par y
		this.champsDeVision = new Terrain[x][y];  
		
		//Vecteur base du champs de vision, c.a.d perpendiculaire à la direction de la voiture et d'unité 1
		Vecteur base = vTools.directionOrthogonaleNormale(this.voiture.getDirection());
		
		//Vecteur origine (point (0,0) du cdv)
		Vecteur origine = vTools.addition(this.voiture.getPosition(),vTools.prodDouble(base, (int)(-(y+1) / 2)));
		
		//Vecteur curseur qui represente un point du cdv, qui se déplacera afin de remplir notre cdv
		Vecteur curseur = origine.cloneAsVecteur();
		
		//Vecteur normal (unité 1) de la direction de la voiture, utilisé pour déplacer le curseur
		Vecteur normDirection = vTools.normalisation(this.voiture.getDirection());
		
		//Parcours du terrain devant notre voiture et remplissage du cdv
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				try{
					//s'il y a une voiture à la position du curseur, on l'ajoute au cdv, sinon on ajoute le terrain
//					if(this.circuit.isCar(curseur, listeVoitures))
//						champsDeVision[i][j] = Terrain.Voiture;
//					else
						champsDeVision[i][j] = this.circuit.getTerrain(curseur);
				}
				catch(java.lang.ArrayIndexOutOfBoundsException ex){
					//si le curseur est en dehors du circuit, on indique un Terrain.Out dans le cdv
					champsDeVision[i][j] = Terrain.Out;
				}
				//on avance le curseur d'une position(colonne) sur la meme ligne
				curseur.autoAdd(base);
			}
			//on remet le curseur à l'origine (premiere colonne)
			curseur = origine.cloneAsVecteur();
			//on place le curseur sur la ligne suivante (au dessus)
			curseur.autoAdd(vTools.prodDouble(normDirection,i));
			//on recommence
		}
	}
	public Terrain[][] getChampsDeVision(HashMap<Equipe,Voiture> listeVoitures){
		//A vérifier, ce test n'est plus possible si on veut afficher les autre voiture dans le cdv
		//if(voiture.getDirection() != this.directionCache || voiture.getPosition() != this.positionCache)
			refreshChampsDeVision(listeVoitures);
		return this.champsDeVision;
	}
	
	
}
