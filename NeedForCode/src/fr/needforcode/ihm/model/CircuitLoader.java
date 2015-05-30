package fr.needforcode.ihm.model;

import java.io.File;
import java.io.IOException;
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
	 * Liste des circuits <Nom, Fichier .trk associé>
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
	 * L'extension des fichiers images.
	 */
	private String extensionImage;
	
	/**
	 * L'extension des fichiers circuits.
	 */
	private String extensionCircuit;
	
	/**
	 * Constructeur de CircuitLoader.
	 */
	public CircuitLoader() { // TODO: Params dans un XML
		extensionImage 	 = "png";
		extensionCircuit = "trk";
		pathCircuits 	 = "./trk/";
		pathImages 	 	 = "./png/";
		circuits 	 	 = new HashMap<String, File>();
	}
	
	/**
	 * Charge les circuits dans un HashMap.
	 * @throws Exception
	 */
	//TODO:Générer les png avant d'ajouter au dictionaire de circuits
	public void loadCircuits() throws Exception {
		File dir = new File(this.pathCircuits);
		
		if (!dir.isDirectory())
			throw new PathException("Le chemin d'accès spécifié ne fait pas référence à un dossier");
		
		for (File circuit : dir.listFiles()) {
			/* Retire l'extension du fichier */
			String[] infos = circuit.getName().split(Pattern.quote("."));
			
			String name = infos[0];
			String ext = infos[1];
			
			if (ext.equals(extensionCircuit))
				this.circuits.put(name, circuit);
		}
	}
	
	/**
	 * Charge l'image associée au circuit.
	 * @param name Nom du circuit
	 * @return Chemin d'accès a l'image associée au circuit
	 * @throws AssociatedImageException Si le circuit n'a pas d'image associée
	 * @throws IOException Si il y a une erreur sur le chemin d'accès à l'image
	 */
	public String getImagePathFromName(String name) throws AssociatedImageException, IOException {
		if (!circuits.containsKey(name))
			throw new AssociatedImageException("Le circuit " + name + " n'a pas d'image associé");
		
		// Crée le fichier pour récupérer l'url absolue
		File img = new File(pathImages + name + "." + extensionImage);
		
		return img.getCanonicalPath();
	}
	
	/**
	 * Charge l'image associée au circuit.
	 * @param name Nom du circuit
	 * @return Chemin d'accès a l'image associée au circuit
	 * @throws CircuitNotFoundException Si le nom du circuit n'existe pas 
	 */
	public String getTrkPathFromName(String name) throws Exception {
		if (!circuits.containsKey(name))
			throw new CircuitNotFoundException();
		
		return circuits.get(name).getCanonicalPath();
	}
	
	/**
	 * Renvois la liste des circuits.
	 * @returns Liste des circuits dans un dictionnaire de la forme <Nom, Fichier associé>
	 */
	public HashMap<String, File> getCircuits() {
		return this.circuits;
	}

}
