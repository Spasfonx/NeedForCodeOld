import fr.needforcode.circuit.Terrain;
import fr.needforcode.course.Course;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Commande;
import fr.needforcode.pilote.champsDeVision;


public class EquipeCamille extends Equipe {
	
	private int i = 0;

	public EquipeCamille(String n, Course c) {
		super("Camille", c);
	}
	
	public EquipeCamille() {
		super("Camille");
	}

	@Override
	public Commande run() {
		
		Terrain [][] monChampDeVision = getChampsDeVision();
		System.out.println("test");
		/*
		double xMax = monChampDeVision.length;
		double yMax = monChampDeVision[0].length;
		int xBordureDebut = 0, yBordureDebut = 0, xBordureFin = 0, yBordureFin = 0;

		for(int i = (int) xMax; i > 0; i--){
			for(int j = 0; j < yMax; j++){
				if(monChampDeVision[i][j].equals(Terrain.Route)){
					xBordureDebut = i;
					yBordureDebut = j;
				}
			}
			
		}
		
		for(int i = xBordureDebut; i > 0; i--){
			for(int j = yBordureDebut; j < yMax; j++){
				if(!monChampDeVision[i][j].equals(Terrain.Route)){
					xBordureFin = i;
					yBordureFin = j;
				}
			}
			
		}
		double a = 1;
		double t = goToPixel((xBordureDebut+xBordureFin)/2, (yBordureDebut+yBordureFin)/2);
		t = goToPixel(100, 160);
		System.out.println(t);*/
		double a = 1;
		double t = 0.005;
		
		Terrain[][] cdv = this.getChampsDeVision();
		
		if (i > 200) {
			a = 0.1; t = 0.5;
		}
		
		i++;
		//System.out.println(goToPixel(monChampDeVision, 100, 100));
		System.out.println("test");
		return new Commande(a, t);
		

	}

}
