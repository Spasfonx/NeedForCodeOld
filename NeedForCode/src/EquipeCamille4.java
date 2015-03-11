import fr.needforcode.course.Course;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Commande;

public class EquipeCamille4 extends Equipe {
	
	private int i = 0;

	public EquipeCamille4(String n, Course c) {
		super("Camille", c);
	}
	
	public EquipeCamille4() {
		super("Camille");
	}

	@Override
	public Commande run() {
		double a = 1;
		double t = 0.005;
		
		if (i > 200) {
			a = 0.1; t = 0.065;
		}
		
		i++;
		return new Commande(a, t);

	}

}