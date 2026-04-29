package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author docente di POO (da un'idea di Michael Kolling and David J. Barnes)
 * 
 * @version base
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""
			+ "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
			+ "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
			+ "I locali sono popolati da strani personaggi, " + "alcuni amici, altri... chissa!\n"
			+ "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
			+ "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
			+ "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
			+ "Per conoscere le istruzioni usa il comando 'aiuto'.";

	static final private String[] elencoComandi = { "vai", "prendi", "posa", "aiuto", "fine" };

	private Partita partita;
	private IOConsole io;

	public DiaDia(IOConsole io) {
		this.partita = new Partita();
		this.io = io;
	}

	public void gioca() {
		String istruzione;

		io.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do
			istruzione = io.leggiRiga();
		while (!processaIstruzione(istruzione));
	}

	/**
	 * Processa una istruzione
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false
	 *         altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire = new Comando(istruzione);
		String nome = comandoDaEseguire.getNome();
		
		if (comandoDaEseguire.sconosciuto() || nome == null || nome.trim().isEmpty())
			return false;
		if ("fine".equals(nome)) {
			this.fine();
			return true;
		}
		else if ("vai".equals(nome))
			this.vai(comandoDaEseguire.getParametro());
		else if ("aiuto".equals(nome))
			this.aiuto();
		else if ("prendi".equals(nome))
			this.prendi(comandoDaEseguire.getParametro());
		else if ("posa".equals(nome))
			this.posa(comandoDaEseguire.getParametro());
		else
			io.mostraMessaggio("Comando sconosciuto");
		if (this.partita.vinta()) {
			io.mostraMessaggio("Hai vinto!");
			return true;
		}
		else
			return false;
	}

	// implementazioni dei comandi dell'utente:

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		for (int i = 0; i < elencoComandi.length; i++)
			io.mostraMessaggio(elencoComandi[i] + " ");
		io.mostraMessaggio("");
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra e ne stampa il
	 * nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if (direzione == null)
			io.mostraMessaggio("Dove vuoi andare?");
		Stanza prossimaStanza = null;
		prossimaStanza = this.partita.getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			io.mostraMessaggio("Direzione inesistente");
		else {
			this.partita.setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getCfu();
			this.partita.setCfu(cfu--);
		}
		io.mostraMessaggio(partita.getStanzaCorrente().getDescrizione());
	}
	
	/**
	 * Comando "Prendi"
	 */
	private void prendi(String attrezzo) {
		if (attrezzo == null)
			io.mostraMessaggio("Cosa vuoi prendere?");
		else {
			Attrezzo a = this.partita.getStanzaCorrente().getAttrezzo(attrezzo);
			if (a == null)
				io.mostraMessaggio("Attrezzo non presente nella stanza");
			else if (this.partita.getGiocatore().getBorsa().addAttrezzo(a)) {
				this.partita.getStanzaCorrente().removeAttrezzo(a);
				io.mostraMessaggio("Attrezzo " + attrezzo + " preso.");
			} else
				io.mostraMessaggio("Non puoi portare questo attrezzo, pesa troppo...");
		}
	}
	
	private void posa(String attrezzo) {
		if(attrezzo == null) {
			io.mostraMessaggio("Cosa vuoi prendere?");
		}
		else {
			Attrezzo a = this.partita.getGiocatore().getBorsa().getAttrezzo(attrezzo);
			if(a == null) {
				io.mostraMessaggio("Attrezzo non presente nella borsa");
			}
			else if(this.partita.getStanzaCorrente().addAttrezzo(a)) {
				this.partita.getGiocatore().getBorsa().removeAttrezzo(attrezzo);
				io.mostraMessaggio("Attrezzo " + attrezzo + " posato.");
			}
			else {
				io.mostraMessaggio("Non c'entrano più attrezzi nella stanza!");
			}
		}
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		io.mostraMessaggio("Grazie di aver giocato!"); // si desidera smettere
	}

	public static void main(String[] argc) {
		IOConsole ioConsole = new IOConsole();
		DiaDia gioco = new DiaDia(ioConsole);
		gioco.gioca();
	}
}