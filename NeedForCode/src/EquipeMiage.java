import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.course.Course;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Commande;

public class EquipeMiage extends Equipe {


	private static int nbExec = 0;

	public EquipeMiage(String n, Course c) {
		super("CamilleLeMiagiste!", c);
	}

	public EquipeMiage() {
		super("CamilleLeMiagiste!");
	}

	@Override
	public Commande run() {

		Terrain [][] monChampDeVision = getChampsDeVision();
		int xMax = monChampDeVision.length;
		int yMax = monChampDeVision[0].length;
		int xBordureDebut = 0, yBordureDebut = 0, xBordureFin = 0, yBordureFin = 0, j = yMax - 1 , i = xMax - 1;
		boolean sortie = false;

		/*while(!sortie && i >=  0){
			while(!sortie && j >= 0){
				if(TerrainTools.isRunnable(monChampDeVision[i][j])){
					xBordureDebut = i;
					yBordureDebut = j;
					sortie = true;
				}
				j--;
			}	
			i--;
		}*/
		
		int pas = 100;
		i = pas;
		j = yMax / 2;
		
		while(TerrainTools.isRunnable(monChampDeVision[i][j]) && j > 0) {
			j--;
		}
		
		xBordureDebut = i;
		yBordureDebut = j;

		double t = goToPixel(monChampDeVision,xBordureDebut,yBordureDebut);
		double a = 0.9;

		return new Commande(a, t);
	}
}

