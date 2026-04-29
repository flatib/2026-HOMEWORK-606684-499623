package it.uniroma3.diadia;

public class IOSimulator implements IO {
	
	private String[] comandi;
	private int counterComandi = 0;
	private int counterOutput = 0;
	private String[] output;
	
	public IOSimulator(int numeroOutput) {
		output = new String[numeroOutput];
	}
	
	public String[] getComando() {
		return comandi;
	}

	public void setComando(String[] comandi) {
		this.comandi = comandi;
	}

	public String[] getOutput() {
		return output;
	}

	public void setOutput(String[] output) {
		this.output = output;
	}

	@Override
	public void mostraMessaggio(String msg) {
		output[counterOutput] = msg;
		System.out.println(msg);
		counterOutput++;
	}
	
	@Override
	public String leggiRiga() {
		String riga = comandi[counterComandi];
		System.out.println(comandi[counterComandi]);
		counterComandi++;
		return riga;
	}

}
