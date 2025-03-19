package bancaNuova;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner tastiera = new Scanner(System.in);

		System.out.print("Ciao, vuoi accedere o registrarti?(a o r)");
		String s = tastiera.nextLine();
		while (s != 'a' || s != 'r') {
			System.out.print("Errore, reinserisci scelta!");
			System.out.print("Vuoi accedere o registrarti?(a o r)");
			String s = tastiera.nextLine();
		} // while

		private static final File datiUtenti = new File("datiUtenti.txt");

		if (s == 'a') {
			System.out.print("Inserisci nome utente: ");
			String nomeUtente = tastiera.nextLine();

			try (BufferedReader reader = new BufferedReader(new FileReader(datiUtenti))) {
				String linea = reader.readLine();
				if ((linea = reader.readLine()) == nomeUtente) { // legge ogni riga del file
					break; // fermo il ciclo
				} // if
				else
					return;
			} // try
			catch (IOException e) {
				e.printStackTrace();
			} // catch

			System.out.print("Inserisci password: ");
			String password = tastiera.nextLine();

			try (BufferedReader reader = new BufferedReader(new FileReader(datiUtenti))) {
				String linea = reader.readLine();
				if ((linea = reader.readLine()) == password) { // legge ogni riga del file
					break; // fermo il ciclo
				} // if
				else
					return;
			} // try
			catch (IOException e) {
				e.printStackTrace();
			} // catch
			System.out.print("Accesso avvenuto correttamente!");
		} // if
		else if (s == 'r') {
			System.out.print("Inserisci nome utente: ");
			String nomeUtente = tastiera.nextLine();
			System.out.print("Inserisci password: ");
			String password = tastiera.nextLine();

			System.out.print("Sei sicuro delle credenziali?(s o n)");
			String s = tastiera.nextLine();
			while (s != 's' || s != 'n') {
				System.out.print("Errore, reinserisci scelta!");
				System.out.print("Sei sicuro delle credenziali?(s o n)");
				String s = tastiera.nextLine();
			} // while

			if (s == 's') {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(datiUtenti, true))) {// true per non
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
			} // if
			while (s == 'n') {
				System.out.print("Inserisci nome utente: ");
				String nomeUtente = tastiera.nextLine();
				System.out.print("Inserisci password: ");
				String password = tastiera.nextLine();

				System.out.print("Sei sicuro delle credenziali?(s o n)");
				String s = tastiera.nextLine();
				while (s != 's' || s != 'n') {
					System.out.print("Errore, reinserisci scelta!");
					System.out.print("Sei sicuro delle credenziali?(s o n)");
					String s = tastiera.nextLine();
				} // while
			} // while
		} // else if

		int mese = 1;

		Menu m = new Menu(6);
		int scelta;

		do {

			scelta = m.scelta(mese);

			switch (scelta) {

			case 1: {
				double importoD = 0;
				System.out.println("\nQuanti soldi vuoi depositare?");
				String prelievoString = tastiera.nextLine();
				importoD = tools.convertiDouble(prelievoString);
				double temp = Utente.deposito(importoD);
				if (temp != -1) {
					Utente.setContoPortafoglio(temp);
					System.out.println("Deposito avvenuto con successo\n\n\n");
				} // if
				else {
					System.out.println("Deposito fallito! Fondi insufficienti\n\n\n");
				} // else
				break;
			} // case 1

			case 2:
				double importoP = 0;
				while (importoP <= 0) {
					System.out.println("\nQuanti soldi vuoi prelevare?");
					String prelievoString = tastiera.nextLine();
					importoP = tools.convertiDouble(prelievoString);
				}
				double temp1 = b.prelievo(importoP, p.getContoPortafoglio());
				if (temp1 != -1) {
					p.setContoPortafoglio(temp1);
					System.out.println("Prelievo avvenuto con successo\n\n\n");
				} else {
					System.out.println("Prelievo fallito! Fondi insufficienti\n\n\n");
				}
				break;

			case 3:
				System.out.println("\nConto: " + b.getContoBanca() + " euro\n\n\n");
				break;

			case 4:
				System.out.println("\nPortafoglio: " + p.getContoPortafoglio() + " euro\n\n\n");
				break;

			case 5:

				double soldi;

				do {
					System.out.println("Quanti soldi vuoi investire?");
					String investitiString = tastiera.nextLine();
					soldi = tools.convertiDouble(investitiString);
				} while (soldi > b.getContoBanca() || soldi <= 0);

				int sceltaDellaDurata;
				sceltaDellaDurata = m.sceltaDurata();
				int sceltaDelRischio;
				sceltaDelRischio = m.sceltaRischi(sceltaDellaDurata);
				System.out.println(sceltaDellaDurata);
				if (b.investimento(soldi, sceltaDelRischio, sceltaDellaDurata) != null) {
					System.out.println("Investimento andato a buon fine!!!");
				} else {
					System.out.println("Investimento non riuscito!!!");
				}

				break;

			case 6:
				mese += 1;
				p.setContoPortafoglio(p.getContoPortafoglio() + 100);

				b.ordina();
				b.gestisciInvestimenti();
				break;
			}
		} while (scelta != 0);
		tastiera.close();
	}
}
