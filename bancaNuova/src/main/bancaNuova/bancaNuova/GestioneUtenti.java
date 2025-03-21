
import java.io.*;
import java.util.*;

public class GestioneUtenti {

	Scanner tastiera = new Scanner(System.in);
	private static final File fileUtenti = new File("datiUtenti.txt");

	public Utente Accesso() throws IOException {

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
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String linea = reader.readLine();
				if (linea == null) {
					s = "r";
				} // if
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // catch

			if (s.charAt(0) == 'r') {
				System.out.println("Non c'è nessun account presente!");
				System.out.println("Ti faremo fare la registrazione :)");
			} // if
			else {
				boolean nomeUtenteTrovato = false;
				String nomeUtente = "";
				while (!nomeUtenteTrovato) {
					System.out.print("Inserisci nome utente: ");
					nomeUtente = tastiera.nextLine();
					try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath))) {
						String linea1;
						while ((linea1 = reader1.readLine()) != null) { // legge ogni riga del file
							if (linea1.equals(nomeUtente)) {
								System.out.println("Nome utente corretto!");
								nomeUtenteTrovato = true;
								break;
							} // if
						} // while
						if (nomeUtenteTrovato) {

						} // if
						else {
							System.err.println("Nome utente sbagliato! Riprova");
						} // else
					} // try
					catch (IOException e) {
						System.err.println("Errore nella lettura del file: " + e.getMessage());
					} // catch
				} // while
				nomeUtenteTrovato = false;
				while (!nomeUtenteTrovato) {
					System.out.print("Inserisci password: ");
					String password = tastiera.nextLine();
					try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath))) {
						String linea1;
						while ((linea1 = reader1.readLine()) != null) { // legge ogni riga del file
							if (linea1.equals(password)) {
								System.out.println("Password corretta!");
								nomeUtenteTrovato = true;
								break;
							} // if
						} // while
						if (nomeUtenteTrovato) {

						} // if
						else {
							System.err.println("Password sbagliata! Riprova");
						} // else
					} // try
					catch (IOException e) {
						System.err.println("Errore nella lettura del file: " + e.getMessage());
					} // catch
				} // while
				Utente utente = Utente.leggiUtenteDaFile(nomeUtente);
				return utente;
			} // else
		} // if
		if (s.charAt(0) == 'r') {
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
				utente.creazione(nomeUtente);
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
		} // if
		return null;
	}
}
