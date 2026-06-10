package it.uniroma3.diadia.giocatore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.attrezzi.ComparatorePerNome;
import it.uniroma3.diadia.attrezzi.ComparatorePerPeso;

public class Borsa {
    public final static int DEFAULT_PESO_MAX_BORSA = 10;
    private Map<String, Attrezzo> attrezzi;
    private int pesoMax;

    public Borsa() {
        this(DEFAULT_PESO_MAX_BORSA);
    }

    public Borsa(int pesoMax) {
        this.pesoMax = pesoMax;
        this.attrezzi = new HashMap<>();
    }

    public boolean addAttrezzo(Attrezzo attrezzo) {
        if (this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
            return false;
        this.attrezzi.put(attrezzo.getNome(), attrezzo);
        return true;
    }

    public int getPesoMax() {
        return pesoMax;
    }

    public Attrezzo getAttrezzo(String nomeAttrezzo) {
        return this.attrezzi.get(nomeAttrezzo);
    }

    public int getPeso() {
        int peso = 0;
        for (Attrezzo attrezzo : this.attrezzi.values()) {
            peso += attrezzo.getPeso();
        }
        return peso;
    }

    public boolean isEmpty() {
        return this.attrezzi.isEmpty();
    }

    public boolean hasAttrezzo(String nomeAttrezzo) {
        return this.attrezzi.containsKey(nomeAttrezzo);
    }

    public Attrezzo removeAttrezzo(String nomeAttrezzo) {
        return this.attrezzi.remove(nomeAttrezzo);
    }
    
    public List<Attrezzo> getContenutoOrdinatoPerPeso() {
    	List<Attrezzo> orderedAttrezzi = new ArrayList<Attrezzo>();
    	orderedAttrezzi.addAll(this.attrezzi.values());
    	orderedAttrezzi.sort(null);
		return orderedAttrezzi;
    }
    
    public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
    	SortedSet<Attrezzo> orderedAttrezzi = new TreeSet<Attrezzo>(new ComparatorePerNome());
    	orderedAttrezzi.addAll(this.attrezzi.values());
    	return orderedAttrezzi;
    }
    
    public Map<Integer,Set<Attrezzo>> getContenutoRaggruppatoPerPeso() {
    	Map<Integer,Set<Attrezzo>> raggruppati = new HashMap<Integer,Set<Attrezzo>>();
		for (Attrezzo attrezzo : this.attrezzi.values()) {
			int peso = attrezzo.getPeso();
			if (!raggruppati.containsKey(peso))
				raggruppati.put(peso, new TreeSet<Attrezzo>(new ComparatorePerNome()));
			raggruppati.get(peso).add(attrezzo);
		}
		return raggruppati;
    }
    
    public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso() {
    	SortedSet<Attrezzo> orderedAttrezzi = new TreeSet<Attrezzo>(new ComparatorePerPeso());
		orderedAttrezzi.addAll(this.attrezzi.values());
		return orderedAttrezzi;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        if (!this.isEmpty()) {
            s.append("Contenuto borsa (" + this.getPeso() + "kg/" + this.getPesoMax() + "kg): ");
            for (Attrezzo attrezzo : attrezzi.values())
                s.append(attrezzo.toString() + " ");
        } else {
            s.append("Borsa vuota");
        }
        return s.toString();
    }
}