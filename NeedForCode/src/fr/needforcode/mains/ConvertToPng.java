package fr.needforcode.mains;

import fr.needforcode.circuit.*;
import fr.needforcode.circuit.factory.CircuitFactory;

/**
 * class permettant le lancement de l'algorithme de conversion d'un fichier trk en png
 * @author camille
 *
 */
public class ConvertToPng {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage : java -classpath bin fr.needforcode.mains.ConvertToPng file.trk");
		return;
		}
			TerrainTools.previsualisation(args[0]);
			System.out.println("Converting " + args[0] + " to PNG done.");
		}
}
