package it.uniroma3.diadia;

public enum Direzione {
	
	NORD, SUD, EST, OVEST;
	
	public static Direzione[] valori() {
        return Direzione.values();
    }

    public static Direzione getDirezione(String s) {
        try {
            return Direzione.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    @Override
    public String toString() {
		return this.name().toLowerCase();
	}
}
