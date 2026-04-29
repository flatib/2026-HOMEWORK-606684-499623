package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class IOSimulatorTest {
	
	private IOSimulator io;
	private String[] comandi;
	private String[] output;
	
	private static final String MESSAGGIO_BENVENUTO = ""
			+ "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
			+ "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
			+ "I locali sono popolati da strani personaggi, alcuni amici, altri... chissa!\n"
			+ "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
			+ "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
			+ "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
			+ "Per conoscere le istruzioni usa il comando 'aiuto'.";
	
	@AfterEach
	void setUp() {
		io.setComando(comandi);
		DiaDia gioco = new DiaDia(io);
		gioco.gioca();
		assertArrayEquals(output, io.getOutput());
	}
	
	@Test
	void testPartita1() {
		io = new IOSimulator(7);
		String[] comandi = {"vai ovest", "prendi chiave", "vai est", "posa chiave", "vai nord"};
		String[] output = {MESSAGGIO_BENVENUTO, 
				"Laboratorio Campus", 
				"Attrezzo chiave preso.",
				"Atrio", 
				"Attrezzo chiave posato.", 
				"Biblioteca", 
				"Hai vinto!"};
		this.comandi = comandi;
		this.output = output;
	}
	
	@Test
	void testPartita2() {
		io = new IOSimulator(3);
		String[] comandi = {"abc", "fine"};
		String[] output = {MESSAGGIO_BENVENUTO, 
				"\nNon c'è nessuna azione con questo nome!", 
				"Grazie di aver giocato!"};
		this.comandi = comandi;
		this.output = output;
	}
	
	@Test
	void testPartita3() {
		io = new IOSimulator(19);
		String[] comandi = {"guarda", "vai nord", "fine"};
		String[] output = {MESSAGGIO_BENVENUTO,
				"",
				"---STANZA CORRENTE---",
				"Atrio\nUscite:  nord est sud ovest\nAttrezzi nella stanza: osso (1kg) \nQUESTA È UNA STANZA BLOCCATA",
				"---------------------",
				"",
				"---STANZA VINCENTE---",
				"Biblioteca",
				"---------------------",
				"",
				"---CREDITI RIMANENTI---",
				"20 CFU",
				"---------------------",
				"",
				"---BORSA---",
				"Borsa vuota",
				"---------------------",
				"Atrio", "Grazie di aver giocato!"};
		this.comandi = comandi;
		this.output = output;
	}
	
	@Test
	void testPartita4() {
		io = new IOSimulator(5);
		String[] comandi = {"vai sud", "prendi lanterna", "vai nord", "fine"};
		String[] output = {MESSAGGIO_BENVENUTO,
				"Aula N10",
				"Attrezzo lanterna preso.",
				"Atrio",
				"Grazie di aver giocato!"};
		this.comandi = comandi;
		this.output = output;
	}
	
	@Test
	void testPartita5() {
		io = new IOSimulator(11);
		String[] comandi = {"prendi osso", "aiuto", "fine"};
		String[] output = {MESSAGGIO_BENVENUTO,
				"Attrezzo osso preso.",
				"",
				"Azioni disponibili:",
				"vai ",
				"prendi ",
				"posa ",
				"guarda ",
				"aiuto ",
				"fine ",
				"Grazie di aver giocato!"};
		this.comandi = comandi;
		this.output = output;
	}
	
	@Test
	void testPartita6() {
		io = new IOSimulator(6);
		String[] comandi = {"vai sud", "vai ovest", "vai est", "vai nord", "fine"};
		String[] output = {MESSAGGIO_BENVENUTO,
				"Aula N10",
				"Laboratorio Campus",
				"Atrio",
				"Atrio",
				"Grazie di aver giocato!"};
		this.comandi = comandi;
		this.output = output;
	}
	
	@Test
	void testPartita7() {
		io = new IOSimulator(6);
		String[] comandi = {"vai ovest", "prendi chiave", "vai est", "vai nord", "fine"};
		String[] output = {MESSAGGIO_BENVENUTO,
				"Laboratorio Campus",
				"Attrezzo chiave preso.",
				"Atrio",
				"Atrio",
				"Grazie di aver giocato!"};
		this.comandi = comandi;
		this.output = output;
	}
	
	@Test
	void testPartita8() {
		io = new IOSimulator(3);
		String[] comandi = {"prendi nonEsiste", "fine"};
		String[] output = {MESSAGGIO_BENVENUTO,
				"Attrezzo non presente nella stanza",
				"Grazie di aver giocato!"};
		this.comandi = comandi;
		this.output = output;
	}

}