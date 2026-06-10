package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.AbstractComando;

class AbstractComandoTest {

	private static class FakeComando extends AbstractComando {
		@Override
		public void esegui(Partita partita, IO io) {
		}
	}

	@Test
	void testSetParametroDiDefaultNonLanciaEccezioni() {
		AbstractComando comando = new FakeComando();
		assertDoesNotThrow(() -> comando.setParametro("parametro"));
	}

	@Test
	void testSetParametroDiDefaultAccettaNull() {
		AbstractComando comando = new FakeComando();
		assertDoesNotThrow(() -> comando.setParametro(null));
	}

}
