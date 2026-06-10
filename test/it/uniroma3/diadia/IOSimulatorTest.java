package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.AbstractComando;

class IOSimulatorTest {
	
	private IOSimulator io;
	private List<String> comandi;
	private Map<String, List<String>> output;
	
	private static final String MESSAGGIO_BENVENUTO = ""
			+ "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
			+ "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
			+ "I locali sono popolati da strani personaggi, alcuni amici, altri... chissa!\n"
			+ "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
			+ "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
			+ "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
			+ "Per conoscere le istruzioni usa il comando 'aiuto'.";
	
	private void addOutput(String comando, String... messaggi) {
		output.put(comando, new ArrayList<String>(List.of(messaggi)));
	}
	
	@BeforeEach
	void setUp() throws Exception {
		Field campo = AbstractComando.class.getDeclaredField("elencoComandi");
		campo.setAccessible(true);
		@SuppressWarnings("unchecked")
		Set<String> elenco = (Set<String>) campo.get(null);
		elenco.clear();

		io = new IOSimulator();
		comandi = new ArrayList<String>();
		output = new HashMap<String, List<String>>();
	}
	
	@AfterEach
	void play() throws Exception {
		Labirinto labirinto = Labirinto.newBuilder()
			    .addStanzaIniziale("Atrio")
			    .addAttrezzo("osso", 1)
			    .addStanzaBloccata("Aula N11", "est", "chiave")
			    .addStanza("Aula N10")
			    .addAttrezzo("lanterna", 3)
			    .addStanzaBuia("Laboratorio Campus", "lanterna")
			    .addAttrezzo("chiave", 1) 
			    .addStanza("Biblioteca")
			    .addStanzaVincente("Biblioteca")
			    .addAdiacenza("Atrio", "Biblioteca", "nord")
			    .addAdiacenza("Atrio", "Aula N11", "est")
			    .addAdiacenza("Atrio", "Aula N10", "sud")
			    .addAdiacenza("Atrio", "Laboratorio Campus", "ovest")
			    .addAdiacenza("Aula N11", "Laboratorio Campus", "est")
			    .addAdiacenza("Aula N11", "Atrio", "ovest")
			    .addAdiacenza("Aula N10", "Atrio", "nord")
			    .addAdiacenza("Aula N10", "Aula N11", "est")
			    .addAdiacenza("Aula N10", "Laboratorio Campus", "ovest")
			    .addAdiacenza("Laboratorio Campus", "Atrio", "est")
			    .addAdiacenza("Laboratorio Campus", "Aula N11", "ovest")
			    .addAdiacenza("Biblioteca", "Atrio", "sud").getLabirinto();
		io.setComandi(comandi);
		DiaDia gioco = new DiaDia(labirinto, io, 1, 20, 10);
		gioco.gioca();
		assertEquals(output, io.getOutput());
	}
	
	@Test
	void testPartita1() {
		comandi.add("vai ovest");
		comandi.add("prendi chiave");
		comandi.add("vai est");
		comandi.add("vai est");
		comandi.add("posa chiave");
		comandi.add("vai est");
		comandi.add("vai est");
		comandi.add("vai nord");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("vai ovest", "Laboratorio Campus");
		addOutput("prendi chiave", "Attrezzo chiave preso.");
		addOutput("vai est", "Atrio", "Aula N11", "Laboratorio Campus", "Atrio");
		addOutput("posa chiave", "Attrezzo chiave posato.");
		addOutput("vai nord", "Biblioteca", "Hai vinto il gioco completo!");
	}
	
	@Test
	void testPartita2() {
		comandi.add("abc");
		comandi.add("fine");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("abc", "\nNon c'è nessuna azione con questo nome!");
		addOutput("fine", "Grazie di aver giocato!");
	}
	
	@Test
	void testPartita3() {
		comandi.add("guarda");
		comandi.add("vai est");
		comandi.add("vai est");
		comandi.add("fine");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("guarda",
				"",
				"---STANZA CORRENTE---",
				"Atrio\nUscite: nord sud est ovest\nAttrezzi nella stanza: osso (1kg) \nPersonaggio presente: Nessuno",
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
				"---------------------");
		addOutput("vai est", "Aula N11", "Aula N11");
		addOutput("fine", "Grazie di aver giocato!");
	}
	
	@Test
	void testPartita4() {
		comandi.add("vai sud");
		comandi.add("prendi lanterna");
		comandi.add("vai nord");
		comandi.add("fine");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("vai sud", "Aula N10");
		addOutput("prendi lanterna", "Attrezzo lanterna preso.");
		addOutput("vai nord", "Atrio");
		addOutput("fine", "Grazie di aver giocato!");
	}
	
	@Test
	void testPartita5() {
		comandi.add("prendi osso");
		comandi.add("aiuto");
		comandi.add("fine");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("prendi osso", "Attrezzo osso preso.");
		addOutput("aiuto", "", "Azioni disponibili:", "aiuto", "prendi");
		addOutput("fine", "Grazie di aver giocato!");
	}
	
	@Test
	void testPartita6() {
		comandi.add("vai sud");
		comandi.add("vai ovest");
		comandi.add("vai est");
		comandi.add("vai est");
		comandi.add("vai ovest");
		comandi.add("fine");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("vai sud", "Aula N10");
		addOutput("vai ovest", "Laboratorio Campus", "Atrio");
		addOutput("vai est", "Atrio", "Aula N11");
		addOutput("fine", "Grazie di aver giocato!");
	}
	
	@Test
	void testPartita7() {
		comandi.add("vai ovest");
		comandi.add("prendi chiave");
		comandi.add("vai est");
		comandi.add("vai est");
		comandi.add("vai est");
		comandi.add("fine");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("vai ovest", "Laboratorio Campus");
		addOutput("prendi chiave", "Attrezzo chiave preso.");
		addOutput("vai est", "Atrio", "Aula N11", "Aula N11");
		addOutput("fine", "Grazie di aver giocato!");
	}
	
	@Test
	void testPartita8() {
		comandi.add("prendi nonEsiste");
		comandi.add("fine");

		addOutput("inizio", MESSAGGIO_BENVENUTO);
		addOutput("prendi nonEsiste", "Attrezzo non presente nella stanza");
		addOutput("fine", "Grazie di aver giocato!");
	}

}