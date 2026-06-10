package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Mago;

public class MagoTest {

    private Mago magoConAttrezzo;
    private Mago magoSenzaAttrezzo;
    private Partita partita;
    private Attrezzo bacchetta;
    private Stanza stanzaCorrente;

    @BeforeEach
    public void setUp() {
        bacchetta = new Attrezzo("bacchetta", 1);
        magoConAttrezzo = new Mago("Silente", "Sono il preside di Hogwarts!", bacchetta);
        magoSenzaAttrezzo = new Mago("Gandalf", "Un mago non è mai in ritardo.", null);

        partita = new Partita(
                Labirinto.newBuilder()
                        .addStanzaIniziale("Atrio")
                        .addStanza("Biblioteca")
                        .addStanzaVincente("Uscita")
                        .addAdiacenza("Atrio", "Biblioteca", "nord")
                        .addAdiacenza("Biblioteca", "Atrio", "sud")
                        .addAdiacenza("Biblioteca", "Uscita", "est")
                        .getLabirinto()
        );

        partita.getLabirinto().setStanzaCorrente(
                partita.getLabirinto().getStanze().get("Atrio")
        );

        stanzaCorrente = partita.getLabirinto().getStanzaCorrente();
    }

    @Test
    public void testAgisci_ConAttrezzo_AggiungeAttrezzoAllaStanza() {
        assertFalse(stanzaCorrente.hasAttrezzo("bacchetta"));
        magoConAttrezzo.agisci(partita);
        assertTrue(stanzaCorrente.hasAttrezzo("bacchetta"));
    }

    @Test
    public void testAgisci_ConAttrezzo_RestituisceMessaggioDono() {
        String risultato = magoConAttrezzo.agisci(partita);
        assertNotNull(risultato);
        assertFalse(risultato.isBlank());
    }

    @Test
    public void testAgisci_ConAttrezzo_AttrezzoNulloDopoIlDono() {
        magoConAttrezzo.agisci(partita);
        int sizeDopoUnaDonazione = stanzaCorrente.getAttrezzi().size();
        magoConAttrezzo.agisci(partita);
        assertEquals(sizeDopoUnaDonazione, stanzaCorrente.getAttrezzi().size());
    }

    @Test
    public void testAgisci_ConAttrezzo_SecondaChiamataRestituisceScuse() {
        magoConAttrezzo.agisci(partita);
        String risultato = magoConAttrezzo.agisci(partita);
        assertNotNull(risultato);
        assertFalse(risultato.isBlank());
    }

    @Test
    public void testAgisci_SenzaAttrezzo_NonAggiungeNullaAllaStanza() {
        int sizePrima = stanzaCorrente.getAttrezzi().size();
        magoSenzaAttrezzo.agisci(partita);
        assertEquals(sizePrima, stanzaCorrente.getAttrezzi().size());
    }

    @Test
    public void testAgisci_SenzaAttrezzo_RestituisceMessaggioScuse() {
        String risultato = magoSenzaAttrezzo.agisci(partita);
        assertNotNull(risultato);
        assertFalse(risultato.isBlank());
    }

    @Test
    public void testSaluta_PrimaVolta_haSalutatoDiventaTrue() {
        assertFalse(magoConAttrezzo.haSalutato());
        magoConAttrezzo.saluta();
        assertTrue(magoConAttrezzo.haSalutato());
    }

    @Test
    public void testSaluta_PrimaVolta_ContienePresentazione() {
        String risposta = magoConAttrezzo.saluta();
        assertTrue(risposta.contains("Silente"));
    }

    @Test
    public void testSaluta_SecondaVolta_ContieneGiaPresentati() {
        magoConAttrezzo.saluta();
        String risposta = magoConAttrezzo.saluta();
        assertTrue(risposta.contains("presentati") || risposta.contains("Presentati"));
    }

    @Test
    public void testCostruttore_AttrezzoNonNullo_AgisciDona() {
        Mago mago = new Mago("Merlino", "Saggio antico", new Attrezzo("spada", 5));
        mago.agisci(partita);
        assertTrue(stanzaCorrente.hasAttrezzo("spada"));
    }
    
    @Test
    public void testRiceviRegalo_magoConAttrezzo() {
    	String messaggio = magoConAttrezzo.riceviRegalo(new Attrezzo("incudine", 6), partita);
    	assertEquals("Grazie per il regalo, ora ne dimezzerò il peso!", messaggio);
    	assertTrue(stanzaCorrente.hasAttrezzo("incudine"));
    	assertTrue(stanzaCorrente.getAttrezzo("incudine").getPeso() == 3);
    }
}