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
	
	//affichage(temporaire)
	BufferedImage im;
	JLabel jl;
	
	
	public Pilote(Voiture v, Circuit c,BufferedImage im, JLabel jl){
		this.circuit = c;
		this.voiture = v;
		this.im = im;
		this.jl = jl;
		this.champs = new champsDeVision(v,c,im,jl);
		
	}
}
