package bancaNuova;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utente {
	
	private final String nomeUtente;
    private final String password;
    private static final File transazioni = new File("transazioni.txt");
	private double contoPortafoglio;
	private double contoBanca;
	
	public Utente(String nomeUtente, String password, double contoPortafoglio, double contoBanca) {
		this.nomeUtente=nomeUtente;
		this.password=password;
		this.contoPortafoglio=contoPortafoglio;
		this.contoBanca=contoBanca;
	}

	public double getContoPortafoglio() {
		return contoPortafoglio;
	}

	public void setContoPortafoglio(double contoPortafoglio) {
		this.contoPortafoglio = contoPortafoglio;
	}

	public double getContoBanca() {
		return contoBanca;
	}

	public void setContoBanca(double contoBanca) {
		this.contoBanca = contoBanca;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public String getPassword() {
		return password;
	}
	
	
	@Override
	public String toString() {
		String s = "";
		
		return s;
	}
	
}
