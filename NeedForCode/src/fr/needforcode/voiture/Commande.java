package fr.needforcode.voiture;

/**
 * @author Omar Ben Bella
 * @version 1.0
 */

public class Commande {

	private double acc;
	private double turn;

	/**
	  * Constructeur de la classe Commande avec un acc�l�ration et une rotation 
	  * 
	  * @param double acc - acc�l�ration
	  * @param double turn - rotation
	  */
	public Commande(double acc, double turn) {
		super();
		this.acc = acc;
		this.turn = turn;
	}

	/**
	  * Contr�le de l'acc�leration et de la rotation
	  * qui doivent �tre compris entre -1 et 1
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
