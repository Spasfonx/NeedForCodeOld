import fr.needforcode.circuit.Terrain;
import fr.needforcode.course.Course;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Commande;

public class EquipeMiage extends Equipe {
	
	private int i = 0;

	public EquipeMiage(String n, Course c) {
		super("Miage", c);
	}
	
	public EquipeMiage() {
		super("Miage");
	}

	@Override
	public Commande run() {
		double a = 1;
		double t = 0.005;
		
		Terrain[][] cdv = this.getChampsDeVision();
		
		if (i > 190) {
			a = 0.1; t = 0.065;
		}
		
		i++;
		return new Commande(a, t);

	}

}
