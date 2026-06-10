package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class LabirintoTest {

	private Labirinto labirinto;

	@BeforeEach
	void setUp() {
		labirinto = Labirinto.newBuilder()
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
				.addAdiacenza("Biblioteca", "Atrio", "sud")
				.getLabirinto();
	}

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
		assertSame(labirinto.getStanzaCorrente().getStanzaAdiacente(Direzione.NORD),
				labirinto.getStanzaVincente());
	}

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
		labirinto.setStanzaCorrente(
				labirinto.getStanzaCorrente().getStanzaAdiacente(Direzione.NORD));
		assertEquals("Biblioteca", labirinto.getStanzaCorrente().getNome());
	}

	@Test
	void testGetStanzaCorrente1() {
		assertEquals("Atrio", labirinto.getStanzaCorrente().getNome());
	}

	@Test
	void testGetStanzaCorrente2() {
		labirinto.getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));
		labirinto.setStanzaCorrente(
				labirinto.getStanzaCorrente().getStanzaAdiacente(Direzione.NORD));
		assertEquals("Biblioteca", labirinto.getStanzaCorrente().getNome());
	}

	@Test
	void testGetStanzaCorrente3() {
		labirinto.getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));

		assertSame(labirinto.getStanzaCorrente(),
				labirinto.getStanzaCorrente()
						.getStanzaAdiacente(Direzione.NORD)
						.getStanzaAdiacente(Direzione.SUD));

		assertSame(labirinto.getStanzaCorrente(),
				labirinto.getStanzaCorrente()
						.getStanzaAdiacente(Direzione.SUD)
						.getStanzaAdiacente(Direzione.NORD));

		assertSame(labirinto.getStanzaCorrente(),
				labirinto.getStanzaCorrente()
						.getStanzaAdiacente(Direzione.EST)
						.getStanzaAdiacente(Direzione.OVEST));

		assertSame(labirinto.getStanzaCorrente(),
				labirinto.getStanzaCorrente()
						.getStanzaAdiacente(Direzione.OVEST)
						.getStanzaAdiacente(Direzione.EST));
	}
}