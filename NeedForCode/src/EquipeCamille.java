import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.course.Course;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Commande;
import fr.needforcode.pilote.champsDeVision;


public class EquipeCamille extends Equipe {
	
	private static int nbExec = 0;

	public EquipeCamille(String n, Course c) {
		super("Camille", c);
	}
	
	public EquipeCamille() {
		super("Camille");
	}

	@Override
	public Commande run() {
		this.nbExec++;
		
		if(nbExec % 1 == 0){
			
			Terrain [][] monChampDeVision = getChampsDeVision();
			int xMax = monChampDeVision.length;
			int yMax = monChampDeVision[0].length;
			int xBordureDebut = 0, yBordureDebut = 0, xBordureFin = 0, yBordureFin = 0;
			
			for(int i =  xMax - 1; i >= 0; i--){
				for(int j = 0; j <= yMax - 1; j++){
					if(TerrainTools.isRunnable(monChampDeVision[i][j])){
						xBordureDebut = i;
						yBordureDebut = j;
					}
					else{
						xBordureDebut = xMax / 2;
						yBordureDebut = yMax / 2;
					}
				}				
			}
			
			for(int i = xBordureDebut - 1; i > 0; i--){
				for(int j = yBordureDebut - 1; j < yMax; j++){
					if(TerrainTools.isRunnable(monChampDeVision[i][j])){
						xBordureFin = i;
						yBordureFin = j;
					}
					else{
						xBordureFin = xMax / 2;
						yBordureFin = yMax / 2;
					}
				}
				
			}
			
	
			System.out.println("yBordureFin = " + yBordureFin + "; yBordureDebut = " + yBordureDebut);
			
			double a = 0.8;
			double t = goToPixel(monChampDeVision,(yBordureFin-yBordureDebut)/2,(xBordureFin+xBordureDebut)/2);
			//double t = goToPixel(monChampDeVision,(xBordureDebut+xBordureFin)/2, (yBordureDebut+yBordureFin)/2);
			//t = goToPixel(100, 160);
			System.out.println(t);
			/*
			double a = 1;
			double t = 0.005;
			
			if (i > 200) {
				a = 0.1; t = 0.5;
			}
			
			i++;
		*/
			return new Commande(a, t);
			//System.out.println(goToPixel(monChampDeVision, 100, 100));
			//System.out.println("test");
		}

		else{
			return new Commande(0,0);

		}
		
		

	}

}
