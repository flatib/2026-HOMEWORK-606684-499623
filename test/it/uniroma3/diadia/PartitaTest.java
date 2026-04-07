package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartitaTest {
	
	private Partita partita;
	
	@BeforeEach
	void setUp() {
		partita = new Partita();
	}
	
	//metodo vinta
	
	@Test
	void testVinta1() {
		assertFalse(partita.vinta());
	}
	
	@Test
	void testVinta2() {
		partita.setStanzaCorrente(partita.getStanzaVincente());
		assertTrue(partita.vinta());
	}
	
	@Test
	void testVinta3() {
		partita.setStanzaCorrente(partita.getStanzaCorrente().getStanzaAdiacente("nord"));
		assertTrue(partita.vinta());
	}
	
	//metodo isFinita
	
	@Test
	void testIsFinita1() {
		assertFalse(partita.isFinita());
	}
	
	@Test
	void testIsFinita2() {
		partita.setStanzaCorrente(partita.getStanzaVincente());
		assertTrue(partita.isFinita());
	}
	
	@Test
	void testIsFinita3() {
		partita.setStanzaCorrente(partita.getStanzaCorrente().getStanzaAdiacente("nord"));
		assertTrue(partita.isFinita());
	}

}
