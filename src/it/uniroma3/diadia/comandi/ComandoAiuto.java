package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto implements Comando {
	
	static final private String[] elencoComandi = { "vai", "prendi", "posa", "guarda", "aiuto", "fine" };
	private IO io;
	
	public ComandoAiuto(IO io) {
		this.io = io;
	}

	@Override
	public void esegui(Partita partita) {
		
		io.mostraMessaggio("");
		io.mostraMessaggio("Azioni disponibili:");
		for (int i = 0; i < elencoComandi.length; i++)
			io.mostraMessaggio(elencoComandi[i] + " ");
	}

	@Override
	public void setParametro(String parametro) {}

}
