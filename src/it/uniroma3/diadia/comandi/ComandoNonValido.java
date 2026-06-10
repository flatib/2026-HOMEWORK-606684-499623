package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido extends AbstractComando {
	
	public ComandoNonValido() {}
	
	@Override
	public void esegui(Partita partita, IO io) {
		io.mostraMessaggio("\nNon c'è nessuna azione con questo nome!");
	}

}
