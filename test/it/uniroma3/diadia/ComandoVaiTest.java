package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.ComandoVai;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.comandi.Comando;

class ComandoVaiTest {

	private Comando comandoVai;
	private Partita partita;
	private Stanza atrio;
	private Stanza n11;
	
	@BeforeEach
	void setUp() {
		comandoVai = new ComandoVai(new IOConsole());
		partita = new Partita();
		atrio = new Stanza("atrio");
		n11 = new Stanza("N11");
		partita.setStanzaCorrente(atrio);
		atrio.impostaStanzaAdiacente("nord", n11);
		n11.impostaStanzaAdiacente("sud", n11);
	}
	
	@Test
	void testEseguiSuccessfull() {
		comandoVai.setParametro("nord");
		comandoVai.esegui(partita);
		assertSame(n11, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiDirezioneNull() {
		comandoVai.esegui(partita);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiDirezioneBlank() {
		comandoVai.setParametro("");
		comandoVai.esegui(partita);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiDirezioneInesistente() {
		comandoVai.setParametro("est");
		comandoVai.esegui(partita);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	void testEseguiInputErrato() {
		comandoVai.setParametro("abc");
		comandoVai.esegui(partita);
		assertSame(atrio, partita.getLabirinto().getStanzaCorrente());
	}

}
