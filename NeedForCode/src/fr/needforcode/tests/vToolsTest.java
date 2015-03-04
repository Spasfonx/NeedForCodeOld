package fr.needforcode.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;

public class vToolsTest {
	protected Vecteur v1;
	protected Vecteur v2;

	@Before
	public void setUp() throws Exception {
		v1 = new Vecteur(new Double(10.0),new Double(20.0));
		v2 = new Vecteur(new Double(5.0),new Double(1.0));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNorme() {
		assertEquals(vTools.norme(v1),22.3607,0.0001);
	}
	
	@Test
	public void testScalaire() {
		assertEquals(vTools.scalaire(v1,v2),70.0,0.0001);
	}
	
	@Test
	public void testAddition() {
		Vecteur v3 = vTools.addition(v1, v2);
		assertTrue(v3.getX() == 15.0);
		assertTrue(v3.getY() == 21.0);
	}
	
	@Test
	public void testSoustraction() {
		Vecteur v4 = vTools.soustraction(v1, v2);
		assertTrue(v4.getX() == -5.0);
		assertTrue(v4.getY() == -19.0);
	}
	
	@Test
	public void testProdDouble() {
		Vecteur v5 = vTools.prodDouble(v1, 2.0);
		assertTrue(v5.getX() == 20.0);
		assertTrue(v5.getY() == 40.0);
	}
	
	@Test
	public void testProduitVectoriel() {
		Vecteur v6 = vTools.produitVectoriel(v1,v2);
		assertTrue(v6.getX() == -90.0);
		assertTrue(v6.getY() == 90.0);
	}
	
	@Test
	public void testRotation() {
		Vecteur v7 = v1.cloneAsVecteur();
		vTools.rotation(v7,0.43633231299858);
		assertEquals(v7.getX(),0.610713,0.0001);
		assertEquals(v7.getY(),22.3523,0.0001);
	}
	
	@Test
	public void testDirectionOrthogonaleNormale() {
		Vecteur v8 = v1.cloneAsVecteur();
		System.out.println((vTools.directionOrthogonaleNormale(v8)).toString());

	}

}
