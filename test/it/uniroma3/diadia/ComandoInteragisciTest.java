package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.ComandoInteragisci;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

class ComandoInteragisciTest {

    private Partita partita;
    private IO io;
    private AbstractComando comando;

    private Cane cane;
    private Strega strega;
    private Stanza atrio;
    private Stanza biblioteca;
    private Stanza aula;
    private Stanza vuota;
    private Stanza piena;
    private Stanza uscita;

    @BeforeEach
    void setUp() {
        this.partita = new Partita(
                Labirinto.newBuilder()
                        .addStanzaIniziale("Atrio")
                        .addStanza("Biblioteca")
                        .addStanza("Aula")
                        .addStanza("Vuota")
                        .addStanza("Piena")
                        .addStanzaVincente("Uscita")
                        .addAdiacenza("Atrio", "Biblioteca", "nord")
                        .addAdiacenza("Biblioteca", "Atrio", "sud")
                        .addAdiacenza("Biblioteca", "Aula", "est")
                        .addAdiacenza("Aula", "Biblioteca", "ovest")
                        .addAdiacenza("Aula", "Uscita", "nord")
                        .getLabirinto()
        );

        this.io = new IOConsole(new Scanner(System.in));
        this.comando = new ComandoInteragisci();

        this.cane = new Cane("Fido", "Sono un cane feroce!", "bistecca", new Attrezzo("lanterna", 2));
        this.strega = new Strega("Morgana", "Sono la strega più potente del mondo!");

        this.atrio = partita.getLabirinto().getStanze().get("Atrio");
        this.biblioteca = partita.getLabirinto().getStanze().get("Biblioteca");
        this.aula = partita.getLabirinto().getStanze().get("Aula");
        this.vuota = partita.getLabirinto().getStanze().get("Vuota");
        this.piena = partita.getLabirinto().getStanze().get("Piena");
        this.uscita = partita.getLabirinto().getStanze().get("Uscita");

        biblioteca.addAttrezzo(new Attrezzo("libro", 3));
        aula.addAttrezzo(new Attrezzo("penna", 1));
        piena.addAttrezzo(new Attrezzo("osso", 1));
        piena.addAttrezzo(new Attrezzo("chiave", 1));
        uscita.addAttrezzo(new Attrezzo("lanterna", 2));
        atrio.addAttrezzo(new Attrezzo("mappa", 1));
    }

    @Test
    void testEseguiConCane_ImpostaMessaggioCorretto() {
        atrio.setPersonaggio(cane);
        partita.getLabirinto().setStanzaCorrente(atrio);

        comando.esegui(partita, io);

        assertEquals("Il cane ti ha morso! Hai perso 1 CFU!",
                ((ComandoInteragisci) comando).getMessaggio());
    }

    @Test
    void testEseguiConCane_DiminuisceCfuDelGiocatore() {
        atrio.setPersonaggio(cane);
        partita.getLabirinto().setStanzaCorrente(atrio);

        int cfuIniziali = partita.getGiocatore().getCfu();

        comando.esegui(partita, io);

        assertEquals(cfuIniziali - 1, partita.getGiocatore().getCfu());
    }

    @Test
    void testEseguiConMago_ImpostaMessaggioNonNullo() {
        Mago mago = new Mago("Merlino", "Sono un grande mago!", new Attrezzo("bacchetta", 1));
        biblioteca.setPersonaggio(mago);
        partita.getLabirinto().setStanzaCorrente(biblioteca);

        comando.esegui(partita, io);

        assertNotNull(((ComandoInteragisci) comando).getMessaggio());
        assertFalse(((ComandoInteragisci) comando).getMessaggio().isBlank());
    }

    @Test
    void testEseguiConMago_AggiungeAttrezzoAllaStanza() {
        Mago mago = new Mago("Merlino", "Sono un grande mago!", new Attrezzo("bacchetta", 1));
        biblioteca.setPersonaggio(mago);
        partita.getLabirinto().setStanzaCorrente(biblioteca);

        comando.esegui(partita, io);

        assertTrue(biblioteca.hasAttrezzo("bacchetta"));
    }

    @Test
    void testEseguiConStregaNonSalutata_SpostaNellaStanzaConMenoAttrezzi() {
        aula.setPersonaggio(strega);
        partita.getLabirinto().setStanzaCorrente(aula);

        comando.esegui(partita, io);

        assertEquals("Non mi hai salutato, ora ti sposterò nella stanza con meno attrezzi!",
                ((ComandoInteragisci) comando).getMessaggio());
        assertEquals(vuota, partita.getLabirinto().getStanzaCorrente());
    }

    @Test
    void testEseguiConStregaSalutata_SpostaNellaStanzaConPiuAttrezzi() {
        strega.saluta();
        aula.setPersonaggio(strega);
        partita.getLabirinto().setStanzaCorrente(aula);

        piena.addAttrezzo(new Attrezzo("libro", 1));
        piena.addAttrezzo(new Attrezzo("candela", 1));

        comando.esegui(partita, io);

        assertEquals("Ora ti sposterò nella stanza con più attrezzi!",
                ((ComandoInteragisci) comando).getMessaggio());
        assertEquals(piena, partita.getLabirinto().getStanzaCorrente());
    }

    @Test
    void testEseguiSenzaPersonaggio_MessaggioRestaNullo() {
        partita.getLabirinto().setStanzaCorrente(atrio);

        comando.esegui(partita, io);

        assertNull(((ComandoInteragisci) comando).getMessaggio());
    }
}