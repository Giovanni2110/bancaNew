
import java.io.*;
import java.util.*;

public class GestioneUtenti {
	Scanner tastiera = new Scanner(System.in);

	public static final File fileUtenti = new File("datiUtenti.txt");

	// costruttore
	public GestioneUtenti() {

	}

	public Utente cercaUtente(String nomeUtente) {
		for (int i = 0; i < utenti.size(); i++) {
			if (utenti.get(i).getNome().equals(nomeUtente)) {
				return utenti.get(i);
			}
		}
		return null;
	}

	public Utente Accesso() {

		System.out.print("Ciao, vuoi accedere o registrarti?(a o r)");
		String s = tastiera.nextLine();
		while (s != "a" || s != "r") {
			System.out.print("Errore, reinserisci scelta!");
			System.out.print("Vuoi accedere o registrarti?(a o r)");
			s = tastiera.nextLine();
		} // while

		if (s == "a") {
			System.out.print("Inserisci nome utente: ");
			String nomeUtente = tastiera.nextLine();

			try (BufferedReader reader = new BufferedReader(new FileReader(fileUtenti))) {
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

			try (BufferedReader reader = new BufferedReader(new FileReader(fileUtenti))) {
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

			Utente u = cercaUtente(nomeUtente);
			System.out.print("Accesso avvenuto correttamente!");
			return u;
		} // if
		else if (s == "r") {
			System.out.print("Inserisci nome utente: ");
			String nomeUtente = tastiera.nextLine();
			System.out.print("Inserisci password: ");
			String password = tastiera.nextLine();

			System.out.print("Sei sicuro delle credenziali?(s o n)");
			s = tastiera.nextLine();
			while (s != "s" || s != "n") {
				System.out.print("Errore, reinserisci scelta!");
				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
			} // while

			if (s == "s") {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileUtenti, true))) {// true per non
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
				return utente;
			} // if
			while (s == "n") {
				System.out.print("Inserisci nome utente: ");
				nomeUtente = tastiera.nextLine();
				System.out.print("Inserisci password: ");
				password = tastiera.nextLine();

				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
				while (s != "s" || s != "n") {
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
