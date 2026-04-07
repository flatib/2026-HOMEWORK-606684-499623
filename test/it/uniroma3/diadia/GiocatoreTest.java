package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.giocatore.Giocatore;

class GiocatoreTest {

	private Giocatore giocatore = new Giocatore();
	
	@BeforeEach
	void setUp() {
		giocatore = new Giocatore();
	}
	
	@Test
	void testGetBorsa() {
		assertNotNull(giocatore.getBorsa());
	}
	
	@Test
	void testGetCfu() {
		assertEquals(20, giocatore.getCfu());
	}
	
	@Test
	void testSetCfu() {
		giocatore.setCfu(10);
		assertEquals(10, giocatore.getCfu());
	}

}
