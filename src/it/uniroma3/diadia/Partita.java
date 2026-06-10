package it.uniroma3.diadia;

import java.io.InputStream;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.giocatore.Giocatore;

public class Partita {

    private int numeroLivelli;
    private boolean finita;
    private Labirinto labirinto;
    private Giocatore giocatore;
    private int currentLevel;

    public Partita(Labirinto labirinto) {
        this(labirinto, 5, 20, 10);
    }

    public Partita(Labirinto labirinto, int numeroLivelli, int cfuIniziali, int pesoMaxBorsa) {
        this.finita = false;
        this.labirinto = labirinto;
        this.giocatore = new Giocatore(cfuIniziali, pesoMaxBorsa);
        this.currentLevel = 1;
        this.numeroLivelli = numeroLivelli;
    }

    public Labirinto getLabirinto() {
        return labirinto;
    }

    public void setLabirinto(Labirinto labirinto) {
        this.labirinto = labirinto;
    }

    public boolean vinta() {
        return isUltimoLivello() &&
               labirinto.getStanzaCorrente() == labirinto.getStanzaVincente();
    }

    public boolean levelCompletato() {
        return labirinto.getStanzaCorrente() == labirinto.getStanzaVincente();
    }

    public boolean isUltimoLivello() {
        return this.currentLevel == this.numeroLivelli;
    }

    public boolean isFinita() {
        return finita || vinta() || (giocatore.getCfu() == 0);
    }

    public void setFinita() {
        this.finita = true;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public int getNumeroLivelli() {
        return this.numeroLivelli;
    }

    public void setNumeroLivelli(int numeroLivelli) {
        this.numeroLivelli = numeroLivelli;
    }

    public void nextLevel() throws Exception {
        if (currentLevel < numeroLivelli) {
            currentLevel++;
            InputStream in = DiaDia.class.getClassLoader().getResourceAsStream("labirinti/labirinto" + currentLevel + ".txt");
            if (in == null)
                throw new RuntimeException("Risorsa labirinti/labirinto1.txt non trovata");
            Labirinto nuovoLabirinto = new Labirinto(in);
            setLabirinto(nuovoLabirinto);
        }
    }

    public Giocatore getGiocatore() {
        return this.giocatore;
    }
}