package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.CaricatoreLabirinto;
import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;

class CaricatoreLabirintoTest {

    private Labirinto caricaLabirinto(String labirintoString) throws FormatoFileNonValidoException {
        CaricatoreLabirinto caricatore = new CaricatoreLabirinto(new StringReader(labirintoString));
        caricatore.carica();
        return caricatore.getLabirinto();
    }

    @Test
    void testMonolocale() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio\n" +
                "Inizio: Atrio\n" +
                "Vincente: Atrio\n" +
                "Attrezzi: \n" +
                "Uscite: \n";

        Labirinto labirinto = caricaLabirinto(labirintoString);

        assertNotNull(labirinto.getStanzaIniziale());
        assertEquals("Atrio", labirinto.getStanzaIniziale().getNome());
        assertNotNull(labirinto.getStanzaVincente());
        assertEquals("Atrio", labirinto.getStanzaVincente().getNome());
        assertSame(labirinto.getStanzaIniziale(), labirinto.getStanzaVincente());
        assertEquals(0, labirinto.getStanzaIniziale().getAttrezzi().size());
    }

    @Test
    void testLabirintoBaseConAttrezziEUscite() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, Biblioteca, Laboratorio\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: chiave 1 Atrio, libro 2 Biblioteca, martello 3 Laboratorio\n" +
                "Uscite: Atrio nord Biblioteca, Atrio est Laboratorio, Laboratorio ovest Atrio\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);

        assertEquals(3, labirinto.getStanze().size());
        assertEquals("Atrio", labirinto.getStanzaIniziale().getNome());
        assertEquals("Biblioteca", labirinto.getStanzaVincente().getNome());
        assertTrue(labirinto.getStanzaIniziale().hasAttrezzo("chiave"));
        assertEquals("Biblioteca", labirinto.getStanzaIniziale().getStanzaAdiacente(Direzione.NORD).getNome());
        assertEquals("Laboratorio", labirinto.getStanzaIniziale().getStanzaAdiacente(Direzione.EST).getNome());
    }

    @Test
    void testCaricaStanzaBloccataComportamento() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, N10\n" +
                "StanzeBloccate: Biblioteca nord chiave\n" +
                "Inizio: Atrio\n" +
                "Vincente: N10\n" +
                "Attrezzi: \n" +
                "Uscite: Atrio nord Biblioteca, Biblioteca sud Atrio, Biblioteca nord N10, N10 sud Biblioteca\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);
        Stanza biblioteca = labirinto.getStanzaIniziale().getStanzaAdiacente(Direzione.NORD);

        assertEquals("Biblioteca", biblioteca.getNome());
        assertSame(biblioteca, biblioteca.getStanzaAdiacente(Direzione.NORD));
        assertTrue(biblioteca.getDescrizione().contains("STANZA BLOCCATA"));
    }

    @Test
    void testCaricaStanzaBuiaComportamento() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, Archivio\n" +
                "StanzeBuie: Archivio lanterna\n" +
                "Inizio: Atrio\n" +
                "Vincente: Archivio\n" +
                "Attrezzi: \n" +
                "Uscite: Atrio nord Archivio, Archivio sud Atrio\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);
        Stanza archivio = labirinto.getStanzaVincente();

        assertEquals("Archivio", archivio.getNome());
        assertEquals("qui c'è buio pesto", archivio.getDescrizione());
    }

    @Test
    void testCaricaStanzaMagicaComportamento() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, AulaMagica\n" +
                "StanzeMagiche: AulaMagica 1\n" +
                "Inizio: Atrio\n" +
                "Vincente: AulaMagica\n" +
                "Attrezzi: pozione 1 AulaMagica, spada 2 AulaMagica\n" +
                "Uscite: Atrio nord AulaMagica, AulaMagica sud Atrio\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);
        Stanza aulaMagica = labirinto.getStanzaVincente();

        assertEquals("AulaMagica", aulaMagica.getNome());
        assertTrue(aulaMagica.hasAttrezzo("pozione"));
        assertTrue(aulaMagica.hasAttrezzo("adaps"));
        assertNotNull(aulaMagica.getAttrezzo("adaps"));
        assertEquals(4, aulaMagica.getAttrezzo("adaps").getPeso());
    }

    @Test
    void testCaricaMago() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: \n" +
                "Uscite: Atrio nord Biblioteca\n" +
                "Personaggi: Mago Merlino Sono_un_mago bacchetta 1 Atrio\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);
        Stanza atrio = labirinto.getStanzaIniziale();

        assertNotNull(atrio.getPersonaggio());
        assertEquals("Merlino", atrio.getPersonaggio().getNome());
    }

    @Test
    void testCaricaStrega() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: \n" +
                "Uscite: Atrio nord Biblioteca\n" +
                "Personaggi: Strega Morgana Sono_una_strega Biblioteca\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);
        Stanza biblioteca = labirinto.getStanzaVincente();

        assertNotNull(biblioteca.getPersonaggio());
        assertEquals("Morgana", biblioteca.getPersonaggio().getNome());
    }

    @Test
    void testCaricaCane() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, Laboratorio\n" +
                "Inizio: Atrio\n" +
                "Vincente: Laboratorio\n" +
                "Attrezzi: osso 1 Atrio\n" +
                "Uscite: Atrio nord Laboratorio\n" +
                "Personaggi: Cane Fido Sono_un_cane osso collare 1 Laboratorio\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);
        Stanza laboratorio = labirinto.getStanzaVincente();

        assertNotNull(laboratorio.getPersonaggio());
        assertEquals("Fido", laboratorio.getPersonaggio().getNome());
    }

    @Test
    void testCaricaPiuPersonaggiSuUnicaRiga() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, Biblioteca, Laboratorio\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: osso 1 Atrio\n" +
                "Uscite: Atrio nord Biblioteca, Atrio est Laboratorio\n" +
                "Personaggi: Mago Merlino Sono_un_mago bacchetta 1 Atrio, " +
                "Strega Morgana Sono_una_strega Biblioteca, " +
                "Cane Fido Sono_un_cane osso collare 1 Laboratorio\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);

        assertNotNull(labirinto.getStanzaIniziale().getPersonaggio());
        assertEquals("Merlino", labirinto.getStanzaIniziale().getPersonaggio().getNome());

        assertNotNull(labirinto.getStanzaVincente().getPersonaggio());
        assertEquals("Morgana", labirinto.getStanzaVincente().getPersonaggio().getNome());

        assertNotNull(labirinto.getStanze().get("Laboratorio").getPersonaggio());
        assertEquals("Fido", labirinto.getStanze().get("Laboratorio").getPersonaggio().getNome());
    }

    @Test
    void testCaricaLabirintoCompletoConStanzeSpecialiEPersonaggi() throws FormatoFileNonValidoException {
        String labirintoString =
                "Stanze: Atrio, Biblioteca, Laboratorio, AulaMagica\n" +
                "StanzeBloccate: Biblioteca nord chiave\n" +
                "StanzeBuie: Laboratorio lanterna\n" +
                "StanzeMagiche: AulaMagica 1\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: chiave 1 Biblioteca, pozione 1 AulaMagica, spada 2 AulaMagica\n" +
                "Uscite: Atrio nord Biblioteca, Atrio est Laboratorio, Atrio ovest AulaMagica, Biblioteca sud Atrio, Laboratorio ovest Atrio, AulaMagica est Atrio\n" +
                "Personaggi: Mago Merlino Sono_un_mago bacchetta 1 Atrio\n";

        Labirinto labirinto = caricaLabirinto(labirintoString);

        assertEquals("Atrio", labirinto.getStanzaIniziale().getNome());
        assertEquals("Biblioteca", labirinto.getStanzaVincente().getNome());

        assertNotNull(labirinto.getStanzaIniziale().getPersonaggio());
        assertEquals("Merlino", labirinto.getStanzaIniziale().getPersonaggio().getNome());

        assertTrue(labirinto.getStanzaVincente().getDescrizione().contains("STANZA BLOCCATA"));
        assertEquals("qui c'è buio pesto", labirinto.getStanze().get("Laboratorio").getDescrizione());

        Stanza aulaMagica = labirinto.getStanze().get("AulaMagica");
        assertTrue(aulaMagica.hasAttrezzo("pozione"));
        assertTrue(aulaMagica.hasAttrezzo("adaps"));
    }

    @Test
    void testErrorMarkerMancante() {
        String labirintoString =
                "Stanze: Atrio\n" +
                "Inizio: Atrio\n" +
                "Vincente: Atrio\n" +
                "Uscite: \n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorStanzaInizialeNonValida() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: StanzaInesistente\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: \n" +
                "Uscite: \n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorStanzaVincenteNonValida() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: StanzaInesistente\n" +
                "Attrezzi: \n" +
                "Uscite: \n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorPesoNonValido() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: martello abc Atrio\n" +
                "Uscite: \n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorAttrezzoInStanzaInesistente() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: martello 10 StanzaInesistente\n" +
                "Uscite: \n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorUscitaDaStanzaInesistente() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: \n" +
                "Uscite: StanzaInesistente nord Biblioteca\n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorUscitaVersoStanzaInesistente() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: \n" +
                "Uscite: Atrio nord StanzaInesistente\n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorPersonaggioInStanzaInesistente() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: \n" +
                "Uscite: Atrio nord Biblioteca\n" +
                "Personaggi: Mago Merlino Sono_un_mago bacchetta 1 StanzaInesistente\n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }

    @Test
    void testErrorTipoPersonaggioSconosciuto() {
        String labirintoString =
                "Stanze: Atrio, Biblioteca\n" +
                "Inizio: Atrio\n" +
                "Vincente: Biblioteca\n" +
                "Attrezzi: \n" +
                "Uscite: Atrio nord Biblioteca\n" +
                "Personaggi: Drago Smaug Io_sono_un_drago Atrio\n";

        assertThrows(FormatoFileNonValidoException.class, () -> caricaLabirinto(labirintoString));
    }
}