package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Cane;

class StanzaBloccataTest {

    private Stanza atrio;
    private Stanza n11;
    private Stanza biblioteca;
    private StanzaBloccata stanzaBloccata;
    private Attrezzo lanterna;
    private Attrezzo chiave;

    @BeforeEach
    void setUp() {
        atrio = new Stanza("Atrio");
        n11 = new Stanza("N11");
        biblioteca = new Stanza("Biblioteca");
        stanzaBloccata = new StanzaBloccata("Stanza Bloccata", "sud", "chiave");
        lanterna = new Attrezzo("lanterna", 1);
        chiave = new Attrezzo("chiave", 1);
    }

    @Test
    void testGetStanzaAdiacenteVuota() {
        assertNull(atrio.getStanzaAdiacente(Direzione.SUD));
    }

    @Test
    void testImpostaStanzaAdiacente() {
        atrio.impostaStanzaAdiacente(Direzione.SUD, n11);
        assertEquals(n11, atrio.getStanzaAdiacente(Direzione.SUD));
    }

    @Test
    void testImpostaStanzaAdicenteSovrascritta() {
        atrio.impostaStanzaAdiacente(Direzione.SUD, n11);
        atrio.impostaStanzaAdiacente(Direzione.SUD, biblioteca);
        assertSame(biblioteca, atrio.getStanzaAdiacente(Direzione.SUD));
    }

    @Test
    void testGetDirezioniVuote() {
        assertTrue(atrio.getDirezioni().isEmpty());
    }

    @Test
    void testGetDirezioniUna() {
        atrio.impostaStanzaAdiacente(Direzione.SUD, n11);
        assertTrue(atrio.getDirezioni().contains(Direzione.SUD));
    }

    @Test
    void testGetDirezioniDue() {
        atrio.impostaStanzaAdiacente(Direzione.SUD, n11);
        atrio.impostaStanzaAdiacente(Direzione.EST, biblioteca);
        assertTrue(atrio.getDirezioni().contains(Direzione.SUD));
        assertTrue(atrio.getDirezioni().contains(Direzione.EST));
    }

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
    void testToStringSenzaPersonaggio() {
        assertEquals("Atrio\nUscite:\nAttrezzi nella stanza: \nPersonaggio presente: Nessuno", atrio.toString());
    }

    @Test
    void testToStringConPersonaggio() {
        atrio.setPersonaggio(new Cane("Fido", "Sono un cane feroce!", "bistecca", new Attrezzo("lanterna", 2)));
        assertEquals("Atrio\nUscite:\nAttrezzi nella stanza: \nPersonaggio presente: Fido (cibo preferito: bistecca)", atrio.toString());
    }

    @Test
    void testToStringConDatiESenzaPersonaggio() {
        atrio.impostaStanzaAdiacente(Direzione.NORD, n11);
        atrio.addAttrezzo(lanterna);
        assertEquals("Atrio\nUscite: nord\nAttrezzi nella stanza: lanterna (1kg) \nPersonaggio presente: Nessuno", atrio.toString());
    }

    @Test
    void testToStringConDatiEConPersonaggio() {
        atrio.impostaStanzaAdiacente(Direzione.NORD, n11);
        atrio.addAttrezzo(lanterna);
        atrio.setPersonaggio(new Cane("Fido", "Sono un cane feroce!", "bistecca", new Attrezzo("lanterna", 2)));
        assertEquals("Atrio\nUscite: nord\nAttrezzi nella stanza: lanterna (1kg) \nPersonaggio presente: Fido (cibo preferito: bistecca)", atrio.toString());
    }

    @Test
    void testGetStanzaAdiacenteDirezioneNonBloccata() {
        stanzaBloccata.impostaStanzaAdiacente(Direzione.NORD, n11);
        assertEquals("N11", stanzaBloccata.getStanzaAdiacente(Direzione.NORD).getNome());
    }

    @Test
    void testGetStanzaAdiacenteDirezioneBloccataNoAttrezzo() {
        stanzaBloccata.impostaStanzaAdiacente(Direzione.SUD, biblioteca);
        assertEquals("Stanza Bloccata", stanzaBloccata.getStanzaAdiacente(Direzione.SUD).getNome());
    }

    @Test
    void testGetStanzaAdiacenteDirezioneBloccataAttrezzoSbagliato() {
        stanzaBloccata.impostaStanzaAdiacente(Direzione.SUD, biblioteca);
        stanzaBloccata.addAttrezzo(lanterna);
        assertEquals("Stanza Bloccata", stanzaBloccata.getStanzaAdiacente(Direzione.SUD).getNome());
    }

    @Test
    void testGetStanzaAdiacenteDirezioneBloccataAttrezzoGiusto() {
        stanzaBloccata.impostaStanzaAdiacente(Direzione.SUD, biblioteca);
        stanzaBloccata.addAttrezzo(chiave);
        assertEquals("Biblioteca", stanzaBloccata.getStanzaAdiacente(Direzione.SUD).getNome());
    }

    @Test
    void getDescrizioneBloccata() {
        assertEquals(stanzaBloccata.toString() + "\nQUESTA È UNA STANZA BLOCCATA", stanzaBloccata.getDescrizione());
    }
}