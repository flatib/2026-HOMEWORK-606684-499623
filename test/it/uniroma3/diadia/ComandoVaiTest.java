package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.ComandoVai;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;

class ComandoVaiTest {

	private AbstractComando comandoVai;
	private Partita partita;
	private Stanza atrio;
	private Stanza n11;
	private IO io;
	
	@BeforeEach
	void setUp() {
		io = new IOConsole(new Scanner(System.in));
		comandoVai = new ComandoVai();
		partita = new Partita(Labirinto.newBuilder().getLabirinto());
		atrio = new Stanza("atrio");
		n11 = new Stanza("N11");
		partita.getLabirinto().setStanzaCorrente(atrio);
		atrio.impostaStanzaAdiacente(Direzione.NORD, n11);
		n11.impostaStanzaAdiacente(Direzione.SUD, n11);
	}
	
	@Test
	void testEseguiSuccessful() {
		comandoVai.setParametro("nord");
		comandoVai.esegui(partita, io);
		assertSame(n11, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiDirezioneNull() {
		comandoVai.esegui(partita, io);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiDirezioneBlank() {
		comandoVai.setParametro("");
		comandoVai.esegui(partita, io);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiDirezioneInesistente() {
		comandoVai.setParametro("est");
		comandoVai.esegui(partita, io);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiInputErrato() {
		comandoVai.setParametro("abc");
		comandoVai.esegui(partita, io);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}

}
