package it.uniroma3.diadia;

import java.io.InputStream;
import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;

public class DiaDia {

    static final private String MESSAGGIO_BENVENUTO = ""
        + "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
        + "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
        + "I locali sono popolati da strani personaggi, alcuni amici, altri... chissa!\n"
        + "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
        + "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
        + "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
        + "Per conoscere le istruzioni usa il comando 'aiuto'.";

    private Partita partita;
    private IO io;

    public DiaDia(Labirinto labirinto, IO io, int numeroLivelli, int cfuIniziali, int pesoMaxBorsa) {
        this.partita = new Partita(labirinto, numeroLivelli, cfuIniziali, pesoMaxBorsa);
        this.io = io;
    }

    public void gioca() throws Exception {
        String istruzione;
        io.mostraMessaggio(MESSAGGIO_BENVENUTO);
        do
            istruzione = io.leggiRiga();
        while (!processaIstruzione(istruzione));
    }

    private boolean processaIstruzione(String istruzione) throws Exception {
        AbstractComando comandoDaEseguire;
        FabbricaDiComandi factory = new FabbricaDiComandiRiflessiva();
        comandoDaEseguire = factory.costruisciComando(istruzione);
        comandoDaEseguire.esegui(this.partita, this.io);

        if (this.partita.levelCompletato()) {
            if (this.partita.isUltimoLivello()) {
                io.mostraMessaggio("Hai vinto il gioco completo!");
            } else {
                io.mostraMessaggio("Hai completato il livello " + this.partita.getCurrentLevel() + "!");
                this.partita.nextLevel();
                io.mostraMessaggio("Benvenuto al livello " + this.partita.getCurrentLevel() + "!");
            }
        }

        if (this.partita.getGiocatore().getCfu() == 0) {
            io.mostraMessaggio("Hai esaurito i CFU...");
        }

        return this.partita.isFinita();
    }

    public static void main(String[] argc) throws Exception {
    	ConfigurazioneDiadia config = new ConfigurazioneDiadia();
        IO io = new IOConsole(new Scanner(System.in));
        InputStream in = DiaDia.class.getClassLoader().getResourceAsStream("labirinti/labirinto1.txt");
        if (in == null)
            throw new RuntimeException("Risorsa labirinti/labirinto1.txt non trovata");
        Labirinto labirinto = new Labirinto(in);
        DiaDia gioco = new DiaDia(labirinto, io, config.getNumeroLivelli(), config.getCfuIniziali(), config.getPesoMaxBorsa());
        gioco.gioca();
    }
}