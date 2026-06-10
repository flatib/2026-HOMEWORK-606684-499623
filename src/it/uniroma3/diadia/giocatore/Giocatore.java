package it.uniroma3.diadia.giocatore;

public class Giocatore {
	
	private Borsa borsa;
	private int cfu;
	
	public Giocatore(int cfuIniziali, int pesoMaxBorsa) {
        this.borsa = new Borsa(pesoMaxBorsa);
        this.cfu = cfuIniziali;
    }
	
	public Borsa getBorsa() {
		return this.borsa;
	}
	
	public int getCfu() {
		return this.cfu;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
	
	public String toString() {
		return cfu + " CFU";
	}
}
