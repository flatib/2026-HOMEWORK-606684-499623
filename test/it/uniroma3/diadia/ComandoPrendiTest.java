package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.ComandoPrendi;

class ComandoPrendiTest {

	private Comando comandoPrendi;
	private Partita partita;
	
	@BeforeEach
	void setUp() {
		comandoPrendi = new ComandoPrendi(new IOConsole());
		partita = new Partita();
		partita.setStanzaCorrente(new Stanza("atrio"));
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("lanterna", 1));
		partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("osso", 1));
	}
	
	@Test
	void testEseguiNull() {
		comandoPrendi.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiBlank() {
		comandoPrendi.setParametro("");
		comandoPrendi.esegui(partita);
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo(""));
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo(""));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiNonEsistente() {
		comandoPrendi.setParametro("tablet");
		comandoPrendi.esegui(partita);
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("tablet"));
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("tablet"));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiBorsaPienaNumero() {
		for(int i = 1; i < 10; i++) {
			partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("attrezzo" + i, 1));
		}
		comandoPrendi.setParametro("lanterna");
		comandoPrendi.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiBorsaPienaPeso() {
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("camion", 10));
		comandoPrendi.setParametro("camion");
		comandoPrendi.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
		assertNull(partita.getGiocatore().getBorsa().getAttrezzo("camion"));
	}
	
	@Test
	void testEseguiSuccess() {
		comandoPrendi.setParametro("lanterna");
		comandoPrendi.esegui(partita);
		assertEquals("lanterna", partita.getGiocatore().getBorsa().getAttrezzo("lanterna").getNome());
		assertNull(partita.getLabirinto().getStanzaCorrente().getAttrezzo("lanterna"));
	}

}
