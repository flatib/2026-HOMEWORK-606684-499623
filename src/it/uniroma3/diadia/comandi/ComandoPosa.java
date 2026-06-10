package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa extends AbstractComando {

	private String attrezzo;
	
	public ComandoPosa() {
		super();
	}
	@Override
	public void esegui(Partita partita, IO io) {

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
