package fr.needforcode.tests;

import static org.junit.Assert.*;

import org.junit.*;

import fr.needforcode.voiture.*;
import fr.needforcode.geometrie.*;

public class VoitureImplTestAvecDerapage {
	
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
		maCommande = new Commande(0.7,0.7853); // on créer une commande à appliquer sur notre voiture
		//(0.7,0.7853) -> ici la commande permet une acceleration à 0.7 et tourner a 45° (pie/4 soit 0.7853 rad)
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
		assertEquals(maVoitureImpl.getVitesse(),0.2,0.1);
	}

	@Test
	public void TestGetPosition() {
		assertEquals(maVoitureImpl.getPosition().getX(),1.151412,0.000001);
		assertEquals(maVoitureImpl.getPosition().getY(),2.614064,0.000001);
	}

	@Test
	public void TestGetDirection() {
		assertEquals(maVoitureImpl.getDirection().getX(),0.757058,0.000001);
		assertEquals(maVoitureImpl.getDirection().getY(),3.07032,0.00001);
	}

	@Test
	public void TestGetDerapage() {
		assertEquals(maVoitureImpl.getDerapage(),true); 
		// dans ce cas-ci la voiture derape encore apres application de la commande
	}

	@Test
	public void TestGetBraquage() {
		assertEquals(maVoitureImpl.getBraquage(),0.2,0.1);
		//ps: braquage est une constante
	}
	

}
