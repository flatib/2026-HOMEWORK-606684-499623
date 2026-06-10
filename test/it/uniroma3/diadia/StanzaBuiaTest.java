package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Cane;

class StanzaBuiaTest {

    private Stanza atrio;
    private Stanza n11;
    private Stanza biblioteca;
    private StanzaBuia stanzaBuia;
    private Attrezzo lanterna;

    @BeforeEach
    void setUp() {
        atrio = new Stanza("Atrio");
        n11 = new Stanza("N11");
        biblioteca = new Stanza("Biblioteca");
        stanzaBuia = new StanzaBuia("Stanza Buia", "lanterna");
        lanterna = new Attrezzo("lanterna", 1);
    }

    @Test
    void testGetStanzaAdiacenteVuota() {
        assertNull(atrio.getStanzaAdiacente(Direzione.NORD));
    }

    @Test
    void testImpostaStanzaAdiacente() {
        atrio.impostaStanzaAdiacente(Direzione.NORD, n11);
        assertEquals(n11, atrio.getStanzaAdiacente(Direzione.NORD));
    }

    @Test
    void testImpostaStanzaAdicenteSovrascritta() {
        atrio.impostaStanzaAdiacente(Direzione.NORD, n11);
        atrio.impostaStanzaAdiacente(Direzione.NORD, biblioteca);
        assertSame(biblioteca, atrio.getStanzaAdiacente(Direzione.NORD));
    }

    @Test
    void testGetDirezioniVuote() {
        assertTrue(atrio.getDirezioni().isEmpty());
    }

    @Test
    void testGetDirezioniUna() {
        atrio.impostaStanzaAdiacente(Direzione.NORD, n11);
        assertTrue(atrio.getDirezioni().contains(Direzione.NORD));
    }

    @Test
    void testGetDirezioniDue() {
        atrio.impostaStanzaAdiacente(Direzione.NORD, n11);
        atrio.impostaStanzaAdiacente(Direzione.EST, biblioteca);
        assertTrue(atrio.getDirezioni().contains(Direzione.NORD));
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
    void testGetDescrizioneNoAttrezzo() {
        assertEquals("qui c'è buio pesto", stanzaBuia.getDescrizione());
    }

    @Test
    void testGetDescrizioneConAttrezzo() {
        stanzaBuia.addAttrezzo(lanterna);
        assertEquals(stanzaBuia.toString(), stanzaBuia.getDescrizione());
    }
}