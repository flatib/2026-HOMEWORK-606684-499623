package it.uniroma3.diadia.ambienti;

import java.io.*;
import java.util.*;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class CaricatoreLabirinto {

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze */
	private static final String STANZE_MARKER = "Stanze:";             

	/* prefisso di una singola riga contenente il nome della stanza iniziale */
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";    

	/* prefisso della riga contenente il nome stanza vincente */
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";  

	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeAttrezzo> <peso> <nomeStanza> */
	private static final String ATTREZZI_MARKER = "Attrezzi:";

	/* prefisso della riga contenente le specifiche dei collegamenti tra stanza nel formato <nomeStanzaDa> <direzione> <nomeStanzaA> */
	private static final String USCITE_MARKER = "Uscite:";
	
	/* prefisso della riga contenente i nomi delle stanze bloccate nel formato <nomeStanzaBloccata> <direzioneBloccante> <attrezzoSbloccante> */
	private static final String STANZE_BLOCCATE_MARKER = "StanzeBloccate:";
	
	/* prefisso della riga contenente i nomi delle stanze buie nel formato <nomeStanzaBuia> <attrezzoSbloccante> */
	private static final String STANZE_BUIE_MARKER = "StanzeBuie:";
	
	/* prefisso della riga contenente i nomi delle stanze magiche nel formato <nomeStanzaMagica> <soglia> */
	private static final String STANZE_MAGICHE_MARKER = "StanzeMagiche:";
	
	/* prefisso della riga contenente i nomi dei personaggi nel formato:
	 * Cane: Cane <nome> <descrizione> <ciboPreferito> <nomeAttrezzo> <pesoAttrezzo> <stanza>
	 * Mago: Mago <nome> <descrizione> <nomeAttrezzo> <pesoAttrezzo> <stanza>
	 * Strega: Strega <nome> <descrzione> <stanza>
	 * */
	private static final String PERSONAGGI_MARKER = "Personaggi:";
	

	private String rigaInAttesa = null;

	/*
	 *  Esempio di un possibile file di specifica di un labirinto (vedi POO-26-eccezioni-file.pdf)

		Stanze: biblioteca, N10, N11
		Inizio: N10
		Vincente: N11
		Attrezzi: martello 10 biblioteca, pinza 2 N10
		Uscite: biblioteca nord N10, biblioteca sud N11

	 */
	private LineNumberReader reader;

	private Labirinto.LabirintoBuilder builder;
	private String nomeStanzaIniziale;
	private String nomeStanzaVincente;


	public CaricatoreLabirinto(InputStream nomeRisorsa) {
		this(new InputStreamReader(nomeRisorsa));
	}

	public CaricatoreLabirinto(Reader reader) {
		this.builder = Labirinto.newBuilder();
		this.reader = new LineNumberReader(reader);
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			this.leggiECreaStanze();
			this.leggiECreaStanzeBloccate();
			this.leggiECreaStanzeBuie();
			this.leggiECreaStanzeMagiche();
			this.leggiInizialeEvincente();
			this.leggiECollocaAttrezzi();
			this.leggiEImpostaUscite();
			this.leggiECollocaPersonaggi();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
	    try {
	        String riga = (rigaInAttesa != null) ? rigaInAttesa : this.reader.readLine();
	        rigaInAttesa = null;
	        check(riga != null && riga.startsWith(marker), "era attesa una riga che cominciasse per " + marker);
	        return riga.substring(marker.length());
	    } catch (IOException e) {
	        throw new FormatoFileNonValidoException(e.getMessage());
	    }
	}
	
	private String leggiRigaOpzionaleCheCominciaPer(String marker) throws FormatoFileNonValidoException {
	    try {
	        String riga = (rigaInAttesa != null) ? rigaInAttesa : this.reader.readLine();
	        if (riga == null || !riga.startsWith(marker)) {
	            rigaInAttesa = riga;
	            return null;
	        }
	        rigaInAttesa = null;
	        return riga.substring(marker.length());
	    } catch (IOException e) {
	        throw new FormatoFileNonValidoException(e.getMessage());
	    }
	}

	private void leggiECreaStanze() throws FormatoFileNonValidoException  {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		for(String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			builder.addStanza(nomeStanza);
		}
	}
	
	private void leggiECreaStanzeBloccate() throws FormatoFileNonValidoException {
	    String specifiche = this.leggiRigaOpzionaleCheCominciaPer(STANZE_BLOCCATE_MARKER);
	    if (specifiche != null && !specifiche.isBlank()) {
	    	for (String specifica : separaStringheAlleVirgole(specifiche)) {
		        try (Scanner scannerLinea = new Scanner(specifica)) {
		            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una stanza bloccata."));
		            String nomeStanza = scannerLinea.next();
		            check(scannerLinea.hasNext(), msgTerminazionePrecoce("la direzione bloccata della stanza " + nomeStanza + "."));
		            String direzioneBloccata = scannerLinea.next();
		            check(scannerLinea.hasNext(), msgTerminazionePrecoce("l'attrezzo sbloccante della stanza " + nomeStanza + "."));
		            String attrezzoSbloccante = scannerLinea.next();

		            this.builder.addStanzaBloccata(nomeStanza, direzioneBloccata, attrezzoSbloccante);
		        }
		    }
	    }
	}
	
	private void leggiECreaStanzeBuie() throws FormatoFileNonValidoException {
	    String specifiche = this.leggiRigaOpzionaleCheCominciaPer(STANZE_BUIE_MARKER);
	    if (specifiche != null && !specifiche.isBlank()) {
	    	for (String specifica : separaStringheAlleVirgole(specifiche)) {
		        try (Scanner scannerLinea = new Scanner(specifica)) {
		            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una stanza buia."));
		            String nomeStanza = scannerLinea.next();
		            check(scannerLinea.hasNext(), msgTerminazionePrecoce("l'attrezzo necessario della stanza " + nomeStanza + "."));
		            String attrezzoNecessario = scannerLinea.next();

		            this.builder.addStanzaBuia(nomeStanza, attrezzoNecessario);
		        }
		    }
	    }
	}
	
	private void leggiECreaStanzeMagiche() throws FormatoFileNonValidoException {
	    String specifiche = this.leggiRigaOpzionaleCheCominciaPer(STANZE_MAGICHE_MARKER);
	    if (specifiche != null && !specifiche.isBlank()) {
	    	for (String specifica : separaStringheAlleVirgole(specifiche)) {
		        try (Scanner scannerLinea = new Scanner(specifica)) {
		            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome di una stanza magica."));
		            String nomeStanza = scannerLinea.next();
		            check(scannerLinea.hasNextInt(), msgTerminazionePrecoce("la soglia della stanza magica " + nomeStanza + "."));
		            int soglia = scannerLinea.nextInt();

		            this.builder.addStanzaMagica(nomeStanza, soglia);
		        }
		    }
	    }
	}
	
	private void leggiECollocaPersonaggi() throws FormatoFileNonValidoException {
	    String specifichePersonaggi = this.leggiRigaOpzionaleCheCominciaPer(PERSONAGGI_MARKER);
	    if (specifichePersonaggi != null && !specifichePersonaggi.isBlank()) {
	    	for (String specificaPersonaggio : separaStringheAlleVirgole(specifichePersonaggi)) {
		        this.parsePersonaggio(specificaPersonaggio.trim());
		    }
	    }
	}
	
	private void parsePersonaggio(String specifica) throws FormatoFileNonValidoException {
	    try (Scanner scannerLinea = new Scanner(specifica)) {
	        check(scannerLinea.hasNext(), msgTerminazionePrecoce("il tipo di personaggio."));
	        String tipo = scannerLinea.next();

	        if (tipo.equals("Mago")) {
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome del mago."));
	            String nome = scannerLinea.next();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione del mago " + nome + "."));
	            String presentazione = scannerLinea.next();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome dell'attrezzo del mago " + nome + "."));
	            String nomeAttrezzo = scannerLinea.next();
	            check(scannerLinea.hasNextInt(), msgTerminazionePrecoce("il peso dell'attrezzo del mago " + nome + "."));
	            int peso = scannerLinea.nextInt();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("la stanza del mago " + nome + "."));
	            String nomeStanza = scannerLinea.next();

	            check(isStanzaValida(nomeStanza), "Personaggio " + nome + " non collocabile: stanza " + nomeStanza + " inesistente");
	            this.builder.getListaStanze().get(nomeStanza)
	                    .setPersonaggio(new it.uniroma3.diadia.personaggi.Mago(nome, presentazione,
	                            new Attrezzo(nomeAttrezzo, peso)));
	        }

	        else if (tipo.equals("Strega")) {
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome della strega."));
	            String nome = scannerLinea.next();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione della strega " + nome + "."));
	            String presentazione = scannerLinea.next();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("la stanza della strega " + nome + "."));
	            String nomeStanza = scannerLinea.next();

	            check(isStanzaValida(nomeStanza), "Personaggio " + nome + " non collocabile: stanza " + nomeStanza + " inesistente");
	            this.builder.getListaStanze().get(nomeStanza)
	                    .setPersonaggio(new it.uniroma3.diadia.personaggi.Strega(nome, presentazione));
	        }

	        else if (tipo.equals("Cane")) {
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome del cane."));
	            String nome = scannerLinea.next();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("la presentazione del cane " + nome + "."));
	            String presentazione = scannerLinea.next();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il cibo preferito del cane " + nome + "."));
	            String ciboPreferito = scannerLinea.next();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("il nome dell'attrezzo del cane " + nome + "."));
	            String nomeAttrezzo = scannerLinea.next();
	            check(scannerLinea.hasNextInt(), msgTerminazionePrecoce("il peso dell'attrezzo del cane " + nome + "."));
	            int peso = scannerLinea.nextInt();
	            check(scannerLinea.hasNext(), msgTerminazionePrecoce("la stanza del cane " + nome + "."));
	            String nomeStanza = scannerLinea.next();

	            check(isStanzaValida(nomeStanza), "Personaggio " + nome + " non collocabile: stanza " + nomeStanza + " inesistente");
	            this.builder.getListaStanze().get(nomeStanza)
	                    .setPersonaggio(new it.uniroma3.diadia.personaggi.Cane(nome, presentazione,
	                            ciboPreferito, new Attrezzo(nomeAttrezzo, peso)));
	        }

	        else {
	            check(false, "Tipo personaggio sconosciuto " + tipo);
	        }
	    }
	}

	private List<String> separaStringheAlleVirgole(String string) {
		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(",");
		try (Scanner scannerDiParole = scanner) {
			while (scannerDiParole.hasNext()) {
				result.add(scannerDiParole.next().trim());
			}
		}
		return result;
	}


	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER).trim();
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale +" non definita");
		
		Stanza stanzaIniziale = this.builder.getListaStanze().get(nomeStanzaIniziale);
		this.builder.getLabirinto().setStanzaIniziale(stanzaIniziale);
		
		nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER).trim();
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		
		Stanza stanzaVincente = this.builder.getListaStanze().get(nomeStanzaVincente);
		this.builder.getLabirinto().setStanzaVincente(stanzaVincente);
	}

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {
		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);
		if(specificheAttrezzi!= null && !specificheAttrezzi.isBlank()) {
			for(String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
				String nomeAttrezzo = null;
				String pesoAttrezzo = null;
				String nomeStanza = null; 
				try (Scanner scannerLinea = new Scanner(specificaAttrezzo)) {
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un attrezzo."));
					nomeAttrezzo = scannerLinea.next();
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo "+nomeAttrezzo+"."));
					pesoAttrezzo = scannerLinea.next();
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza in cui collocare l'attrezzo "+nomeAttrezzo+"."));
					nomeStanza = scannerLinea.next();
				}				
				posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
			}
		}
	}

	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			check(isStanzaValida(nomeStanza),"Attrezzo "+ nomeAttrezzo+" non collocabile: stanza " +nomeStanza+" inesistente");
			Stanza stanza = this.builder.getListaStanze().get(nomeStanza);
			stanza.addAttrezzo(new Attrezzo(nomeAttrezzo, peso));
		}
		catch (NumberFormatException e) {
			check(false, "Peso attrezzo "+nomeAttrezzo+" non valido");
		}
	}


	private boolean isStanzaValida(String nomeStanza) {
		return this.builder.getListaStanze().containsKey(nomeStanza);
	}

	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
		String specificheUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
		if(specificheUscite != null && !specificheUscite.isBlank()) {
			for(String specificaUscita : separaStringheAlleVirgole(specificheUscite)) {
				try (Scanner scannerLinea = new Scanner(specificaUscita)) {
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("le uscite di una stanza."));
					String stanzaPartenza = scannerLinea.next();
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("la direzione di una uscita della stanza "+stanzaPartenza));
					String dir = scannerLinea.next();
					check(scannerLinea.hasNext(),msgTerminazionePrecoce("la destinazione di una uscita della stanza "+stanzaPartenza+" nella direzione "+dir));
					String stanzaDestinazione = scannerLinea.next();
				
					impostaUscita(stanzaPartenza, dir, stanzaDestinazione);
				}
			}
		}
	}
	
	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere "+msg;
	}

	private void impostaUscita(String stanzaDa, String dir, String nomeA) throws FormatoFileNonValidoException {
		check(isStanzaValida(stanzaDa),"Stanza di partenza sconosciuta "+dir);
		check(isStanzaValida(nomeA),"Stanza di destinazione sconosciuta "+ dir);
		this.builder.addAdiacenza(stanzaDa, nomeA, dir);
	}


	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore) throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException("Formato file non valido [" + this.reader.getLineNumber() + "] "+messaggioErrore);		
	}

	public Stanza getStanzaIniziale() {
		return this.builder.getLabirinto().getStanzaIniziale();
	}

	public Stanza getStanzaVincente() {
		return this.builder.getLabirinto().getStanzaVincente();
	}

	public Labirinto getLabirinto() {
		return this.builder.getLabirinto();
	}
}