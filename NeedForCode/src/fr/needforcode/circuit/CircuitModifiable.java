package fr.needforcode.circuit;

/**
 * Interface circuit modifiable
 * @author Camille
 *
 */
public interface CircuitModifiable extends Circuit {
	/**
	 * Modifie le pixel de Terrain situé à (i,j) par un Terain de type t.
	 * @param i abscisse
	 * @param j ordonnée
	 * @param t Terrain à set
	 */
	public void setTerrain(int i, int j, Terrain t);
}