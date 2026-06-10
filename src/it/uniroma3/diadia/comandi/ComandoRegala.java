package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoRegala extends AbstractComando {
	
	private String nomeAttrezzo;
	
	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo = parametro;
	}
	
	@Override
	public void esegui(Partita partita, IO io) {
		if (this.nomeAttrezzo == null)
			io.mostraMessaggio("Cosa dovrei regalare?...");
		else {
			if(partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo)) {
				if(partita.getLabirinto().getStanzaCorrente().getPersonaggio() == null) {
					io.mostraMessaggio("Non c'e' nessuno a cui regalare questo attrezzo...");
				}
				else {
					String messaggio = partita.getLabirinto().getStanzaCorrente().getPersonaggio().riceviRegalo(partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo), partita);
					io.mostraMessaggio(messaggio);
				}
			}
			 else
				io.mostraMessaggio("Non hai questo attrezzo nella borsa...");
		}
	}

}
