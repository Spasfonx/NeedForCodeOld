package fr.needforcode.pilote;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;

public class Pilote {
	private Circuit circuit;
	private Voiture voiture;
	private	champsDeVision champs;
	
	
	public Pilote(Voiture v, Circuit c){
		this.circuit = c;
		this.voiture = v;
		this.champs = new champsDeVision(v,c);		
	}
}
