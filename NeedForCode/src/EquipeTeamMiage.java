import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.course.Course;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Commande;
import fr.needforcode.pilote.ChampDeVision;

/**
 * Equipe de test. Son comportement fait le tour du circuit,
 * mais comporte certains bugs et n'est pas du tout optimisé.
 * @author Omar Ben Bella
 * @version 1.0
 */
public class EquipeTeamMiage extends Equipe {
	private static int nbExec = 0;

	public EquipeTeamMiage(String n, Course c) {
		super("Miage", c);
	}

	public EquipeTeamMiage() {
		super("Miage");
	}

	@Override
	public Commande run() {
		nbExec++;
		
		if (nbExec % 1 == 0) {
			ChampDeVision monChampDeVision = getChampsDeVision();
			int xMax = monChampDeVision.getMatrice().length;
			int yMax = monChampDeVision.getMatrice()[0].length;
			int xBordureDebut = xMax - 1, yBordureDebut = 0, xBordureFin = 0, yBordureFin = 0;
			int sorti = 0;
			
			for (int i = xMax - 1; i >= 0; i--) {
				for (int j = 0; j <= yMax - 1; j++) {
					if (TerrainTools
							.isRunnable(monChampDeVision.getMatrice()[i][j])
							&& sorti == 0
							&& monChampDeVision.getMatrice()[i][j] != Terrain.Out) {
						xBordureDebut = i;
						yBordureDebut = j;
						sorti = 1;
					}
				}
			}

			double t = goToPixel(xBordureDebut, yBordureDebut + 50);
			double a = 0.8;

			return new Commande(a, t);
		} else {
			return new Commande(0, 0);
		}
	}
}