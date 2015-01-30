package fr.needforcode.circuit;

/**
 * Interface circuit modifiable
 * @author Camille
 *
 */
public interface CircuitModifiable extends Circuit {
	public void setTerrain(int i, int j, Terrain t);
	//public void majDijkstra();
}