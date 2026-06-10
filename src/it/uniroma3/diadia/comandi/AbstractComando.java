package it.uniroma3.diadia.comandi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractComando implements Comando {
	
	private static final String PREFISSO = "Comando";
	private static final Set<String> elencoComandi = new HashSet<>();
	
	public AbstractComando() {
		String nomeClasse = this.getClass().getSimpleName();
		if (nomeClasse.startsWith(PREFISSO) && !nomeClasse.equals("ComandoNonValido")) {
			String nomeComando = nomeClasse.substring(PREFISSO.length()).toLowerCase();
			elencoComandi.add(nomeComando);
		}
	}
	
	@Override
	public void setParametro(String parametro) {}
	
	public static Set<String> getElencoComandi() {
		return Collections.unmodifiableSet(elencoComandi);
	}
	
}
