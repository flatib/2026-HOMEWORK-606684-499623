package it.uniroma3.diadia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOSimulator implements IO {
	
	private List<String> comandi;
	private Map<String, List<String>> output;
	private String ultimoComando;
	
	public IOSimulator() {
		output = new HashMap<String, List<String>>();
		comandi = new ArrayList<String>();
		ultimoComando = "inizio";
	}
	
	public List<String> getComandi() {
		return comandi;
	}

	public void setComandi(List<String> comandi) {
		this.comandi = comandi;
	}

	public Map<String, List<String>> getOutput() {
		return output;
	}

	public void setOutput(Map<String, List<String>> output) {
		this.output = output;
	}

	@Override
	public void mostraMessaggio(String msg) {
		if (!output.containsKey(ultimoComando))
			output.put(ultimoComando, new ArrayList<String>());
		output.get(ultimoComando).add(msg);
		System.out.println(msg);
	}
	
	@Override
	public String leggiRiga() {
		String riga = comandi.removeFirst();
		System.out.println(riga);
		ultimoComando = riga;
		return riga;
	}

}
