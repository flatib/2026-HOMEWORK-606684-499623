package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Strega;

public class StregaTest {

    private Strega strega;
    private Partita partita;
    private Labirinto labirinto;

    @BeforeEach
    public void setUp() {
        this.labirinto = Labirinto.newBuilder()
                .addStanzaIniziale("Ingresso")
                .addStanza("StanzaStrega")
                .addStrega("Morgana", "Sono la strega più potente del mondo!")
                .addStanza("StanzaPiena")
                .addAttrezzo("osso", 1)
                .addAttrezzo("lanterna", 2)
                .addStanza("StanzaVuota")
                .addStanzaVincente("Biblioteca")
                .addAttrezzo("libro", 1)
                .addAdiacenza("Ingresso", "StanzaStrega", "nord")
                .addAdiacenza("StanzaStrega", "Ingresso", "sud")
                .addAdiacenza("StanzaStrega", "StanzaPiena", "est")
                .addAdiacenza("StanzaPiena", "StanzaStrega", "ovest")
                .addAdiacenza("StanzaStrega", "StanzaVuota", "nord")
                .addAdiacenza("StanzaVuota", "StanzaStrega", "sud")
                .getLabirinto();

        this.partita = new Partita(this.labirinto);
        this.partita.getLabirinto().setStanzaCorrente(
                this.partita.getLabirinto().getStanze().get("StanzaStrega")
        );

        this.strega = (Strega) this.partita.getLabirinto()
                .getStanzaCorrente()
                .getPersonaggio();
    }

    @Test
    public void testAgisci_SenzaSaluto_SpostaNellaStanzaConMenoAttrezzi() {
        String messaggio = strega.agisci(partita);
        assertFalse(strega.haSalutato());
        assertEquals("StanzaVuota", partita.getLabirinto().getStanzaCorrente().getNome());
        assertEquals("Non mi hai salutato, ora ti sposterò nella stanza con meno attrezzi!", messaggio);
    }

    @Test
    public void testAgisci_DopoSaluto_SpostaNellaStanzaConPiuAttrezzi() {
        String risposta1 = strega.saluta();
        String risposta2 = strega.agisci(partita);
        assertTrue(strega.haSalutato());
        assertEquals("Ciao, io sono Morgana. Sono la strega più potente del mondo!", risposta1);
        assertEquals("StanzaPiena", partita.getLabirinto().getStanzaCorrente().getNome());
        assertEquals("Ora ti sposterò nella stanza con più attrezzi!", risposta2);
    }

    @Test
    public void testSalutaGiaSalutato() {
        strega.saluta();
        String risposta = strega.saluta();
        assertEquals("Ciao, io sono Morgana. Ci siamo gia' presentati!", risposta);
    }
    
    @Test
    public void testRiceviRegalo() {
    	String messaggio = strega.riceviRegalo(new Attrezzo("pozione", 1), partita);
    	assertEquals("AH-AH-AH!", messaggio);
    }
}