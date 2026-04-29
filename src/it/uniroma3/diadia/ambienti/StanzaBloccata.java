package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {
	
	private String direzioneBloccata;
	private String attrezzo;
	
	public StanzaBloccata(String nome, String direzioneBloccata, String attrezzo) {
		super(nome);
		this.direzioneBloccata = direzioneBloccata;
		this.attrezzo = attrezzo;
	}
	
	@Override
	public Stanza getStanzaAdiacente(String direzione) {
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
