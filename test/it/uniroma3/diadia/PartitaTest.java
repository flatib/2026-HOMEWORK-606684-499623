package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class PartitaTest {
	
	private Partita partita;
	
	@BeforeEach
	void setUp() {
		partita = new Partita(Labirinto.newBuilder()
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
			    .addAdiacenza("Biblioteca", "Atrio", "sud").getLabirinto());
	}
	
	//metodo vinta
	
	@Test
	void testVinta1() {
		assertFalse(partita.vinta());
	}
	
	@Test
	void testVinta2() {
		partita.setCurrentLevel(5);
		partita.getLabirinto().setStanzaCorrente(partita.getLabirinto().getStanzaVincente());
		assertTrue(partita.vinta());
	}
	
	@Test
	void testVinta3() {
		partita.setCurrentLevel(5);
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));
		partita.getLabirinto().setStanzaCorrente(partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(Direzione.NORD));
		assertTrue(partita.vinta());
	}
	
	//metodo isFinita
	
	@Test
	void testIsFinita1() {
		assertFalse(partita.isFinita());
	}
	
	@Test
	void testIsFinita2() {
		partita.setCurrentLevel(5);
		partita.getLabirinto().setStanzaCorrente(partita.getLabirinto().getStanzaVincente());
		assertTrue(partita.isFinita());
	}
	
	@Test
	void testIsFinita3() {
		partita.setCurrentLevel(5);
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("chiave", 1));
		partita.getLabirinto().setStanzaCorrente(partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(Direzione.NORD));
		assertTrue(partita.isFinita());
	}

}
