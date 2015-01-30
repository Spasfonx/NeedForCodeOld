package fr.needforcode.geometrie;
import java.math.*;

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
		double nX, nY;
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
	 * @return Vecteur
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
	 * @return Vecteur
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
	 * @return Vecteur
	 */
	public static Vecteur prodDouble(Vecteur v, double d) {
		
		return new Vecteur(v.getX() * d, v.getY() * d);
	}
	
	/**
	 * Rotation d'un Vecteur 1 d'un angle "angle"
	 * @author Camille
	 * @param a Vecteur 1
	 * @param angle de rotation
	 * @return
	 */
	public static void rotation(Vecteur v, double angle) {
		double vX = v.getX();
		v.setX(vX * Math.cos(angle) - v.getY() * Math.sin(angle));
		v.setY(vX * Math.sin(angle) + v.getY() * Math.cos(angle));
	}
	
	// Ci dessous non test� :
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
	 * @return 
	 */
	public static double composanteZ(Vecteur u, Vecteur v) {
		double a = u.getX() * v.getY() - u.getY() * v.getX();
		return Math.signum(a);
	}
	
	/**
	 * Calcul de l'angle form� par 2 vecteurs (ave le signe, attention sens trigo)
	 * @param Vecteur a
	 * @param Vecteur bb
	 * @return L'angle form� par le vecteur aet le vecteur b
	 */
	public static double angle(Vecteur a, Vecteur b) {
		double res = composanteZ(a, b) * (Math.acos(scalaire(a, b) / (norme(a) * norme(b))));
		if (Double.isNaN(res)) {
			return 0;
		}
		return res;
	}
}