package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.Direzione;

public class StanzaBloccata extends Stanza {
	
	private Direzione direzioneBloccata;
	private String attrezzo;
	
	public StanzaBloccata(String nome, String direzioneBloccata, String attrezzo) {
		super(nome);
		this.direzioneBloccata = Direzione.getDirezione(direzioneBloccata);
		this.attrezzo = attrezzo;
	}
	
	@Override
	public Stanza getStanzaAdiacente(Direzione direzione) {
		if(direzione.equals(direzioneBloccata) && !this.hasAttrezzo(attrezzo)) {
			return this;
		}
		return super.getStanzaAdiacente(direzione);
	}
	
	@Override
	public String getDescrizione() {
		return super.toString() + "\nQUESTA È UNA STANZA BLOCCATA";
	}

}
