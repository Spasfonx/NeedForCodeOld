package fr.needforcode.ihm.model;

import java.io.File;
import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Charge la liste des circuits depuis le repertoire dédié
 * 
 * @author Christophe
 * @version 1.0
 */
public class CircuitLoader {
	
	/**
	 * Liste des circuits <Nom, Fichier associé>
	 */
	private HashMap<String, File> circuits;
	
	/**
	 * Le chemin d'accès aux fichiers trk.
	 */
	private String pathCircuits;
	
	/**
	 * Le chemin d'accès aux fichiers images.
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
		
		if (!dir.isDirectory()) // TODO: Créer Exception spécialisée
			throw new Exception("Le chemin d'accès spécifié ne fait pas référence à un dossier");
		
		for(File circuit : dir.listFiles()) {
			// Retire l'extension du fichier
			String name = circuit.getName().split(Pattern.quote("."))[0];
			this.circuits.put(name, circuit);
		}
	}
	
	/**
	 * Charge l'image associée au circuit.
	 * 
	 * @param name Nom du circuit
	 * @return Chemin d'accès a l'image associée au circuit
	 * 
	 * @throws Exception 
	 */
	public String getImagePathFromName(String name) throws Exception {
		if (!circuits.containsKey(name)) // TODO: Créer Exception spécialisée
			throw new Exception("Le circuit " + name + " n'a pas d'image associé");
		
		// Crée le fichier pour récupérer l'url absolue
		File img = new File(pathImages + name + "." + ext);
		
		return img.getCanonicalPath();
	}
	
	/**
	 * Charge l'image associée au circuit.
	 * 
	 * @param name Nom du circuit
	 * @return Chemin d'accès a l'image associée au circuit
	 * 
	 * @throws Exception 
	 */
	public String getTrkPathFromName(String name) throws Exception {
		if (!circuits.containsKey(name)) // TODO: Créer Exception spécialisée
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
