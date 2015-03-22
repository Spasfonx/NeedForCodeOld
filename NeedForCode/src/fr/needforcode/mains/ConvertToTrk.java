package fr.needforcode.mains;

import fr.needforcode.circuit.TerrainTools;

public class ConvertToTrk {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
			.println("Usage : java -classpath bin fr.needforcode.mains.ConvertToTrk file.png");
			return;
		}
		String filename = args[0];
		TerrainTools.convertToTrk(filename);
		System.out.println("Converting " + filename + " to TRK done.");
	}
}