package it.uniroma3.diadia.ambienti;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import it.uniroma3.diadia.Direzione;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class Labirinto {
	
	private Stanza stanzaIniziale;
	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;
	private Map<String, Stanza> stanze;
	
	public Labirinto() {
		this.stanze = new HashMap<>();
	}
	
	public Labirinto(InputStream nomeRisorsa) throws FormatoFileNonValidoException {
		this.stanze = new HashMap<>();
		CaricatoreLabirinto c = new CaricatoreLabirinto(nomeRisorsa);
		c.carica();
		this.setStanzaIniziale(c.getStanzaIniziale());
		this.setStanzaVincente(c.getStanzaVincente());
		this.stanze = c.getLabirinto().getStanze();
	}
	
	public static LabirintoBuilder newBuilder() {
        return new LabirintoBuilder();
    }
	
	public Stanza getStanzaIniziale() {
		return stanzaIniziale;
	}
	
	public void setStanzaIniziale(Stanza stanzaIniziale) {
		this.stanzaIniziale = stanzaIniziale;
		this.stanzaCorrente = stanzaIniziale;
	}
	
	public Map<String, Stanza> getStanze() {
		return stanze;
	}
	
	public void setStanze(Map<String, Stanza> stanze) {
		this.stanze = stanze;
	}
	
	public Stanza getStanzaVincente() {
		return stanzaVincente;
	}
	
	public void setStanzaVincente(Stanza stanzaVincente) {
		this.stanzaVincente = stanzaVincente;
	}

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}
	
	public static class LabirintoBuilder {

		private Labirinto labirinto;
		private Stanza ultimaStanzaAggiunta;
		private Map<String, Stanza> stanze;
		
		public LabirintoBuilder() {
			this.labirinto = new Labirinto();
			this.stanze = new HashMap<>();
		}
		
		public LabirintoBuilder addStanzaIniziale(String nome) {
			Stanza stanzaIniziale = new Stanza(nome);
			this.labirinto.setStanzaIniziale(stanzaIniziale);
			this.ultimaStanzaAggiunta = stanzaIniziale;
			this.stanze.put(nome, stanzaIniziale);
			return this;
		}
		
		public LabirintoBuilder addStanzaVincente(String nome) {
			Stanza stanzaVincente = new Stanza(nome);
			this.labirinto.setStanzaVincente(stanzaVincente);
			this.ultimaStanzaAggiunta = stanzaVincente;
			this.stanze.put(nome, stanzaVincente);
			return this;
		}
		
		public LabirintoBuilder addAttrezzo(String nome, int peso) {
			Attrezzo attrezzo = new Attrezzo(nome, peso);
			this.ultimaStanzaAggiunta.addAttrezzo(attrezzo);
			return this;
		}
		
		public LabirintoBuilder addAdiacenza(String stanza1, String stanza2, String direzioneStr) {
			Stanza s1 = this.stanze.get(stanza1);
			Stanza s2 = this.stanze.get(stanza2);
			Direzione dir = Direzione.getDirezione(direzioneStr);
			if (s1 != null && s2 != null) {
				s1.impostaStanzaAdiacente(dir, s2);
			}
			return this;
		}
		
		public LabirintoBuilder addStanza(String nome) {
			Stanza stanza = new Stanza(nome);
			this.ultimaStanzaAggiunta = stanza;
			this.stanze.put(nome, stanza);
			return this;
		}
		
		public LabirintoBuilder addStanzaBloccata(String nome, String direzioneBloccata, String attrezzoSbloccante) {
			Stanza stanza = new StanzaBloccata(nome, direzioneBloccata, attrezzoSbloccante);
			this.ultimaStanzaAggiunta = stanza;
			this.stanze.put(nome, stanza);
			return this;
		}
		
		public LabirintoBuilder addStanzaBuia(String nome, String attrezzoSbloccante) {
			Stanza stanza = new StanzaBuia(nome, attrezzoSbloccante);
			this.ultimaStanzaAggiunta = stanza;
			this.stanze.put(nome, stanza);
			return this;
		}
		
		public LabirintoBuilder addStanzaMagica(String nome, int soglia) {
			Stanza stanza = new StanzaMagica(nome, soglia);
			this.ultimaStanzaAggiunta = stanza;
			this.stanze.put(nome, stanza);
			return this;
		}
		
		public LabirintoBuilder addMago(String nome, String descrizione, String attrezzo, int peso) {
			AbstractPersonaggio personaggio = new Mago(nome, descrizione, new Attrezzo(attrezzo, peso));
			this.ultimaStanzaAggiunta.setPersonaggio(personaggio);
			return this;
		}
		
		public LabirintoBuilder addStrega(String nome, String descrizione) {
			AbstractPersonaggio personaggio = new Strega(nome, descrizione);
			this.ultimaStanzaAggiunta.setPersonaggio(personaggio);
			return this;
		}
		
		public LabirintoBuilder addCane(String nome, String descrizione, String ciboPreferito, String attrezzo, int peso) {
			AbstractPersonaggio personaggio = new Cane(nome, descrizione, ciboPreferito, new Attrezzo(attrezzo, peso));
			this.ultimaStanzaAggiunta.setPersonaggio(personaggio);
			return this;
		}
		
		public Labirinto getLabirinto() {
			labirinto.setStanze(this.stanze);
			return this.labirinto;
		}
		
		public Map<String, Stanza> getListaStanze() {
			return this.stanze;
		}
	}
}
