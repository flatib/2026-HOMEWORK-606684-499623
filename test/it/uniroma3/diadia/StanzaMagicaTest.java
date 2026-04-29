package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaMagica;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaMagicaTest {

	private Stanza atrio;
	private Stanza N11;
	private Stanza biblioteca;
	private StanzaMagica stanzaMagica;
	private Attrezzo lanterna;

	@BeforeEach
	void setUp() {
		atrio = new Stanza("Atrio");
		N11 = new Stanza("N11");
		biblioteca = new Stanza("Biblioteca");
		stanzaMagica = new StanzaMagica("Stanza Magica");
		lanterna = new Attrezzo("lanterna", 1);
	}

	// metodo getStanzaAdiacente

	@Test
	void testGetStanzaAdiacenteVuota() {
		assertNull(atrio.getStanzaAdiacente("nord"));
	}

	@Test
	void testImpostaStanzaAdiacente() {
		atrio.impostaStanzaAdiacente("nord", N11);
		assertEquals(N11, atrio.getStanzaAdiacente("nord"));
	}

	@Test
	void testImpostaStanzaAdicenteSovrascritta() {
		atrio.impostaStanzaAdiacente("nord", N11);
		atrio.impostaStanzaAdiacente("nord", biblioteca);
		assertSame(biblioteca, atrio.getStanzaAdiacente("nord"));
		assertArrayEquals(new String[] { "nord" }, atrio.getDirezioni());
	}
	
	// metodo getDirezioni

	@Test
	void testGetDirezioniVuote() {
		assertArrayEquals(new String[] {}, atrio.getDirezioni());
	}

	@Test
	void testGetDirezioniUna() {
		atrio.impostaStanzaAdiacente("nord", N11);
		assertArrayEquals(new String[] { "nord" }, atrio.getDirezioni());
	}

	@Test
	void testGetDirezioniDue() {
		atrio.impostaStanzaAdiacente("nord", N11);
		atrio.impostaStanzaAdiacente("est", biblioteca);
		assertArrayEquals(new String[] { "nord", "est" }, atrio.getDirezioni());
	}

	// metodo addAttrezzo
	@Test
	void testAddAttrezzo() {
		assertTrue(atrio.addAttrezzo(lanterna));
	}

	@Test
	void testAddAttrezzoAlMassimo() {
		for (int i = 0; i < 10; i++)
			assertTrue(atrio.addAttrezzo(new Attrezzo("attrezzo" + i, i + 1)));
	}

	@Test
	void testAddAttrezzoOltreMassimo() {
		for (int i = 0; i < 10; i++)
			atrio.addAttrezzo(new Attrezzo("attrezzo" + i, i + 1));
		assertFalse(atrio.addAttrezzo(lanterna));
	}
	
	// metodo hasAttrezzo

	@Test
	void testHasAttrezzoPresente() {
		atrio.addAttrezzo(lanterna);
		assertTrue(atrio.hasAttrezzo("lanterna"));
	}

	@Test
	void testHasAttrezzoAssente() {
		atrio.addAttrezzo(lanterna);
		assertFalse(atrio.hasAttrezzo("osso"));
	}

	@Test
	void testHasAttrezzoStanzaVuota() {
		assertFalse(atrio.hasAttrezzo("lanterna"));
	}
	
	// metodo getAttrezzo

	@Test
	void testGetAttrezzoPresente() {
		atrio.addAttrezzo(lanterna);
		assertSame(lanterna, atrio.getAttrezzo("lanterna"));
	}

	@Test
	void testGetAttrezzoAssente() {
		atrio.addAttrezzo(lanterna);
		assertNull(atrio.getAttrezzo("osso"));
	}

	@Test
	void testGetAttrezzoStanzaVuota() {
		assertNull(atrio.getAttrezzo("lanterna"));
	}
	
	// metodo removeAttrezzo
	
	@Test
	void testRemoveAttrezzoPresente() {
		atrio.addAttrezzo(lanterna);
		assertTrue(atrio.removeAttrezzo(lanterna));
	}
	
	@Test
	void testRemoveAttrezzoAssente() {
		atrio.addAttrezzo(lanterna);
		assertFalse(atrio.removeAttrezzo(new Attrezzo("osso", 1)));
	}
	
	@Test
	void testRemoveAttrezzoStanzaVuota() {
		assertFalse(atrio.removeAttrezzo(lanterna));
	}
	
	@Test
	void testToString() {
	
		assertEquals("Atrio\nUscite: \nAttrezzi nella stanza: ", atrio.toString());
		atrio.impostaStanzaAdiacente("nord", N11);
		atrio.addAttrezzo(lanterna);
		assertEquals("Atrio\nUscite:  nord\nAttrezzi nella stanza: lanterna (1kg) ", atrio.toString());
	}
	
	// metodi magici
	
	@Test
	void testAddAttrezzoMagico() {
		stanzaMagica.addAttrezzo(lanterna);
		assertFalse(stanzaMagica.hasAttrezzo("anretnal"));
		assertTrue(stanzaMagica.hasAttrezzo("lanterna"));
		assertEquals(1, stanzaMagica.getAttrezzo("lanterna").getPeso());
	}
	
	@Test
	void testAddAttrezzoMagicoSogliaSuperata() {
		for(int i = 0; i < 3; ++i) {
			stanzaMagica.addAttrezzo(new Attrezzo("attrezzo" + i, 1));
		}
		stanzaMagica.addAttrezzo(lanterna);
		assertTrue(stanzaMagica.hasAttrezzo("anretnal"));
		assertFalse(stanzaMagica.hasAttrezzo("lanterna"));
		assertEquals(2, stanzaMagica.getAttrezzo("anretnal").getPeso());
	}

}
