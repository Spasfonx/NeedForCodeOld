package fr.needforcode.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.*;
import fr.needforcode.voiture.*;

public class CommandeTest {
	
	Commande maCommande;
	Commande maCommande2;
	
	@Before 
	public void setUp1() throws Exception {
		maCommande = new Commande (38,-25);
		maCommande.controlCommand();
		
		maCommande2 = new Commande (-0.5, 0.75);
		maCommande2.controlCommand();
	}
	
	@After 
	public void tearDown() throws Exception {
		maCommande = null;
	}
	
	@Test
	public void testGetTurn(){
		assertEquals(maCommande.getTurn(), -1, 0.0001);
		assertNotEquals(maCommande.getTurn(), -25, 0.0001);
		assertEquals(maCommande2.getTurn(), 0.75, 0.0001);
		assertNotEquals(maCommande2.getTurn(), 1, 0.0001);
		
	}
	
	@Test 
	public void testGetAcc(){
		assertEquals(maCommande.getAcc(), 1, 0.0001);
		assertNotEquals(maCommande.getAcc(), 38, 0.0001);
		assertEquals(maCommande2.getAcc(), -0.5, 0.0001);
		assertNotEquals(maCommande2.getAcc(), -1, 0.001);
	}
	
}
