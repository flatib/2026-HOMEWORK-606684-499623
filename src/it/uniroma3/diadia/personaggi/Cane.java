package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Cane extends AbstractPersonaggio {
	
	private String ciboPreferito;
	private Attrezzo attrezzo;

	public Cane(String nome, String presentazione, String ciboPreferito, Attrezzo attrezzo) {
		super(nome, presentazione);
		this.ciboPreferito = ciboPreferito;
		this.attrezzo = attrezzo;
	}

	@Override
	public String agisci(Partita partita) {
		partita.getGiocatore().setCfu(partita.getGiocatore().getCfu() - 1);
		return "Il cane ti ha morso! Hai perso 1 CFU!";
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if (attrezzo.getNome().equals(this.ciboPreferito)) {
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(this.attrezzo);
			return "Il cane scondinzola felice e ti regala un attrezzo " + this.attrezzo.getNome() + "!";
		}
		partita.getGiocatore().setCfu(partita.getGiocatore().getCfu() - 1);
		return "Il cane ringhia infastidito e ti morde! Hai perso 1 CFU!";
	}
	
	@Override
	public String toString() {
		return this.getNome() + " (cibo preferito: " + this.ciboPreferito + ")";
	}

	
}
