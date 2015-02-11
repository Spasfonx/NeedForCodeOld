package fr.needforcode.ihm.model;

import java.io.File;
import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Charge la liste des circuits depuis le repertoire d�di�
 * 
 * @author Christophe
 * @version 1.0
 */
public class CircuitLoader {
	
	/**
	 * Liste des circuits <Nom, Fichier associ�>
	 */
	private HashMap<String, File> circuits;
	
	/**
	 * Le chemin d'acc�s aux fichiers trk.
	 */
	private String pathCircuits;
	
	/**
	 * Le chemin d'acc�s aux fichiers images.
	 */
	private String pathImages;
	
	/**
	 * L'extension des fichiers images
	 */
	private String ext;
	
	public CircuitLoader() {
		ext 		 = "png";
		pathCircuits = "./trk/";
		pathImages 	 = "./png/";
		circuits 	 = new HashMap<String, File>();
	}
	
	/**
	 * Charge les circuits dans un HashMap.
	 * 
	 * @return
	 * @throws Exception
	 */
	public void loadCircuits() throws Exception {
		File dir = new File(this.pathCircuits);
		
		if (!dir.isDirectory()) // TODO: Cr�er Exception sp�cialis�e
			throw new Exception("Le chemin d'acc�s sp�cifi� ne fait pas r�f�rence � un dossier");
		
		for(File circuit : dir.listFiles()) {
			// Retire l'extension du fichier
			String name = circuit.getName().split(Pattern.quote("."))[0];
			this.circuits.put(name, circuit);
		}
	}
	
	/**
	 * Charge l'image associ�e au circuit.
	 * 
	 * @param name Nom du circuit
	 * @return Chemin d'acc�s a l'image associ�e au circuit
	 * 
	 * @throws Exception 
	 */
	public String getImagePathFromName(String name) throws Exception {
		if (!circuits.containsKey(name)) // TODO: Cr�er Exception sp�cialis�e
			throw new Exception("Le circuit " + name + " n'a pas d'image associ�");
		
		// Cr�e le fichier pour r�cup�rer l'url absolue
		File img = new File(pathImages + name + "." + ext);
		
		return img.getCanonicalPath();
	}
	
	/**
	 * Charge l'image associ�e au circuit.
	 * 
	 * @param name Nom du circuit
	 * @return Chemin d'acc�s a l'image associ�e au circuit
	 * 
	 * @throws Exception 
	 */
	public String getTrkPathFromName(String name) throws Exception {
		if (!circuits.containsKey(name)) // TODO: Cr�er Exception sp�cialis�e
			throw new Exception("Le circuit n'existe pas");
		
		return circuits.get(name).getCanonicalPath();
	}
	
	/**
	 * Get circuit
	 * 
	 * @return Circuits
	 */
	public HashMap<String, File> getCircuits() {
		return this.circuits;
	}

}
