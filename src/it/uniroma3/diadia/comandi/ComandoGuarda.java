package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends AbstractComando {
	
	public ComandoGuarda() {
		super();
	}

	@Override
	public void esegui(Partita partita, IO io) {
		io.mostraMessaggio("");
		io.mostraMessaggio("---STANZA CORRENTE---");
		io.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
		io.mostraMessaggio("---------------------");
		io.mostraMessaggio("");
		io.mostraMessaggio("---STANZA VINCENTE---");
		io.mostraMessaggio(partita.getLabirinto().getStanzaVincente().getNome());
		io.mostraMessaggio("---------------------");
		io.mostraMessaggio("");
		io.mostraMessaggio("---CREDITI RIMANENTI---");
		io.mostraMessaggio(partita.getGiocatore().toString());
		io.mostraMessaggio("---------------------");
		io.mostraMessaggio("");
		io.mostraMessaggio("---BORSA---");
		io.mostraMessaggio(partita.getGiocatore().getBorsa().toString());
		io.mostraMessaggio("---------------------");
	}

}
