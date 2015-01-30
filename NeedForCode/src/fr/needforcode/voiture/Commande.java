package fr.needforcode.voiture;

/**
 * @author Omar Ben Bella
 * @version 1.0
 */

public class Commande {

	private double acc;
	private double turn;

	/**
	  * Constructeur de la classe Commande avec un accélération et une rotation 
	  * 
	  * @param a double acc
	  * @param b double turn
	  */
	public Commande(double acc, double turn) {
		super();
		this.acc = acc;
		this.turn = turn;
	}

	/**
	  * Contrôle de l'accélerarion et de la rotation compris entre -1 et 1
	  */
	public void controlCommand() {
		if (acc > 1)
			acc = 1;
		else if (acc < -1)
			acc = -1;
		if (turn < -1)
			turn = -1;
		else if (turn > 1)
			turn = 1;
	}

	public double getTurn() {
		return turn;
	}

	public double getAcc() {
		return acc;
	}

	@Override
	public String toString() {
		return "Commande [acc=" + acc + ", turn=" + turn + "]";
	}

}
