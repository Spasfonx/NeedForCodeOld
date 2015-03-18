package fr.needforcode.pilote;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JLabel;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;

public class Pilote {
	private Circuit circuit;
	private Voiture voiture;
	private	ChampsDeVision champs;
	
	
	public Pilote(Voiture v, Circuit c){
		this.circuit = c;
		this.voiture = v;
		this.champs = new ChampsDeVision(v,c);		
	}
	
	public Terrain[][] getChampsDeVision(HashMap<Equipe,Voiture> listeVoitures){
		return this.champs.getChampsDeVision(listeVoitures);
	}
}
