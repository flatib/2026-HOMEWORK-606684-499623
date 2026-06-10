package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.ComandoPrendi;

class ComandoPrendiTest {

	private AbstractComando comandoPrendi;
	private Partita partita;
	private IO io;
	
	@BeforeEach
	void setUp() {
		comandoPrendi = new ComandoPrendi();
		io = new IOConsole(new Scanner(System.in));
		partita = new Partita(Labirinto.newBuilder().getLabirinto());
		partita.getLabirinto().setStanzaCorrente(new Stanza("atrio"));
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("lanterna", 1));
		partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("osso", 1));
	}
	
	@Test
	void testEseguiNull() {
		comandoPrendi.esegui(partita, io);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiBlank() {
		comandoPrendi.setParametro("");
		comandoPrendi.esegui(partita, io);
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo(""));
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo(""));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiNonEsistente() {
		comandoPrendi.setParametro("tablet");
		comandoPrendi.esegui(partita, io);
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
		comandoPrendi.esegui(partita, io);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiBorsaPienaPeso() {
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("camion", 10));
		comandoPrendi.setParametro("camion");
		comandoPrendi.esegui(partita, io);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
		assertNull(partita.getGiocatore().getBorsa().getAttrezzo("camion"));
	}
	
	@Test
	void testEseguiSuccess() {
		comandoPrendi.setParametro("lanterna");
		comandoPrendi.esegui(partita, io);
		assertEquals("lanterna", partita.getGiocatore().getBorsa().getAttrezzo("lanterna").getNome());
		assertNull(partita.getLabirinto().getStanzaCorrente().getAttrezzo("lanterna"));
	}

}
