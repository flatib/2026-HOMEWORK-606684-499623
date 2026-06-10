package it.uniroma3.diadia.ambienti;

import java.util.Comparator;

public class ComparatorePerAttrezzi implements Comparator<Stanza>{
	
	@Override
	public int compare(Stanza s1, Stanza s2) {
		return Integer.compare(s1.getAttrezzi().size(), s2.getAttrezzi().size());
	}

}
