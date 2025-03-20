package myBank;

import java.io.*;
import java.util.*;

public class Utente {

	private String nome;
	private static File transazioni = new File("C:\\Users\\Lenovo\\OneDrive\\Documenti\\BANCACANTON\\account.txt");
	private double contoPortafoglio;
	private double contoBanca;

	public Utente(String nome, double contoPortafoglio, double contoBanca) {
		this.nome = nome;
		this.contoPortafoglio = contoPortafoglio;
		this.contoBanca = contoBanca;
		try {
			transazioni.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getNome() {
		return nome;
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
			return contoPortafoglio;
		} else {
			return -1;
		}

	}

	public double prelievo(double importo, double contoPortafoglio) {
		double prelievo = gestisciImporti(importo, true);
		if (prelievo != -1) {
			contoBanca -= prelievo;
			contoPortafoglio += prelievo;
			return contoPortafoglio;
		} else {
			return -1;
		}

	}

	public void aggiorna() {
		String nuovaRiga1 = String.valueOf(contoPortafoglio) + String.valueOf(contoBanca);

		Vector<String> righe = new Vector<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(transazioni))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				righe.add(linea);
			} // while
		} // try
		catch (IOException e) {
			e.printStackTrace();
		} // catch

		if (righe.size() > 0) {
			righe.set(0, nuovaRiga1);
		} // if

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(transazioni))) {
			for (int i = 0; i < righe.size(); i++) {
				writer.write(righe.get(i));
				writer.newLine();
			} // for
		} // try
		catch (IOException e) {
			e.printStackTrace();
		} // catch

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(transazioni))) {
			for (int i = 0; i < righe.size(); i++) {
				writer.write(righe.get(i));
				writer.newLine();
			} // for
		} // try
		catch (IOException e) {
			e.printStackTrace();
		} // catch

	}
	
	public void creazione() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(transazioni))) {
			writer.write(100 + ";" + 0);
			writer.newLine();
		} // try
		catch (IOException e) {
			e.printStackTrace();
		} // catch
	}

	public void registraOperazione(String operazione) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(transazioni, true))) {
			writer.write(operazione);
			writer.newLine();
		} // try
		catch (IOException e) {
			e.printStackTrace();
		} // catch
	}

	public static Utente leggiUtenteDaFile(String nomeUtente) {
		try (BufferedReader reader = new BufferedReader(new FileReader(transazioni))) {
			String linea = reader.readLine();// legge la prima riga
			if (linea != null) {
				String[] numeri = linea.split(";");// divide i numeri separati da spazio

				if (numeri.length >= 2) {
					double contoBanca = Double.parseDouble(numeri[0]);
					double contoPortafoglio = Double.parseDouble(numeri[1]);
					Utente nuovo = new Utente(nomeUtente, contoPortafoglio, contoBanca);
					return nuovo;
				} // if
			} // if
		} // try
		catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		} // catch
		return null;
	}

	@Override
	public String toString() {
		String s = "";
		s += "Conto in banca: " + contoBanca + "£";
		s += "Conto portafoglio: " + contoPortafoglio + "£";
		return s;
	}

}
