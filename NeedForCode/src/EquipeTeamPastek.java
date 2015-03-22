
import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.course.Course;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.voiture.Commande;
import fr.needforcode.pilote.ChampDeVision;

public class EquipeTeamPastek extends Equipe {
	private static int nbExec = 0;
	public EquipeTeamPastek(String n, Course c) {
		super("Pastek", c);
	}
	public EquipeTeamPastek() {
		super("Pastek");
	}
	@Override
	public Commande run() {
		this.nbExec++;
		if(nbExec % 1 == 0){
			ChampDeVision monChampDeVision = getChampsDeVision();
			int xMax = monChampDeVision.getMatrice().length;
			int yMax = monChampDeVision.getMatrice()[0].length;
			int xBordureDebut = xMax-1, yBordureDebut = 0, xBordureFin = 0, yBordureFin = 0;
			int sorti = 0;
			for(int i = xMax - 1; i >= 0; i--){
				for(int j = 0; j <= yMax - 1; j++){
					if (TerrainTools.isRunnable(monChampDeVision.getMatrice()[i][j]) && sorti == 0 && monChampDeVision.getMatrice()[i][j]!=Terrain.Out){
						xBordureDebut = i;
						yBordureDebut = j;
						sorti = 1;
					}
				}
			}
			//System.out.println(xBordureDebut + "|" + yBordureDebut +" : " + monChampDeVision[xBordureDebut][yBordureDebut]);
			double t = goToPixel(xBordureDebut,yBordureDebut);
			/*
for(int i = 1; i >= 0; i--){
for(int j = 0; j <= yMax - 1; j++){
if (TerrainTools.isRunnable(monChampDeVision[i][j]) && sorti == 0 && monChampDeVision[i][j]!=Terrain.Out){
xBordureDebut = i;
yBordureDebut = j;
sorti = 1;
}
}
}
sorti = 0;
for(int i = xBordureDebut; i >= 0; i--){
for(int j = yBordureDebut; j <= yMax - 1; j++){
if (monChampDeVision[i][j]!=Terrain.Route &&
monChampDeVision[i][j]!=Terrain.BandeBlanche &&
monChampDeVision[i][j]!=Terrain.BandeRouge
&& sorti == 0){
xBordureFin = i;
yBordureFin = j;
sorti = 1;
}
}
}*/
			double a = 0.8;
			//double t = goToPixel(monChampDeVision,(xBordureDebut+xBordureFin)/2,(yBordureDebut+yBordureFin)/2);
			//System.out.println(t);
			return new Commande(a, t);
		}
		else{
			//System.out.println("Je fais le else !");
			return new Commande(0,0);
		}
	}
}