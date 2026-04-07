package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.giocatore.Giocatore;

/**
 * Questa classe modella una partita del gioco
 *
 * @author docente di POO
 * @see Stanza
 * @version base
 */

public class Partita {

	private boolean finita;
	private Labirinto labirinto;
	private Giocatore giocatore;

	public Partita() {
		this.finita = false;
		labirinto = new Labirinto();
		giocatore = new Giocatore();
	}
	
	public Stanza getStanzaVincente() {
		return labirinto.getStanzaVincente();
	}

	public Stanza getStanzaCorrente() {
		return labirinto.getStanzaCorrente();
	}
	
	public void setStanzaCorrente(Stanza stanzaCorrente) {
		labirinto.setStanzaCorrente(stanzaCorrente);
	}
	/**
	 * Restituisce vero se e solo se la partita e' stata vinta
	 * 
	 * @return vero se partita vinta
	 */
	public boolean vinta() {
		return labirinto.getStanzaCorrente() == labirinto.getStanzaVincente();
	}

	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * 
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return finita || vinta() || (giocatore.getCfu() == 0);
	}

	/**
	 * Imposta la partita come finita
	 *
	 */
	public void setFinita() {
		this.finita = true;
	}

	public int getCfu() {
		return giocatore.getCfu();
	}
	
	public void setCfu(int cfu) {
		giocatore.setCfu(cfu);
	}
	
	public Giocatore getGiocatore() {
		return this.giocatore;
	}
}
