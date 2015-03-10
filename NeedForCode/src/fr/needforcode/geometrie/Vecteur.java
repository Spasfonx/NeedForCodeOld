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
	
	/**
	 * Cette méthode permet de comparer l'égalité de vecteur à 10-3 près
	 * @param obj
	 * @return
	 */
	@Deprecated
	public boolean equalsArrondi(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		Double otherX = other.getX();
		Double otherY = other.getY();
		otherX *= 1000;
		otherY *= 1000;
		otherX = (double) Math.round(otherX);
		otherY = (double) Math.round(otherY);
		otherX /= 1000;
		otherY /= 1000;
		
		Double currentX = this.x;
		Double currentY = this.y;
		currentX *= 1000;
		currentY *= 1000;
		currentX = (double) Math.round(currentX);
		currentY = (double) Math.round(currentY);
		currentX /= 1000;
		currentY /= 1000;
		
		if (Double.doubleToLongBits(currentX) != Double.doubleToLongBits(otherX))
			return false;
		if (Double.doubleToLongBits(currentY) != Double.doubleToLongBits(otherY))
			return false;
		
		return true;
	}
	
	public boolean equalsArrondi(Object obj,double precision) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		Double otherX = other.getX();
		Double otherY = other.getY();
		otherX *= precision;
		otherY *= precision;
		otherX = (double) Math.round(otherX);
		otherY = (double) Math.round(otherY);
		otherX /= precision;
		otherY /= precision;
		
		Double currentX = this.x;
		Double currentY = this.y;
		currentX *= precision;
		currentY *= precision;
		currentX = (double) Math.round(currentX);
		currentY = (double) Math.round(currentY);
		currentX /= precision;
		currentY /= precision;
		//System.out.println("X : [" + currentX + ";" + otherX + "]");
		//System.out.println("Y : [" + currentY + ";" + otherY + "]");
		if (Double.doubleToLongBits(currentX) != Double.doubleToLongBits(otherX))
			return false;
		if (Double.doubleToLongBits(currentY) != Double.doubleToLongBits(otherY))
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
	 * @return Vecteur
	 */
	public Vecteur cloneAsVecteur() {
		return new Vecteur(x, y);
	}
	
	/** 
	 * produit scalaire du vecteur courant avec le vecteur v et modification des paramètres du vecteur courant
	 * @param a Vecteur v
	 */
	public void autoAdd(Vecteur v) {
		x = (x + v.x);
		y = (y + v.y);
	}

	/** 
	 * produit vectoriel du vecteur courant avec un double d et modification des paramètres du vecteur courant
	 * @param a double d
	 */
	public void autoProdDouble(double d) {
		x = (x * d);
		y = (y * d);
	}
	
	@Override
	public String toString(){
		return "Vecteur [x = " + x + " y = "+ y + "]";
	}
}
