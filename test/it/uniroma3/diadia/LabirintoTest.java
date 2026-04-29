package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class LabirintoTest {
	
	private Labirinto labirinto = new Labirinto();
	
	@BeforeEach
	void setUp() {
		labirinto = new Labirinto();
	}

	//metodo getStanzaVincente
	
		@Test
		void testGetStanzaVincente1() {
			assertEquals("Biblioteca", labirinto.getStanzaVincente().getNome());
		}
		
		@Test
		void testGetStanzaVincente2() {
			assertNotEquals("AulaN11", labirinto.getStanzaVincente().getNome());
		}
		
		@Test
		void testGetStanzaVincente3() {
			labirinto.getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));
			assertSame(labirinto.getStanzaCorrente().getStanzaAdiacente("nord"), labirinto.getStanzaVincente());
		}
		
		//metodo setStanzaCorrente
		
		@Test
		void testSetStanzaCorrente1() {
			labirinto.setStanzaCorrente(new Stanza("Aula DS2"));
			assertEquals("Aula DS2", labirinto.getStanzaCorrente().getNome());
		}
		
		@Test
		void testSetStanzaCorrente2() {
			labirinto.setStanzaCorrente(labirinto.getStanzaCorrente());
			assertEquals("Atrio", labirinto.getStanzaCorrente().getNome());
		}
		
		@Test
		void testSetStanzaCorrente3() {
			labirinto.getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));
			labirinto.setStanzaCorrente(labirinto.getStanzaCorrente().getStanzaAdiacente("nord"));
			assertEquals("Biblioteca", labirinto.getStanzaCorrente().getNome());
		}
		
		//metodo getStanzaCorrente
		
		@Test
		void testGetStanzaCorrente1() {
			assertEquals("Atrio", labirinto.getStanzaCorrente().getNome());
		}
		
		@Test
		void testGetStanzaCorrente2() {
			labirinto.getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));
			labirinto.setStanzaCorrente(labirinto.getStanzaCorrente().getStanzaAdiacente("nord"));
			assertEquals("Biblioteca", labirinto.getStanzaCorrente().getNome());
		}
		
		@Test
		void testGetStanzaCorrente3() {
			labirinto.getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));
			assertSame(labirinto.getStanzaCorrente(), labirinto.getStanzaCorrente().
					getStanzaAdiacente("nord").getStanzaAdiacente("sud"));
			assertSame(labirinto.getStanzaCorrente(), labirinto.getStanzaCorrente().
					getStanzaAdiacente("sud").getStanzaAdiacente("nord"));
			assertSame(labirinto.getStanzaCorrente(), labirinto.getStanzaCorrente().
					getStanzaAdiacente("est").getStanzaAdiacente("ovest"));
			assertSame(labirinto.getStanzaCorrente(), labirinto.getStanzaCorrente().
					getStanzaAdiacente("ovest").getStanzaAdiacente("est"));
		}

}
