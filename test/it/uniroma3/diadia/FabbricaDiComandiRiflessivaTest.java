package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;

class FabbricaDiComandiRiflessivaTest {
	
	private FabbricaDiComandiRiflessiva fabbrica;
	
	@BeforeEach
	void setUp() {
		fabbrica = new FabbricaDiComandiRiflessiva();
	}

	@Test
    void testCostruisciComandoVaiMinuscolo() {
        AbstractComando comandoMinuscolo = fabbrica.costruisciComando("vai");
        assertEquals("ComandoVai", comandoMinuscolo.getClass().getSimpleName());
        
    }
	
	@Test
	void testCostruisciComandoVaiMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("VAI");
		assertEquals("ComandoVai", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoVaiMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("VaI");
		assertEquals("ComandoVai", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoSalutaMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("SALUTA");
		assertEquals("ComandoSaluta", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoSalutaMinuscolo() {
		AbstractComando comandoMinuscolo = fabbrica.costruisciComando("saluta");
		assertEquals("ComandoSaluta", comandoMinuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoSalutaMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("SaLuTa");
		assertEquals("ComandoSaluta", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoRegalaMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("REGALA");
		assertEquals("ComandoRegala", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoRegalaMinuscolo() {
		AbstractComando comandoMinuscolo = fabbrica.costruisciComando("regala");
		assertEquals("ComandoRegala", comandoMinuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoRegalaMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("ReGaLa");
		assertEquals("ComandoRegala", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoPrendiMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("PRENDI");
		assertEquals("ComandoPrendi", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoPrendiMinuscolo() {
		AbstractComando comandoMinuscolo = fabbrica.costruisciComando("prendi");
		assertEquals("ComandoPrendi", comandoMinuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoPrendiMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("PrEnDi");
		assertEquals("ComandoPrendi", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoPosaMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("POSA");
		assertEquals("ComandoPosa", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoPosaMinuscolo() {
		AbstractComando comandoMinuscolo = fabbrica.costruisciComando("posa");
		assertEquals("ComandoPosa", comandoMinuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoPosaMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("PoSa");
		assertEquals("ComandoPosa", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoInteragisciMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("INTERAGISCI");
		assertEquals("ComandoInteragisci", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoInteragisciMinuscolo() {
		AbstractComando comandoMinuscolo = fabbrica.costruisciComando("interagisci");
		assertEquals("ComandoInteragisci", comandoMinuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoInteragisciMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("InTeRaGiScI");
		assertEquals("ComandoInteragisci", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoFineMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("FINE");
		assertEquals("ComandoFine", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoFineMinuscolo() {
		AbstractComando comandoMinuscolo = fabbrica.costruisciComando("fine");
		assertEquals("ComandoFine", comandoMinuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoFineMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("FiNe");
		assertEquals("ComandoFine", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoAiutoMaiuscolo() {
		AbstractComando comandoMaiuscolo = fabbrica.costruisciComando("AIUTO");
		assertEquals("ComandoAiuto", comandoMaiuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoAiutoMinuscolo() {
		AbstractComando comandoMinuscolo = fabbrica.costruisciComando("aiuto");
		assertEquals("ComandoAiuto", comandoMinuscolo.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoAiutoMisto() {
		AbstractComando comandoMisto = fabbrica.costruisciComando("AiUtO");
		assertEquals("ComandoAiuto", comandoMisto.getClass().getSimpleName());
	}
	
	@Test
	void testCostruisciComandoNonValido() {
		AbstractComando comandoNonValido = fabbrica.costruisciComando("abc");
		assertEquals("ComandoNonValido", comandoNonValido.getClass().getSimpleName());
	}
}
