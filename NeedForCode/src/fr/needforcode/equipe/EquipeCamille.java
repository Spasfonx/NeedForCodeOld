package fr.needforcode.equipe;

import fr.needforcode.course.Course;
import fr.needforcode.voiture.Commande;

public class EquipeCamille extends Equipe {

	public EquipeCamille(String n, Course c) {
		super(n, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Commande run() {
		// TODO Auto-generated method stub
		return new Commande(1,1);
	}

}
