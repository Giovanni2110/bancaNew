package myBank;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner tastiera = new Scanner(System.in);
		GestioneUtenti gestioneU = new GestioneUtenti();
		GestioneInvestimenti gestioneI = new GestioneInvestimenti();

		int mese = 1;
		Utente utente = gestioneU.Accesso();

		Menu m = new Menu(6);
		int scelta;

		do {

			scelta = m.scelta(mese);

			switch (scelta) {

			case 1: {
				double importoD = 0;
				while (importoD <= 0) {
					System.out.println("\nQuanti soldi depositare?");
					String prelievoString = tastiera.nextLine();
					importoD = Tools.convertiDouble(prelievoString);
				}
				double temp = utente.deposito(importoD);
				if (temp != -1) {
					utente.setContoPortafoglio(temp);
					System.out.println("Deposito avvenuto con successo\n\n\n");
				} // if
				else {
					System.out.println("Deposito fallito! Fondi insufficienti\n\n\n");
				} // else
				utente.aggiorna();
				String operazione = "Hai depositato " + String.valueOf(temp) + "£";
				utente.registraOperazione(operazione);
				break;
			} // case 1

			case 2:
				double importoP = 0;
				while (importoP <= 0) {
					System.out.println("\nQuanti soldi vuoi prelevare?");
					String prelievoString = tastiera.nextLine();
					importoP = Tools.convertiDouble(prelievoString);
				}
				double temp1 = utente.prelievo(importoP, utente.getContoPortafoglio());
				if (temp1 != -1) {
					utente.setContoPortafoglio(temp1);
					System.out.println("Prelievo avvenuto con successo\n\n\n");
					utente.aggiorna();
					String operazione = "Hai prelevato " + String.valueOf(temp1) + "£";
					utente.registraOperazione(operazione);
				} else {
					System.out.println("Prelievo fallito! Fondi insufficienti\n\n\n");
				}
				break;

			case 3:
				System.out.println("\nConto: " + utente.getContoBanca() + " euro\n\n\n");
				break;

			case 4:
				System.out.println("\nPortafoglio: " + utente.getContoPortafoglio() + " euro\n\n\n");
				break;

			case 5:

				double soldi;

				do {
					System.out.println("Quanti soldi vuoi investire?");
					String investitiString = tastiera.nextLine();
					soldi = Tools.convertiDouble(investitiString);
				} while (soldi > utente.getContoBanca() || soldi <= 0);

				int sceltaDellaDurata;
				sceltaDellaDurata = m.sceltaDurata();
				int sceltaDelRischio;
				sceltaDelRischio = m.sceltaRischi(sceltaDellaDurata);
				System.out.println(sceltaDellaDurata);
				if (gestioneI.investimento(soldi, sceltaDelRischio, sceltaDellaDurata, utente) != null) {
					System.out.println("Investimento andato a buon fine!!!");
				} else {
					System.out.println("Investimento non riuscito!!!");
				}

				break;

			case 6:
				mese += 1;
				utente.setContoPortafoglio(utente.getContoPortafoglio() + 100);

				gestioneI.ordina();
				gestioneI.gestisciInvestimenti(utente);
				break;
			}
		} while (scelta != 0);
		tastiera.close();
	}
}
