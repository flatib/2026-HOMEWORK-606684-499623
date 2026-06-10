package it.uniroma3.diadia.personaggi;

import java.util.Collections;
import java.util.Map;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.ComparatorePerAttrezzi;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {

	public Strega(String nome, String presentazione) {
		super(nome, presentazione);
	}

	@Override
	public String agisci(Partita partita) {
		ComparatorePerAttrezzi comparatore = new ComparatorePerAttrezzi();
		Map<String, Stanza> stanze = partita.getLabirinto().getStanze();
		if(haSalutato()) {
			partita.getLabirinto().setStanzaCorrente(Collections.max(stanze.values(), comparatore));
			return "Ora ti sposterò nella stanza con più attrezzi!";
		}
		partita.getLabirinto().setStanzaCorrente(Collections.min(stanze.values(), comparatore));
		return "Non mi hai salutato, ora ti sposterò nella stanza con meno attrezzi!";
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		return "AH-AH-AH!";
	}

}
