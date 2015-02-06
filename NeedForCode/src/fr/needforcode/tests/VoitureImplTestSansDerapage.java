package fr.needforcode.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.voiture.Commande;
import fr.needforcode.voiture.VoitureImpl;

public class VoitureImplTestSansDerapage {
	
	VoitureImpl maVoitureImpl;
	Commande maCommande;
	
	@Before
	public void setUp() throws Exception {
		maVoitureImpl = new VoitureImpl(0.8, 0.2, 0.3, 0.2, 0.5, 0.2, 0.4, new Vecteur(1,2), new Vecteur (1,3), 0.2 );
//		System.out.println("getMaxTurnSansDerapage "+ maVoitureImpl.getMaxTurnSansDerapage());
//		System.out.println("getBraquage "+ maVoitureImpl.getBraquage());
//		System.out.println("getDirection "+ maVoitureImpl.getDirection().toString());
//		System.out.println("getPosition "+ maVoitureImpl.getPosition().toString());
//		System.out.println("getVitesse "+ maVoitureImpl.getVitesse());
//		System.out.println("getDerapage "+ maVoitureImpl.getDerapage());
		maCommande = new Commande(0.7,0); // on créer une commande à appliquer sur notre voiture
		//(0.7,0) -> ici la commande permet une acceleration à 0.7 et d'aller tout droit (turn = 0)
//		System.out.println("Application de la commande :");
		maVoitureImpl.piloter(maCommande); //on applique la commande a notre voiture
//		System.out.println("getDirection "+ maVoitureImpl.getDirection().toString());
//		System.out.println("getPosition "+ maVoitureImpl.getPosition().toString());
//		System.out.println("getVitesse "+ maVoitureImpl.getVitesse());
//		System.out.println("getDerapage "+ maVoitureImpl.getDerapage());
	}

	@After
	public void tearDown() throws Exception {
		maVoitureImpl = null;
	}
	

	@Test
	public void TestGetVitesse() {
		assertEquals(maVoitureImpl.getVitesse(),0.31,0.01);
	}

	@Test
	public void TestGetPosition() {
		assertEquals(maVoitureImpl.getPosition().getX(),1.098031,0.000001);
		assertEquals(maVoitureImpl.getPosition().getY(),2.294092,0.000001);
	}

	@Test
	public void TestGetDirection() {
		assertEquals(maVoitureImpl.getDirection().getX(),0.316228,0.000001);
		assertEquals(maVoitureImpl.getDirection().getY(),0.948683,0.00001);
	}

	@Test
	public void TestGetDerapage() {
		assertEquals(maVoitureImpl.getDerapage(),false); 
	}

	@Test
	public void TestGetBraquage() {
		assertEquals(maVoitureImpl.getBraquage(),0.2,0.1);
		//ps: braquage est une constante, et qui deplus est non utilisé dans ce cas là
	}

}
