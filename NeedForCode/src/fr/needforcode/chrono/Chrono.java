package fr.needforcode.chrono;

/**
 * Prototype d'une classe Chronomètre.
 * @author camille
 * @version 1.0
 */
public class Chrono {
	// Fonctions pour le chronometre
	long time;
	
	public Chrono(){
		this.time = 0;
	}

	// Lancement du chrono
	public void Go_Chrono() {
		time = java.lang.System.currentTimeMillis() ;
	}

	// Arret du chrono
	public void Stop_Chrono() {
		long time2 = java.lang.System.currentTimeMillis() ;
		long temps = time2 - time ;
		System.out.println("Temps ecoule = " + temps + " ms") ;
	} 
	
	public long getChrono(){
		
		return this.time;
	}
}
