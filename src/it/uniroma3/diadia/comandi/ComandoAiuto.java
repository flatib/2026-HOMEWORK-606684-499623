package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto extends AbstractComando {
	
	public ComandoAiuto() {
		super();
	}

	@Override
	public void esegui(Partita partita, IO io) {
		
		io.mostraMessaggio("");
		io.mostraMessaggio("Azioni disponibili:");
		for (String comando : super.getElencoComandi())
			io.mostraMessaggio(comando);
	}

}
