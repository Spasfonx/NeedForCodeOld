package fr.needforcode.circuit;

import java.util.ArrayList;
import java.util.HashMap;

import fr.needforcode.chrono.Chrono;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.Vecteur;

/**
 * Classe jalon, un jalon est un segment(ArrayList<vecteur>) symbolisant un passage obligatoire pendant la course
 * @author camille
 *
 */
public class Jalon {
	
	ArrayList<Vecteur> listeJalons;
	private OrientationJalon orientation;
	private static int num;
	private HashMap<Equipe,Chrono> passage;
	
	/**
	 * Constructeur à partir d'un segment existant
	 * @param lj ArrayList<Vecteur>
	 */
	public Jalon(ArrayList<Vecteur> lj){
		this.listeJalons = lj;
	}
	
	/**
	 * Constructeur utilisé par la construction d'un circuit(construit le Jalon)
	 * @param ci Circuit
	 * @param curseur vecteur (permet la construction des jalons)
	 */
	public Jalon(Circuit ci, Vecteur curseur){
		num++;
		this.listeJalons = new ArrayList<Vecteur>();
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
		//System.out.println("Orientation : " + this.orientation.toString());
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
			
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,0));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case GAUCHE :
			curseur.autoAdd(new Vecteur(0,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(0,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case HAUT :
			curseur.autoAdd(new Vecteur(-1,0));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(-1,0));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case DROITE :
			curseur.autoAdd(new Vecteur(0,1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(0,1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case BASGAUCHE :
			curseur.autoAdd(new Vecteur(1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case HAUTGAUCHE :
			curseur.autoAdd(new Vecteur(-1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(-1,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case HAUTDROITE :
			curseur.autoAdd(new Vecteur(1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case BASDROITE :
			curseur.autoAdd(new Vecteur(1,1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case NULL :
			break;
		default :
			break;
		}
		//System.out.println("Jalon " + this.num + " crée");
	}
	

	public ArrayList<Vecteur> getListeJalons() {
		return listeJalons;
	}

	public void setListeJalons(ArrayList<Vecteur> listeJalons) {
		this.listeJalons = listeJalons;
	}
}
