package bancaNuova;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import myGarage.Veicolo;

public class Utente {

	private String nome;
	private static final File transazioni = new File("account.txt");
	private double contoPortafoglio;
	private double contoBanca;

	public Utente(String nome, double contoPortafoglio, double contoBanca) {
		this.nome = nome;
		this.contoPortafoglio = contoPortafoglio;
		this.contoBanca = contoBanca;
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

	public double gestisciImporti(double importo, boolean prelievoBool) {

		if (prelievoBool && importo > this.contoBanca) {
			return -1;
		}

		if (!prelievoBool && importo > this.contoPortafoglio) {
			return -1;
		}

		return importo;
	}

	public double deposito(double importo) {
		double deposito = gestisciImporti(importo, false);
		if (deposito != -1) {
			this.contoBanca += deposito;
			this.contoPortafoglio -= deposito;
			return this.contoPortafoglio;
		} else {
			return -1;
		}

	}

	public double prelievo(double importo, double contoPortafoglio) {
		double prelievo = gestisciImporti(importo, contoPortafoglio, true);
		if (prelievo != -1) {
			contoBanca -= prelievo;
			contoPortafoglio += prelievo;
			return contoPortafoglio;
		} else {
			return -1;
		}

	}

	@Override
	public String toString() {
		String s = "";
		s += "Conto in banca: " + contoBanca + "£";
		s += "Conto portafoglio: " + contoPortafoglio + "£";
		return s;
	}

}
