package fr.needforcode.ihm.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.needforcode.equipe.*;

/**
 * 
 * @author Omar
 * @version 1.0
 */
public class EquipeLoader {
	/**
	 * Dossier contenenant les classes des �quipes.
	 */
	private File repositoryEquipe;

	/**
	 * Loader.
	 */
	private URLClassLoader loader;

	/**
	 * Liste de noms des �quipes charg�es.
	 */
	private ArrayList<String> listeDesEquipes = new ArrayList<String>();

	/**
	 * Chargement des param�tres de l'auto-load.
	 */
	public EquipeLoader() {
		this.repositoryEquipe = new File(".\\equipes");
		try {
			URL urlRepoCritere = this.repositoryEquipe.toURL();
			URL[] urls = { urlRepoCritere };
			this.loader = URLClassLoader.newInstance(urls);
		} catch (MalformedURLException e) {
			System.out.println("L'auto-load n'a pas �t� charg� correctement");
		}
	}

	/**
	 * Charge les classes Equipe dans le dossier associ�.
	 * 
	 * @return Liste des instances d'�quipes charg�es
	 * @throws NomIncorrectException
	 */
	public ArrayList<Equipe> loadEquipe() throws NomIncorrectException {
		ArrayList<Equipe> listeEquipe = new ArrayList<Equipe>();
		File[] listeFileEquipe = this.repositoryEquipe.listFiles();
		
		for (File currentFile : listeFileEquipe) {
			String className = currentFile.getName().substring(0,
					currentFile.getName().lastIndexOf("."));
			Pattern p = Pattern.compile("Equipe[^\\s]*");
			Matcher m = p.matcher(className);
			boolean b = m.matches();
			if (!b) {
				throw new NomIncorrectException();
			}
			String nomEquipe = className.substring(6);
			this.listeDesEquipes.add(nomEquipe);
			
			try {
				Class<?> newEquipe = this.loader.loadClass(className);
				Equipe sd = (Equipe) newEquipe.newInstance();
				listeEquipe.add(sd);

			} catch (ClassNotFoundException e) {
				System.out.println("La classe " + className
						+ " n'a pas pu �tre charg�e");
			} catch (IllegalAccessException e) {
				System.out.println("L'acc�s � la classe " + className
						+ " n'a pas �t� possible");
				System.out.println(e.getMessage());
			} catch (InstantiationException e) {
				System.out.println("La classe " + className
						+ " n'a pas pu �tre instanci�e");
			}
			
		}
		
		return listeEquipe;
	}

}
