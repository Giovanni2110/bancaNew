package myBank;

import java.io.*;
import java.util.*;

public class GestioneUtenti {
	Scanner tastiera = new Scanner(System.in);

	private static final File fileUtenti = new File("datiUtenti.txt");

	// costruttore
	public GestioneUtenti() {

	}

	public Utente Accesso() {
		
		String filePath = "datiUtenti.txt";

		System.out.print("Ciao, vuoi accedere o registrarti?(a o r):");
		String s = tastiera.nextLine();
		while (s.length() > 1) {
			System.out.println("Non puoi inserire piu' di un carattere!");
			System.out.print("Vuoi accedere o registrarti?(a o r):");
			s = tastiera.nextLine();
		} // while
		while (s.charAt(0) != 'a' && s.charAt(0) != 'r') {
			System.out.print("Errore, reinserisci scelta!");
			System.out.print("Vuoi accedere o registrarti?(a o r):");
			s = tastiera.nextLine();
		} // while

		if (s.charAt(0) == 'a') {
			System.out.print("Inserisci nome utente: ");
			String nomeUtente = tastiera.nextLine();

			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String linea = reader.readLine();
				while ((linea = reader.readLine()) != null) { // legge ogni riga del file
					if (linea.equals(nomeUtente)) {
						System.out.print("Nome utente corretto!");
						break;
					} // if
				} // while
			} // try
			catch (IOException e) {
				e.printStackTrace();
			} // catch

			System.out.print("Inserisci password: ");
			String password = tastiera.nextLine();

			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String linea = reader.readLine();
				while ((linea = reader.readLine()) != null) { // legge ogni riga del file
					if (linea.equals(password)) {
						System.out.print("Password corretta!");
						break;
					} // if
				} // while
			} // try
			catch (IOException e) {
				e.printStackTrace();
			} // catch

			System.out.print("Accesso avvenuto correttamente!");
			Utente utente = Utente.leggiUtenteDaFile(nomeUtente);
			return utente;
		} // if
		else if (s.charAt(0) == 'r') {
			System.out.print("Inserisci nome utente: ");
			String nomeUtente = tastiera.nextLine();
			System.out.print("Inserisci password: ");
			String password = tastiera.nextLine();

			System.out.print("Sei sicuro delle credenziali?(s o n)");
			s = tastiera.nextLine();
			while (s.length() > 1) {
				System.out.println("Non puoi inserire piu' di un carattere!");
				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
			} // while
			while (s.charAt(0) != 's' && s.charAt(0) != 'n') {
				System.out.print("Errore, reinserisci scelta!");
				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
			} // while

			if (s.charAt(0) == 's') {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {// true per non
					// sovrascrivere
					writer.write(nomeUtente);
					writer.newLine();
					writer.write(password);
					writer.newLine();
					System.out.println("Dati salvati con successo!");
				} // try
				catch (IOException e) {
					e.printStackTrace();
				} // catch
				Utente utente = new Utente(nomeUtente, 100, 0);
				utente.creazione();
				return utente;
			} // if
			while (s.charAt(0) == 'n') {
				System.out.print("Inserisci nome utente: ");
				nomeUtente = tastiera.nextLine();
				System.out.print("Inserisci password: ");
				password = tastiera.nextLine();

				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
				while (s.charAt(0) != 's' && s.charAt(0) != 'n') {
					System.out.print("Errore, reinserisci scelta!");
					System.out.print("Sei sicuro delle credenziali?(s o n)");
					s = tastiera.nextLine();
				} // while
			} // while
			Utente utente = new Utente(nomeUtente, 100, 0);
			return utente;
		} // else if
		return null;
	}
}
