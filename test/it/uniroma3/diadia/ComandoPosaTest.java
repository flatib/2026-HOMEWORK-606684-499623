package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.ComandoPosa;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.AbstractComando;

class ComandoPosaTest {
	
	private AbstractComando comandoPosa;
	private Partita partita;
	private IO io;

	@BeforeEach
	void setUp() {
		comandoPosa = new ComandoPosa();
		io = new IOConsole(new Scanner(System.in));
		partita = new Partita(Labirinto.newBuilder().getLabirinto());
		partita.getLabirinto().setStanzaCorrente(new Stanza("atrio"));
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("lanterna", 1));
		partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("osso", 1));
	}
	
	@Test
	void testEseguiNull() {
		comandoPosa.esegui(partita, io);
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiBlank() {
		comandoPosa.setParametro("");
		comandoPosa.esegui(partita, io);
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo(""));
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo(""));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiNonEsistente() {
		comandoPosa.setParametro("tablet");
		comandoPosa.esegui(partita, io);
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
		comandoPosa.esegui(partita, io);
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("osso"));
		assertTrue(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
	}
	
	@Test
	void testEseguiSuccess() {
		comandoPosa.setParametro("osso");
		comandoPosa.esegui(partita, io);
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("osso"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("osso"));
	}
}
