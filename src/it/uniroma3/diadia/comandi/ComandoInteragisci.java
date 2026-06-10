package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoInteragisci extends AbstractComando {
	private String messaggio;
			
	@Override
	public void esegui(Partita partita, IO io) {
		AbstractPersonaggio personaggio;
		personaggio = partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if (personaggio!=null) {
			this.messaggio = personaggio.agisci(partita);
			io.mostraMessaggio(this.messaggio);
			} 
		else
			io.mostraMessaggio("Non c'e' nessuno con cui interagire");
	}
	
	public String getMessaggio() {
		return this.messaggio;
	}

}
