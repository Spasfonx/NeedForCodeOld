package fr.needforcode.circuit;

/**
 * Interface circuit modifiable
 * @author Camille
 *
 */
public interface CircuitModifiable extends Circuit {
	/**
	 * Modifie le pixel de Terrain situ� � (i,j) par un Terain de type t.
	 * @param i abscisse
	 * @param j ordonn�e
	 * @param t Terrain � set
	 */
	public void setTerrain(int i, int j, Terrain t);
}