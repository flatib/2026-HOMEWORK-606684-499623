package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.ComandoSaluta;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Strega;

class ComandoSalutaTest {

    private Partita partita;
    private IO io;
    private AbstractComando comando;

    private Stanza atrio;
    private Stanza biblioteca;
    private Stanza aula;

    private Cane cane;
    private Strega strega;

    @BeforeEach
    void setUp() {
        partita = new Partita(
                Labirinto.newBuilder()
                        .addStanzaIniziale("Atrio")
                        .addStanza("Biblioteca")
                        .addStanza("Aula")
                        .addStanzaVincente("Uscita")
                        .addAdiacenza("Atrio", "Biblioteca", "nord")
                        .addAdiacenza("Biblioteca", "Atrio", "sud")
                        .addAdiacenza("Biblioteca", "Aula", "est")
                        .addAdiacenza("Aula", "Biblioteca", "ovest")
                        .addAdiacenza("Aula", "Uscita", "nord")
                        .getLabirinto()
        );

        io = new IOConsole(new Scanner(System.in));
        comando = new ComandoSaluta();

        atrio = partita.getLabirinto().getStanze().get("Atrio");
        biblioteca = partita.getLabirinto().getStanze().get("Biblioteca");
        aula = partita.getLabirinto().getStanze().get("Aula");

        cane = new Cane("Fido", "Sono un cane feroce!",  "bistecca", new Attrezzo("lanterna", 2));
        strega = new Strega("Morgana", "Sono la strega più potente del mondo!");

        atrio.setPersonaggio(cane);
        biblioteca.setPersonaggio(strega);
    }

    @Test
    void testEseguiConPersonaggio_Cane() {
        partita.getLabirinto().setStanzaCorrente(atrio);
        comando.esegui(partita, io);
        assertTrue(cane.haSalutato());
    }

    @Test
    void testEseguiConPersonaggio_CheSalutaDueVolte() {
        partita.getLabirinto().setStanzaCorrente(biblioteca);
        comando.esegui(partita, io);
        comando.esegui(partita, io);
        assertTrue(strega.haSalutato());
    }

    @Test
    void testEseguiSenzaPersonaggio() {
        partita.getLabirinto().setStanzaCorrente(aula);
        comando.esegui(partita, io);
    }
}