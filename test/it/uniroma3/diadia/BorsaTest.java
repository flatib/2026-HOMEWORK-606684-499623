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
		assertEquals(attrezzo.getNome(), borsa.removeAttrezzo("martello").getNome());
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
	
	// getContenutoOrdinatoPerPeso method
	
	@Test
	void testGetContenutoOrdinatoPerPeso() {
		Attrezzo attrezzo1 = new Attrezzo("martello", 3);
		Attrezzo attrezzo2 = new Attrezzo("cacciavite", 2);
		borsa.addAttrezzo(attrezzo1);
		borsa.addAttrezzo(attrezzo2);
		assertEquals(attrezzo2, borsa.getContenutoOrdinatoPerPeso().get(0));
		assertEquals(attrezzo1, borsa.getContenutoOrdinatoPerPeso().get(1));
	}
	
	@Test
	void testGetContenutoOrdinatoPerPesoStessoPeso() {
		Attrezzo attrezzo1 = new Attrezzo("martello", 3);
		Attrezzo attrezzo2 = new Attrezzo("cacciavite", 3);
		borsa.addAttrezzo(attrezzo1);
		borsa.addAttrezzo(attrezzo2);
		assertEquals(attrezzo2, borsa.getContenutoOrdinatoPerPeso().get(0));
		assertEquals(attrezzo1, borsa.getContenutoOrdinatoPerPeso().get(1));
	}
	
	
	// getContenutoOrdinatoPerNome method
	
	@Test
	void testGetContenutoOrdinatoPerNome() {
		Attrezzo attrezzo1 = new Attrezzo("martello", 3);
		Attrezzo attrezzo2 = new Attrezzo("cacciavite", 2);
		borsa.addAttrezzo(attrezzo1);
		borsa.addAttrezzo(attrezzo2);
		assertEquals(attrezzo1, borsa.getContenutoOrdinatoPerNome().last());
		assertEquals(attrezzo2, borsa.getContenutoOrdinatoPerNome().first());
	}
	
	// getContenutoRaggruppatoPerPeso method
	
	@Test
	void testGetContenutoRaggruppatoPerPeso() {
		Attrezzo attrezzo1 = new Attrezzo("martello", 3);
		Attrezzo attrezzo2 = new Attrezzo("cacciavite", 2);
		Attrezzo attrezzo3 = new Attrezzo("osso", 3);
		Attrezzo attrezzo4 = new Attrezzo("chiave inglese", 1);
		Attrezzo attrezzo5 = new Attrezzo("chiave a brugola", 1);
		borsa.addAttrezzo(attrezzo1);
		borsa.addAttrezzo(attrezzo2);
		borsa.addAttrezzo(attrezzo3);
		borsa.addAttrezzo(attrezzo4);
		borsa.addAttrezzo(attrezzo5);
		assertTrue(borsa.getContenutoRaggruppatoPerPeso().get(3).contains(attrezzo1));
		assertTrue(borsa.getContenutoRaggruppatoPerPeso().get(3).contains(attrezzo3));
		assertTrue(borsa.getContenutoRaggruppatoPerPeso().get(2).contains(attrezzo2));
		assertTrue(borsa.getContenutoRaggruppatoPerPeso().get(1).contains(attrezzo4));
		assertTrue(borsa.getContenutoRaggruppatoPerPeso().get(1).contains(attrezzo5));
		assertFalse(borsa.getContenutoRaggruppatoPerPeso().get(3).contains(attrezzo2));
		assertFalse(borsa.getContenutoRaggruppatoPerPeso().get(2).contains(attrezzo1));
	}
	
	// getSortedSetOrdinatoPerPeso method
	
	@Test
	void testGetSortedSetOrdinatoPerPeso() {
		Attrezzo attrezzo1 = new Attrezzo("martello", 3);
		Attrezzo attrezzo2 = new Attrezzo("cacciavite", 2);
		borsa.addAttrezzo(attrezzo1);
		borsa.addAttrezzo(attrezzo2);
		assertEquals(attrezzo2, borsa.getSortedSetOrdinatoPerPeso().first());
		assertEquals(attrezzo1, borsa.getSortedSetOrdinatoPerPeso().last());
	}
	
	@Test
	void testGetSortedSetOrdinatoPerPesoStessoPeso() {
		Attrezzo attrezzo1 = new Attrezzo("martello", 3);
		Attrezzo attrezzo2 = new Attrezzo("cacciavite", 3);
		borsa.addAttrezzo(attrezzo1);
		borsa.addAttrezzo(attrezzo2);
		assertEquals(attrezzo2, borsa.getSortedSetOrdinatoPerPeso().first());
		assertEquals(attrezzo1, borsa.getSortedSetOrdinatoPerPeso().last());
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
