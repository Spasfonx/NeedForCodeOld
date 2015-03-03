package fr.needforcode.circuit;

import java.util.ArrayList;
import java.util.HashMap;

import fr.needforcode.chrono.Chrono;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.Vecteur;

public class Jalon {
	
	ArrayList<Vecteur> listeJalons;
	private OrientationJalon orientation;
	private static int num;
	private HashMap<Equipe,Chrono> passage;
	
	public Jalon(ArrayList<Vecteur> lj){
		this.listeJalons = lj;
	}
	
	public Jalon(Circuit ci, Vecteur curseur){
		num++;
		this.listeJalons = new ArrayList<Vecteur>();
		setOrientation(ci,curseur);
		tracerJalon(ci,curseur);
	}
	
	public void setOrientation(Circuit ci, Vecteur curseur){
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
		
		if(td == Terrain.Herbe && th == Terrain.Herbe && tg == Terrain.Herbe && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge)){
			this.orientation = OrientationJalon.Bas;
		}
		else {
			if(td == Terrain.Herbe && th == Terrain.Herbe && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && tb == Terrain.Herbe){
				this.orientation = OrientationJalon.Gauche;
			}
			else {
				if(td == Terrain.Herbe && (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && tg == Terrain.Herbe && tb == Terrain.Herbe){
					this.orientation = OrientationJalon.Haut;
				}
				else {
					if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) && th == Terrain.Herbe && tg == Terrain.Herbe && tb == Terrain.Herbe){
						this.orientation = OrientationJalon.Droite;
					}
					else {
						if(td == Terrain.Herbe && th == Terrain.Herbe && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge)){
							this.orientation = OrientationJalon.BasGauche;
						}
						else {
							if(td == Terrain.Herbe && (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && tb == Terrain.Herbe){
								this.orientation = OrientationJalon.HautGauche;
							}
							else {
								if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) &&  (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && tg == Terrain.Herbe && tb == Terrain.Herbe){
									this.orientation = OrientationJalon.HautDroit;
								}
								else {
									if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) && th == Terrain.Herbe && tg == Terrain.Herbe && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge)){
										this.orientation = OrientationJalon.BasDroite;
									}
									else{
										this.orientation = OrientationJalon.Null;
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
	
	public void tracerJalon(Circuit ci,Vecteur curseurO){
		Vecteur curseur = curseurO.cloneAsVecteur();
		switch(orientation){
		case Bas :
			curseur.autoAdd(new Vecteur(1,0));
			
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,0));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case Gauche :
			curseur.autoAdd(new Vecteur(0,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(0,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case Haut :
			curseur.autoAdd(new Vecteur(-1,0));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(-1,0));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case Droite :
			curseur.autoAdd(new Vecteur(0,1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(0,1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case BasGauche :
			curseur.autoAdd(new Vecteur(1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case HautGauche :
			curseur.autoAdd(new Vecteur(-1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(-1,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case HautDroit :
			curseur.autoAdd(new Vecteur(1,-1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,-1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case BasDroite :
			curseur.autoAdd(new Vecteur(1,1));
			while(ci.getTerrain(curseur) == Terrain.Route || (ci.getTerrain(curseur) == Terrain.BandeBlanche || ci.getTerrain(curseur) == Terrain.BandeRouge)){
				this.listeJalons.add(curseur.cloneAsVecteur());
				curseur.autoAdd(new Vecteur(1,1));
//				System.out.println(curseur.toString() + " Type : " + ci.getTerrain(curseur) + " ajouté au jalon numéro : " + this.num);
			}
			break;
		case Null :
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
