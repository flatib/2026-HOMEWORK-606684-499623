package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa implements Comando {

	private String attrezzo;
	private IO io;
	
	public ComandoPosa(IO io) {
		this.io = io;
	}
	@Override
	public void esegui(Partita partita) {

		if(attrezzo == null) {
			io.mostraMessaggio("Cosa vuoi prendere?");
		}
		else {
			Attrezzo a = partita.getGiocatore().getBorsa().getAttrezzo(attrezzo);
			if(a == null) {
				io.mostraMessaggio("Attrezzo non presente nella borsa");
			}
			else if(partita.getLabirinto().getStanzaCorrente().addAttrezzo(a)) {
				partita.getGiocatore().getBorsa().removeAttrezzo(attrezzo);
				io.mostraMessaggio("Attrezzo " + attrezzo + " posato.");
			}
			else {
				io.mostraMessaggio("Non c'entrano più attrezzi nella stanza!");
			}
		}
	}

	@Override
	public void setParametro(String parametro) {

		this.attrezzo = parametro;
	}

}
