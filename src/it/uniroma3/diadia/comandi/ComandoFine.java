package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine extends AbstractComando {
	
	public ComandoFine() {
		super();
	}

	@Override
	public void esegui(Partita partita, IO io) {
		
		io.mostraMessaggio("Grazie di aver giocato!");
		partita.setFinita();
		
	}

}
