package fr.needforcode.ihm.model;

import java.io.File;
import java.io.IOException;
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
	 * Liste des circuits <Nom, Fichier .trk associ�>
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
	//TODO:G�n�rer les png avant d'ajouter au dictionaire de circuits
	public void loadCircuits() throws Exception {
		File dir = new File(this.pathCircuits);
		
		if (!dir.isDirectory())
			throw new PathException("Le chemin d'acc�s sp�cifi� ne fait pas r�f�rence � un dossier");
		
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
	 * Charge l'image associ�e au circuit.
	 * @param name Nom du circuit
	 * @return Chemin d'acc�s a l'image associ�e au circuit
	 * @throws AssociatedImageException Si le circuit n'a pas d'image associ�e
	 * @throws IOException Si il y a une erreur sur le chemin d'acc�s � l'image
	 */
	public String getImagePathFromName(String name) throws AssociatedImageException, IOException {
		if (!circuits.containsKey(name))
			throw new AssociatedImageException("Le circuit " + name + " n'a pas d'image associ�");
		
		// Cr�e le fichier pour r�cup�rer l'url absolue
		File img = new File(pathImages + name + "." + extensionImage);
		
		return img.getCanonicalPath();
	}
	
	/**
	 * Charge l'image associ�e au circuit.
	 * @param name Nom du circuit
	 * @return Chemin d'acc�s a l'image associ�e au circuit
	 * @throws CircuitNotFoundException Si le nom du circuit n'existe pas 
	 */
	public String getTrkPathFromName(String name) throws Exception {
		if (!circuits.containsKey(name))
			throw new CircuitNotFoundException();
		
		return circuits.get(name).getCanonicalPath();
	}
	
	/**
	 * Renvois la liste des circuits.
	 * @returns Liste des circuits dans un dictionnaire de la forme <Nom, Fichier associ�>
	 */
	public HashMap<String, File> getCircuits() {
		return this.circuits;
	}

}
