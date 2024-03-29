package fr.needforcode.geometrie;

import java.util.ArrayList;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Jalon;
import fr.needforcode.circuit.OrientationJalon;
import fr.needforcode.circuit.Terrain;
import java.lang.Math;

/**
 * @author Camille
 * @version 1.0
 */
public class vTools {
	/**
	 * Calcul la norme d'un vecteur v
	 * @author Camille
	 * @param v Vecteur dont on va calculer la norme
	 * @return double  �tant la norme du vecteur v
	 */
	public static double norme(Vecteur v){
		//Formule : norme = Racine ( x� + y� )
		return  Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2));
	}
	
	/**
	 * Calcul du produit scalaire entre le Vecteur a et le Vecteur b
	 * @author Camille
	 * @param a Vecteur 1
	 * @param b Vecteur 2
	 * @return double �tant le produit scalaire entre le Vecteur a et le Vecteur b
	 */
	public static double scalaire(Vecteur a, Vecteur b){
		// Formule : scalaire(a.b) = a.x * b.x + a.y * b.y
		return a.getX() * b.getX() + a.getY() * b.getY();
	}
	
	/**
	 * Calcul du produit vectoriel entre le Vecteur a et le Vecteur b
	 * @author Camille
	 * @param a Vecteur 1
	 * @param b Vecteur 2
	 * @return Vecteur �tant le produit vectoriel entre vecteur a et vecteur b
	 */
	public static Vecteur produitVectoriel(Vecteur a , Vecteur b){
		//double nX, nY;
		//Nouveau vecteur n :
		// n.x = a.x * b.y - a.y * b.x
		// n.y = a.y * b.x - a.x * b.y
		return new Vecteur(a.getX() * b.getY() - a.getY() * b.getX(), a.getY() * b.getX() - a.getX() * b.getY());
	}
	
	
	/**
	 * Addition de deux Vecteur a et b
	 * @author Camille
	 * @param a Vecteur 1
	 * @param b vecteur 2
	 * @return Vecteur r�sultant
	 */
	public static Vecteur addition(Vecteur a, Vecteur b){
		//Nouveau vecteur n :
		//n.x = a.x + b.x
		//n.y = a.y + b.y
		return new Vecteur(a.getX() +  b.getX(), a.getY() + b.getY());
	}
	
	/**
	 * Soustraction de deux Vecteur a et b
	 * @author Camille
	 * @param a Vecteur 1
	 * @param b vecteur 2
	 * @return Vecteur r�sultant
	 */
	public static Vecteur soustraction(Vecteur a, Vecteur b){
		//Nouveau vecteur n :
		//n.x = b.x - a.x
		//n.y = b.y - a.y
		return new Vecteur(b.getX() -  a.getX(), b.getY() - a.getY());
	}
	
	/**
	 * Multiplication d'un Vecteur 1 par un scalaire d
	 * @author Camille
	 * @param a Vecteur 1
	 * @param b double scalaire
	 * @return Vecteur r�sultant
	 */
	public static Vecteur prodDouble(Vecteur v, double d) {
		
		return new Vecteur(v.getX() * d, v.getY() * d);
	}
	
	/**
	 * Rotation d'un Vecteur 1 d'un angle "angle"
	 * @author Camille
	 * @param a Vecteur 1
	 * @param angle de rotation
	 * @return void
	 */
	public static void rotation(Vecteur v, double angle) {
		double vX = v.getX();
		v.setX(vX * Math.cos(angle) - v.getY() * Math.sin(angle));
		v.setY(vX * Math.sin(angle) + v.getY() * Math.cos(angle));
	}
	
	/**
	 * normalisation d'un vecteur v
	 * @author Camille
	 * @param un Vecteur v
	 * @return Vecteur v normalis�
	 */
	public static Vecteur normalisation(Vecteur v) {
		double norme = norme(v);
		v.setX(v.getX() / norme);
		v.setY(v.getY() / norme);
		return v;
	}
	
	/**
	 * Test d'orthogonalit� de 2 vecteurs
	 * @param Vecteur a
	 * @param Vecteur b
	 * @return True si a et b orthogonaux, false sinon
	 */
	public static boolean isOrthogonaux(Vecteur a, Vecteur b) {
		return ((angle(a, b) == (Math.PI / 2)) || (angle(a, b) == -(Math.PI / 2)));
	}
	
	/**
	 * Calcul de la composanteZ des vecteur u et v
	 * @param Vecteur u
	 * @param Vecteur v
	 * @return double composanteZ
	 */
	public static double composanteZ(Vecteur u, Vecteur v) {
		double a = u.getX() * v.getY() - u.getY() * v.getX();
		return Math.signum(a);
	}
	
	/**
	 * Calcul de l'angle form� par 2 vecteurs (ave le signe, attention sens trigo)
	 * @param Vecteur a
	 * @param Vecteur b
	 * @return L'angle form� par le vecteur a et le vecteur b (en radian)
	 */
	public static double angle(Vecteur a, Vecteur b) {
		double res = composanteZ(a, b) * (Math.acos(scalaire(a, b) / (norme(a) * norme(b))));
		if (Double.isNaN(res)) {
			return 0;
		}
		return res;
	}
	
	
	/**
	 * Retourne un Vecteur de direction orthogonal au vecteur initial
	 * @param v1 Vecteur initial
	 * @return Vecteur orthogonal
	 */
	public static Vecteur directionOrthogonaleNormale(Vecteur v1){
		Vecteur v = v1.cloneAsVecteur();
		double vX = v.getX();
		v.setX(vX * Math.cos(Math.PI / 2) - v.getY() * Math.sin(Math.PI / 2));
		v.setY(vX * Math.sin(Math.PI / 2) + v.getY() * Math.cos(Math.PI / 2)); 
		return vTools.normalisation(v);
	}
	
	/**
	 * Methode permettant de d�terminer l'orientation du Jalon en fonction de la position du curseur
	 * @param ci Circuit courant
	 * @param curseur Vecteur
	 * @return OrientationJalon calcul�e
	 */
	public static OrientationJalon calculOrientation(Circuit ci, Vecteur curseur){
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
		
		if(td == Terrain.Herbe && th == Terrain.Herbe && tg == Terrain.Herbe && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge || tb == Terrain.EndLine))
			return OrientationJalon.BAS;
		if(td == Terrain.Herbe && th == Terrain.Herbe && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && tb == Terrain.Herbe)
			return OrientationJalon.GAUCHE;
		if(td == Terrain.Herbe && (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && tg == Terrain.Herbe && tb == Terrain.Herbe)
			return OrientationJalon.HAUT;
		if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) && th == Terrain.Herbe && tg == Terrain.Herbe && tb == Terrain.Herbe)
			return OrientationJalon.DROITE;
		if(td == Terrain.Herbe && th == Terrain.Herbe && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge))
			return OrientationJalon.BASGAUCHE;
		if(td == Terrain.Herbe && (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && (tg == Terrain.BandeBlanche || tg == Terrain.BandeRouge) && tb == Terrain.Herbe)
			return OrientationJalon.HAUTGAUCHE;
		if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) &&  (th == Terrain.BandeBlanche || th == Terrain.BandeRouge) && tg == Terrain.Herbe && tb == Terrain.Herbe)
			return OrientationJalon.HAUTDROITE;
		if((td == Terrain.BandeBlanche || td == Terrain.BandeRouge) && th == Terrain.Herbe && tg == Terrain.Herbe && (tb == Terrain.BandeBlanche || tb == Terrain.BandeRouge))
			return OrientationJalon.BASDROITE;
		return OrientationJalon.NULL;
	}
	
	/**
	 * M�thode d�terminant si le Jalon j croise un des Jalon de la liste de Jalon pass�e en param�tre.
	 * @param j Jalon test�.
	 * @param jalonsCircuit liste de Jalon � inspecter.
	 * @return true si croisement, false sinon.
	 */
	public static boolean croiserJalons(Jalon j, ArrayList<Jalon> jalonsCircuit) {
		int nb = 0;
		for (int i = 1; i <= 50; i++) {
			nb = j.getNum() - i;
			if (nb >= 1 && nb <= jalonsCircuit.size()) {
				Jalon courant = jalonsCircuit.get(nb);
				for (Vecteur v2 : j.getListeVecteurs()) {
					for (Vecteur v : courant.getListeVecteurs()) {
						if (v.equalsArrondi(v2, 10)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * M�thode d�terminant si [ab] coupe [cd].
	 * Non d�velopp�e.
	 * @param a Xa-Ya
	 * @param b Xb-Yb
	 * @param c Xc-Yc
	 * @param d Xd-Yd
	 * @return true si croisement, false sinon.
	 */
	//TODO: Developper cette m�thode.
	public static boolean croiser(Vecteur a, Vecteur b, Vecteur c, Vecteur d){
		return false;
	}
	
}
