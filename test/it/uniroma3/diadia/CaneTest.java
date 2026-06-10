package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;
import it.uniroma3.diadia.personaggi.Cane;

public class CaneTest {

    private Cane cane;
    private Partita partita;
    private Giocatore giocatore;

    @BeforeEach
    public void setUp() {
        cane = new Cane("Fido", "Sono un cane feroce!", "bistecca", new Attrezzo("lanterna", 2));

        partita = new Partita(
                Labirinto.newBuilder()
                        .addStanzaIniziale("Atrio")
                        .addStanza("Corridoio")
                        .addStanzaVincente("Biblioteca")
                        .addAdiacenza("Atrio", "Corridoio", "nord")
                        .addAdiacenza("Corridoio", "Atrio", "sud")
                        .addAdiacenza("Corridoio", "Biblioteca", "est")
                        .getLabirinto()
        );

        giocatore = partita.getGiocatore();
    }

    @Test
    public void testAgisci_DecremantaCfu() {
        int cfuIniziali = giocatore.getCfu();
        String messaggio = cane.agisci(partita);
        assertEquals(cfuIniziali - 1, giocatore.getCfu());
        assertEquals("Il cane ti ha morso! Hai perso 1 CFU!", messaggio);
    }

    @Test
    public void testAgisci_MorsoRipetutiDecremanoCfuOgniVolta() {
        int cfuIniziali = giocatore.getCfu();
        String messaggi[] = new String[3];
        messaggi[0] = cane.agisci(partita);
        messaggi[1] = cane.agisci(partita);
        messaggi[2] = cane.agisci(partita);
        assertEquals(cfuIniziali - 3, giocatore.getCfu());
        for (String messaggio : messaggi) {
			assertEquals("Il cane ti ha morso! Hai perso 1 CFU!", messaggio);
		}
    }

    @Test
    public void testAgisci_MorsoSenzaSaluto_DecremantaCfu() {
        assertFalse(cane.haSalutato());
        int cfuIniziali = giocatore.getCfu();
        String messaggio = cane.agisci(partita);
        assertEquals(cfuIniziali - 1, giocatore.getCfu());
        assertEquals("Il cane ti ha morso! Hai perso 1 CFU!", messaggio);
    }

    @Test
    public void testAgisci_MorsoDopoSaluto_DecremantaCfuComunque() {
        cane.saluta();
        assertTrue(cane.haSalutato());
        int cfuIniziali = giocatore.getCfu();
        String messaggio = cane.agisci(partita);
        assertEquals(cfuIniziali - 1, giocatore.getCfu());
        assertEquals("Il cane ti ha morso! Hai perso 1 CFU!", messaggio);
    }

    @Test
    public void testSaluta_PrimaVolta() {
        assertFalse(cane.haSalutato());
        String messaggio = cane.saluta();
        assertTrue(cane.haSalutato());
        assertEquals("Ciao, io sono Fido. Sono un cane feroce!", messaggio);
    }

    @Test
    public void testSaluta_SecondaVolta_ContieneGiaPresentati() {
        cane.saluta();
        String risposta = cane.saluta();
        assertEquals("Ciao, io sono Fido. Ci siamo gia' presentati!", risposta);
    }
    
    @Test
    public void testRiceviRegaloCiboPreferito() {
    	int cfuIniziali = giocatore.getCfu();
		String risposta = cane.riceviRegalo(new Attrezzo("bistecca", 2), partita);
		assertEquals("Il cane scondinzola felice e ti regala un attrezzo lanterna!", risposta);
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("bistecca"));
		assertEquals(cfuIniziali, giocatore.getCfu());
	}
	
	@Test
	public void testRiceviRegaloNonCiboPreferito() {
		int cfuIniziali = giocatore.getCfu();
		String risposta = cane.riceviRegalo(new Attrezzo("pozione", 1), partita);
		assertEquals("Il cane ringhia infastidito e ti morde! Hai perso 1 CFU!", risposta);
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
		assertEquals(cfuIniziali - 1, giocatore.getCfu());
	}
}