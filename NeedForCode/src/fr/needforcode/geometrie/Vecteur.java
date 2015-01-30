package fr.needforcode.geometrie;

import java.io.Serializable;
/**
 * Class Vecteur
 * @author Camille
 *
 */
public class Vecteur implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double x,y;
	
	/**
	 * Constructeur par défaut
	 * @param x
	 * @param y
	 */
	public Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructeur paramétré
	 * @param v1
	 * @param v2
	 */
	public Vecteur(Vecteur v1, Vecteur v2) {
		this(v2.getX() - v1.getX(), v2.getY() - v1.getY());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Clone d'un vecteur
	 * @author Camille
	 * @return
	 */
	public Vecteur cloneAsVecteur() {
		return new Vecteur(x, y);
	}
}
