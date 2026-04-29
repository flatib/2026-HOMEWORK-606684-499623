package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
	
	private String attrezzo;
	private IO io;
	
	public ComandoPrendi(IO io) {
		this.io = io;
	}

	@Override
	public void esegui(Partita partita) {
		
		if (attrezzo == null)
			io.mostraMessaggio("Cosa vuoi prendere?");
		else {
			Attrezzo a = partita.getLabirinto().getStanzaCorrente().getAttrezzo(attrezzo);
			if (a == null)
				io.mostraMessaggio("Attrezzo non presente nella stanza");
			else if (partita.getGiocatore().getBorsa().addAttrezzo(a)) {
				partita.getLabirinto().getStanzaCorrente().removeAttrezzo(a);
				io.mostraMessaggio("Attrezzo " + attrezzo + " preso.");
			} else
				io.mostraMessaggio("Non puoi portare questo attrezzo, pesa troppo...");
		}
	}

	@Override
	public void setParametro(String parametro) {
		
		this.attrezzo = parametro;
	}

}
