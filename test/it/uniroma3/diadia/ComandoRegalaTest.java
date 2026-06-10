package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.ComandoRegala;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

class ComandoRegalaTest {

	private Partita partita;
    private IO io;
    private AbstractComando comando;

    private Cane cane;
    private Strega strega;
    private Mago mago;
    private Stanza stanza;

    @BeforeEach
    void setUp() {
        this.partita = new Partita(Labirinto.newBuilder().getLabirinto());

        this.io = new IOConsole(new Scanner(System.in));
        this.comando = new ComandoRegala();

        this.cane = new Cane("Fido", "Sono un cane feroce!", "bistecca", new Attrezzo("lanterna", 2));
        this.strega = new Strega("Morgana", "Sono la strega più potente del mondo!");
        this.mago = new Mago("Merlino", "Sono il mago più saggio del mondo!", new Attrezzo("bacchetta", 1));
        
        this.stanza = new Stanza("Atrio");
        partita.getLabirinto().setStanzaCorrente(stanza);
        
        partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("bistecca", 2));
        partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("pozione", 1));
        partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("incudine", 6));
        
    }
    
    @Test
    void testRegalaCiboPreferitoAlCane() {
    	partita.getLabirinto().getStanzaCorrente().setPersonaggio(cane);
    	comando.setParametro("bistecca");
    	comando.esegui(partita, io);
    	assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
    	assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("bistecca"));
    }
    
    @Test
    void testRegalaNotCiboPreferitoAlCane() {
    	int cfuIniziali = partita.getGiocatore().getCfu();
    	partita.getLabirinto().getStanzaCorrente().setPersonaggio(cane);
		comando.setParametro("pozione");
		comando.esegui(partita, io);
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("lanterna"));
		assertEquals(cfuIniziali-1, partita.getGiocatore().getCfu());
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("pozione"));
    }
    
    @Test
    void testRegalaAllaStrega() {
    	partita.getLabirinto().getStanzaCorrente().setPersonaggio(strega);
    	comando.setParametro("pozione");
    	comando.esegui(partita, io);
    	assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("pozione"));
    }
    
    @Test
    void testRegalaAlMago() {
    	int pesoIniziale = partita.getGiocatore().getBorsa().getAttrezzo("incudine").getPeso();
    	partita.getLabirinto().getStanzaCorrente().setPersonaggio(mago);
		comando.setParametro("incudine");
		comando.esegui(partita, io);
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("incudine"));
		assertEquals(pesoIniziale/2, partita.getLabirinto().getStanzaCorrente().getAttrezzo("incudine").getPeso());
		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("incudine"));
    }
    

}
