package fr.needforcode.circuit;

import java.util.ArrayList;
import java.util.HashMap;

import fr.needforcode.chrono.Chrono;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.Vecteur;

/**
 * Classe jalon, un jalon est un segment(ArrayList<Vecteur>) symbolisant un passage obligatoire pendant la course
 * @author camille
 *
 */
public class Jalon {
	
	/**
	 * Liste de Vecteurs composants le Jalon.
	 */
	private ArrayList<Vecteur> listeVecteurs;
	
	/**
	 * Orientation du Jalon. (cf orientationJalon pour la liste des orientations possibles)
	 */
	private OrientationJalon orientation;
	
	/**
	 * numéro du jalon.
	 */
	private int num;
	
	/**
	 * Enregistrement des passages de voiture
	 * où Integer est le numéro du tour
	 * et Equipe l'équipe passée.
	 * Fonctionnalité à developper.
	 */
	private HashMap<Integer,Equipe> passage;
	
	/**
	 * Variable de débug pour filtrer le numéro de Jalon souhaité.
	 */
	private final static int DEBUGNUM = 172;

	
	/**
	 * Constructeur à partir d'un segment existant
	 * @param lj ArrayList<Vecteur>
	 */
	@Deprecated
	public Jalon(ArrayList<Vecteur> lj){
		this.listeVecteurs = lj;
	}
	
	/**
	 * Constructeur utilisé par la construction d'un circuit(construit le Jalon)
	 * @param ci Circuit
	 * @param curseur vecteur (permet la construction des jalons)
	 */
	public Jalon(Circuit ci, Vecteur curseur,int num){
		this.listeVecteurs = new ArrayList<Vecteur>();
		this.num = num;
		setOrientation(ci,curseur);
		tracerJalon(ci,curseur);

	}
	
	/**
	 * Methode permettant de déterminer l'orientation du Jalon en fonction de la position du curseur
	 * @param ci
	 * @param curseur
	 */
	public void setOrientation(Circuit ci, Vecteur curseur){
		
		// on reçoit les 4 cases adjacentes au curseur
		Vecteur d, h, g, b;
		d = curseur.cloneAsVecteur();
		h = curseur.cloneAsVecteur();
		g = curseur.cloneAsVecteur();
		b = curseur.cloneAsVecteur();
		d.autoAdd(new Vecteur(0,1));
		h.autoAdd(new Vecteur(-1,0));
		g.autoAdd(new Vecteur(0,-1));
		b.autoAdd(new Vecteur(1,0));
		Terrain td,th,tg, tb;
		td = ci.getTerrain(d);
		th = ci.getTerrain(h);
		tg = ci.getTerrain(g);
		tb = ci.getTerrain(b);
		
		//ici on détermine l'orientation dans laquel on va tracer le Jalon
		if(td == Terrain.Herbe && th == Terrain.Herbe && tg == Terrain.Herbe && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge)){
			this.orientation = OrientationJalon.BAS;
		}
		else {
			if(td == Terrain.Herbe && th == Terrain.Herbe && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && tb == Terrain.Herbe){
				this.orientation = OrientationJalon.GAUCHE;
			}
			else {
				if(td == Terrain.Herbe && (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && tg == Terrain.Herbe && tb == Terrain.Herbe){
					this.orientation = OrientationJalon.HAUT;
				}
				else {
					if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) && th == Terrain.Herbe && tg == Terrain.Herbe && tb == Terrain.Herbe){
						this.orientation = OrientationJalon.DROITE;
					}
					else {
						if(td == Terrain.Herbe && th == Terrain.Herbe && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge)){
							this.orientation = OrientationJalon.BASGAUCHE;
						}
						else {
							if(td == Terrain.Herbe && (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && tb == Terrain.Herbe){
								this.orientation = OrientationJalon.HAUTGAUCHE;
							}
							else {
								if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) &&  (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && tg == Terrain.Herbe && tb == Terrain.Herbe){
									this.orientation = OrientationJalon.HAUTDROITE;
								}
								else {
									if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) && th == Terrain.Herbe && tg == Terrain.Herbe && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge)){
										this.orientation = OrientationJalon.BASDROITE;
									}
									else{
										this.orientation = OrientationJalon.NULL;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Méthode tracant le Jalon dans la direction précédemment établie
	 * @param ci
	 * @param curseurO
	 */
	public void tracerJalon(Circuit ci,Vecteur curseurO){
		Vecteur curseur = curseurO.cloneAsVecteur();
		switch(orientation){
		case BAS :
			curseur.autoAdd(new Vecteur(1,0));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,0));
			}
			break;
		case GAUCHE :
			curseur.autoAdd(new Vecteur(0,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(0,-1));
			}
			break;
		case HAUT :
			curseur.autoAdd(new Vecteur(-1,0));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(-1,0));
			}
			break;
		case DROITE :
			curseur.autoAdd(new Vecteur(0,1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(0,1));
			}
			break;
		case BASGAUCHE :
			curseur.autoAdd(new Vecteur(1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,-1));
			}
			break;
		case HAUTGAUCHE :
			curseur.autoAdd(new Vecteur(-1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(-1,-1));
			}
			break;
		case HAUTDROITE :
			curseur.autoAdd(new Vecteur(-1,1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(-1,1));
			}
			break;
		case BASDROITE :
			curseur.autoAdd(new Vecteur(1,1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge || ci.getTerrain(curseur) == Terrain.StartPoint)|| ci.getTerrain(curseur) == Terrain.EndLine){
				this.listeVecteurs.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,1));
			}
			break;
		case NULL :
			break;
		default :
			break;
		}
	}
	
	//getters et setters
	public int getNum() {
		return this.num;
	}

	public ArrayList<Vecteur> getListeVecteurs() {
		return listeVecteurs;
	}

	public void setListeVecteurs(ArrayList<Vecteur> listeVecteurs) {
		this.listeVecteurs = listeVecteurs;
	}
	
	public String toString(){
		String ret = "";
		for(Vecteur v : this.listeVecteurs)
			ret += v.toString() + " ";
		return ret;
	}
}
