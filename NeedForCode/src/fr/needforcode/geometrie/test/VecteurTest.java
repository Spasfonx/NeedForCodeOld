package fr.needforcode.geometrie.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.needforcode.geometrie.Vecteur;

public class VecteurTest {
	protected Vecteur v1;
	protected Vecteur va;
	protected Vecteur vb;
	protected Vecteur v2;
	
	@Before
	public void setUp() throws Exception {
		v1 = new Vecteur(new Double(10.0),new Double(15.0));
		va = new Vecteur(new Double(1.0),new Double(2.0));
		vb = new Vecteur(new Double(5.0),new Double(6.0));
		v2 = new Vecteur(va,vb);
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

//	@Test
//	public void testHashCode() {
//		assertTrue(v1.hashCode() is int);
//	}
	
	@Test
	public void testEquals() {
		assertEquals(v1,v1);
	}

	@Test
	public void testCloneAsVecteur() {
		Vecteur v3 = v1.cloneAsVecteur();
		assertEquals(v1,v3);
	}

	@Test
	public void testGetX() {
		assertEquals(10.0,v1.getX(),0.001);
	}

	@Test
	public void testGetY() {
		assertEquals(15.0,v1.getY(),0.001);
	}

	@Test
	public void testSetX() {
		v2.setX(100.0);
		assertEquals(100.0,v2.getX(),0.001);
	}

	@Test
	public void testSetY() {
		v2.setY(150.0);
		assertEquals(150.0,v2.getY(),0.001);
	}


}
