package fr.needforcode.tests;

import static org.junit.Assert.*;

import org.junit.*;

import fr.needforcode.voiture.*;
import fr.needforcode.geometrie.*;

public class VoitureImplTest {
	
	VoitureImpl maVoitureImpl;
	
	@Before
	public void setUp1() throws Exception {
		maVoitureImpl = new VoitureImpl(0.5, 0.6, 2.3, 2.1, 3.5, 0.5, 0.4, new Vecteur(1,2), new Vecteur (1,3), 0.2 );
		System.out.println(maVoitureImpl.getMaxTurnSansDerapage());
		System.out.println(maVoitureImpl.getBraquage());
		System.out.println(maVoitureImpl.getDirection().toString());
		System.out.println(maVoitureImpl.getPosition().toString());
		System.out.println(maVoitureImpl.getVitesse());
		System.out.println(maVoitureImpl.getDerapage());
	}
	
	@After
	public void tearDown() throws Exception {
		maVoitureImpl = null;
	}
	
	@Test
	public void TestgetMaxTurnSansDerapage() {

	}

	@Test
	public void TestGetVitesse() {
		
	}

	@Test
	public void TestGetPosition() {
		
	}

	@Test
	public void TestGetDirection() {
		
	}

	@Test
	public void TestGetDerapage() {
		
	}

	@Test
	public void TestGetBraquage() {
		
	}
	

}
