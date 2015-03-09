package fr.needforcode.ihm.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.needforcode.equipe.*;



public class EquipeLoader 
{
	private File repositoryEquipe;
	private URLClassLoader loader;
	private ArrayList<String> listeDesEquipes = new ArrayList<String>();
	
	public EquipeLoader()
	{
		this.repositoryEquipe = new File(".\\equipes");
		try 
		{
			URL urlRepoCritere = this.repositoryEquipe.toURL();
			URL[] urls = {urlRepoCritere};
			this.loader = URLClassLoader.newInstance(urls);
		} 
		catch (MalformedURLException e) 
		{
			System.out.println("Le chargeur de classes a été mal chargé");
		}	
	}
	
	public ArrayList<Equipe> loadEquipe() throws NomIncorrectException
	{
		ArrayList<Equipe> listeEquipe = new ArrayList<Equipe>();
		File[] listeFileEquipe = this.repositoryEquipe.listFiles();
		for(File currentFile : listeFileEquipe)
		{
			String className = currentFile.getName().substring(0, currentFile.getName().lastIndexOf("."));
			Pattern p = Pattern.compile("Equipe[^\\s]*");
			Matcher m = p.matcher(className);
			boolean b = m.matches();
			if(!b){
				throw new NomIncorrectException();
			}
			String nomEquipe = className.substring(6);
			this.listeDesEquipes.add(nomEquipe);
			try
			{
				Class<?> newEquipe =  this.loader.loadClass(className);
				Equipe sd =  (Equipe) newEquipe.newInstance();
				listeEquipe.add(sd);
				
			}
			catch (ClassNotFoundException e) 
			{
				System.out.println("La classe "+className+" n'a pas pu être chargée");
			}
			catch(IllegalAccessException e)
			{
				System.out.println("L'accès à la classe "+className+ " n'a pas été possible");
				System.out.println(e.getMessage());
			}
			catch(InstantiationException e)
			{
				System.out.println("La classe "+className+" n'a pas pu être instanciée");
			}
		}
		return listeEquipe;
	}

	public static void main(String[] args) throws NomIncorrectException
	{
		EquipeLoader al = new EquipeLoader();
		ArrayList<Equipe> listeNewEquipe = al.loadEquipe();
		Equipe sc  = listeNewEquipe.get(0);
		for(String e : al.listeDesEquipes){
			System.out.println(e);
		}
		System.out.println(sc.run());
	}
}
