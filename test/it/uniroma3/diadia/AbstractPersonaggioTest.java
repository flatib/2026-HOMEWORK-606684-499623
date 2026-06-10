package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

class AbstractPersonaggioTest {

	public class FakePersonaggio extends AbstractPersonaggio {
		public FakePersonaggio(String nome, String presentazione) {
			super(nome, presentazione);
		}

		@Override
		public String agisci(Partita partita) {
			return "done";
		}

		@Override
		public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
			return "done";
		}
		
	}
	
	@Test
	void testAgisciDiDefaultNonLanciaEccezioni() {
		AbstractPersonaggio personaggio = new FakePersonaggio("nome", "presentazione");
		assertDoesNotThrow(() -> personaggio.agisci(null));
	}
	
	@Test
	void testAgisciDiDefaultRestituisceStringaNonNull() {
		AbstractPersonaggio personaggio = new FakePersonaggio("nome", "presentazione");
		assertDoesNotThrow(() -> personaggio.agisci(null));
	}

}
