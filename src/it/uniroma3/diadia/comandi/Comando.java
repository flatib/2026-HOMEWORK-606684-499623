package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public interface Comando {
	/**
	s* esecuzione del comando
	*/
	public void esegui(Partita partita, IO io);
	/**
	* set parametro del comando
	*/
	public void setParametro(String parametro);
}
