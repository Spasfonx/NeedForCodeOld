package fr.needforcode.pilote;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;

public class champsDeVision {
	public Terrain[][] champsDeVision;
	int x,y,yMin;
	private Circuit circuit;
	private Voiture voiture;
	private Vecteur directionCache;
	private Vecteur positionCache;

	
	
	public champsDeVision(Voiture v, Circuit c){
		 
		this.circuit = c;
		this.voiture = v;
		this.x = (int) this.voiture.getVitesse() * 300;
		this.y = (int) this.voiture.getVitesse() * 300;
		this.champsDeVision = new Terrain[x][y]; 
		this.directionCache = v.getDirection();
		this.positionCache = v.getPosition();
		this.yMin = 200;
		
	}
	
	
	public void  refreshChampsDeVision(Color maCouleur){
		this.x = (int) ((1 - this.voiture.getVitesse()) * 300);
	
		if((int) ((1 - this.voiture.getVitesse()) * 300)< this.yMin)
			this.y = yMin;
		else
			this.y = (int) ((1 - this.voiture.getVitesse()) * 300);
		this.champsDeVision = new Terrain[x][y];  
		Vecteur base = vTools.directionOrthogonaleNormale(this.voiture.getDirection());
		Vecteur origine = vTools.addition(this.voiture.getPosition(),vTools.prodDouble(base, (int)(-(y+1) / 2)));
		Vecteur curseur = origine.cloneAsVecteur();
		Vecteur test = vTools.normalisation(this.voiture.getDirection());
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				try{
					champsDeVision[i][j] = this.circuit.getTerrain(curseur);
				}
				catch(java.lang.ArrayIndexOutOfBoundsException ex){
					champsDeVision[i][j] = Terrain.Out;
				}
				curseur.autoAdd(base);
			}
			curseur = origine.cloneAsVecteur();
			curseur.autoAdd(vTools.prodDouble(test,i));
		}
	}
	public Terrain[][] getChampsDeVision(){
		if(voiture.getDirection() != this.directionCache || voiture.getPosition() != this.positionCache)
			refreshChampsDeVision(Color.green);
		return this.champsDeVision;
	}
	
	
}
