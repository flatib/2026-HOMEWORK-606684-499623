package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.ComandoPosa;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Comando;

class ComandoPosaTest {
	
	private Comando comandoPosa;
	private Partita partita;

	@BeforeEach
	void setUp() {
		comandoPosa = new ComandoPosa(new IOConsole());
		partita = new Partita();
		partita.setStanzaCorrente(new Stanza("atrio"));
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("lanterna", 1));
		partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("osso", 1));
	}
	
	@Test
	void testEseguiNull() {
		comandoPosa.esegui(partita);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiBlank() {
		comandoPosa.setParametro("");
		comandoPosa.esegui(partita);
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo(""));
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo(""));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiNonEsistente() {
		comandoPosa.setParametro("tablet");
		comandoPosa.esegui(partita);
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("tablet"));
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("tablet"));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiStanzaPiena() {
		for(int i = 1; i < 10; ++i) {
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("attrezzo" + i, 1));
		}
		comandoPosa.setParametro("osso");
		comandoPosa.esegui(partita);
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("osso"));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiSuccess() {
		comandoPosa.setParametro("osso");
		comandoPosa.esegui(partita);
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("osso"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
	}
}
