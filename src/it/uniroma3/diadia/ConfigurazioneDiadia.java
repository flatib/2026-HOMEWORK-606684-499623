package it.uniroma3.diadia;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurazioneDiadia {
	
	private int pesoMaxBorsa;
	private int cfuIniziali;
    private int numeroLivelli;
    
    public ConfigurazioneDiadia() throws Exception {
        Properties prop = new Properties();

        InputStream in = getClass().getClassLoader().getResourceAsStream("diadia.properties");

        if (in == null)
            throw new RuntimeException("File di configurazione non trovato");

        prop.load(in);

        this.pesoMaxBorsa = Integer.parseInt(prop.getProperty("peso_max_borsa"));
        this.cfuIniziali = Integer.parseInt(prop.getProperty("cfu_iniziali"));
        this.numeroLivelli = Integer.parseInt(prop.getProperty("numero_livelli"));
    }
    
    public int getPesoMaxBorsa() {
        return pesoMaxBorsa;
    }

    public int getCfuIniziali() {
        return cfuIniziali;
    }

    public int getNumeroLivelli() {
        return numeroLivelli;
    }
    
}
