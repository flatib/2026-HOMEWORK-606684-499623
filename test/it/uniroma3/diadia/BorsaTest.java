package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

class BorsaTest {

	private Borsa borsa = new Borsa();
	
	@BeforeEach
	void setUp() {
		borsa = new Borsa();
	}
	
	// addAttrezzo method
	
	@Test
	void testAddAttrezzo1() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		assertTrue(borsa.addAttrezzo(attrezzo));
	}
	
	@Test
	void testAddAttrezzo2() {
		for(int i=0; i<10; i++) {
			Attrezzo attrezzo = new Attrezzo("attrezzo"+i, 1);
			borsa.addAttrezzo(attrezzo);
		}
		assertFalse(borsa.addAttrezzo(new Attrezzo("attrezzo10", 1)));
	}
	
	@Test
	void testAddAttrezzo3() {
		for(int i=0; i<5; i++) {
			Attrezzo attrezzo = new Attrezzo("attrezzo" + i, 2);
			borsa.addAttrezzo(attrezzo);
		}
		assertFalse(borsa.addAttrezzo(new Attrezzo("attrezzo5", 1)));
	}
	
	// hasAttrezzo method
	
	@Test
	void testHasAttrezzo1() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		borsa.addAttrezzo(attrezzo);
		assertTrue(borsa.hasAttrezzo("martello"));
	}
	
	@Test
	void testHasAttrezzo2() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		borsa.addAttrezzo(attrezzo);
		assertFalse(borsa.hasAttrezzo("cacciavite"));
	}
	
	@Test
	void testHasAttrezzo3() {
		assertFalse(borsa.hasAttrezzo("martello"));
	}
	
	// getAttrezzo method
	
	@Test
	void testGetAttrezzo1() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		borsa.addAttrezzo(attrezzo);
		assertSame(attrezzo, borsa.getAttrezzo("martello"));
	}
	
	@Test
	void testGetAttrezzo2() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		borsa.addAttrezzo(attrezzo);
		assertNull(borsa.getAttrezzo("cacciavite"));
	}
	
	@Test
	void testGetAttrezzo3() {
		assertNull(borsa.getAttrezzo("martello"));
	}
	
	// removeAttrezzo method
	
	@Test
	void testRemoveAttrezzo1() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		borsa.addAttrezzo(attrezzo);
		assertSame(attrezzo, borsa.removeAttrezzo("martello"));
	}
	
	@Test
	void testRemoveAttrezzo2() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		borsa.addAttrezzo(attrezzo);
		assertNull(borsa.removeAttrezzo("cacciavite"));
	}
	
	@Test
	void testRemoveAttrezzo3() {
		assertNull(borsa.removeAttrezzo("martello"));
	}
	
	// toString method
	
	@Test
	void testToString1() {
		assertEquals("Borsa vuota", borsa.toString());
	}
	
	@Test
	void testToString2() {
		Attrezzo attrezzo = new Attrezzo("martello", 3);
		borsa.addAttrezzo(attrezzo);
		assertEquals("Contenuto borsa (3kg/10kg): martello (3kg) ", borsa.toString());
	}
	
	@Test
	void testToString3() {
		Attrezzo attrezzo1 = new Attrezzo("martello", 3);
		Attrezzo attrezzo2 = new Attrezzo("cacciavite", 2);
		borsa.addAttrezzo(attrezzo1);
		borsa.addAttrezzo(attrezzo2);
		assertEquals("Contenuto borsa (5kg/10kg): martello (3kg) cacciavite (2kg) ", borsa.toString());
	}
	
}
