package fr.needforcode.voiture.factory;


import fr.needforcode.circuit.Circuit;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureImpl;
public class MiageCarFactory implements VoitureFactory {
	private double vmax = 0.9;
	private double alpha_c = 0.02;
	private double braquage = 0.2;
	private double alpha_f = 0.001;
	private double beta_f = 0.006;
	private double alpha_derapage = 0.003;
	// private double masse = 1;
	private double vitesse_sortie_derapage = 0.15;
	private double vitesse = 0.;
	private Vecteur position;
	private Vecteur direction;
	private Circuit trk;
	
	public MiageCarFactory(Circuit track) {
		super();
		this.trk = track;
		this.position = track.getPointDepart().cloneAsVecteur();
		this.direction = track.getDirectionDepart().cloneAsVecteur();
	}
	
	public Voiture build() {
		return new VoitureImpl(vmax, braquage, alpha_c, alpha_f, beta_f,
		alpha_derapage, vitesse, position, direction,
		vitesse_sortie_derapage,trk);
	}
	
	@Deprecated
	public Voiture build2() {
		return new VoitureImpl(vmax, braquage, alpha_c, alpha_f, beta_f,
		alpha_derapage, vitesse, vTools.addition(position, new Vecteur(50,0)), direction.cloneAsVecteur(),
		vitesse_sortie_derapage,trk);
	}
	
	
}
