package bancaNuova;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		int mese = 1;
		Scanner tastiera = new Scanner(System.in);

		double contoBanca = 0;
		Banca b = new Banca(contoBanca);

		double contoPortafoglio = 100;
		Portafoglio p = new Portafoglio(contoPortafoglio);

		Menu m = new Menu(6);
		int scelta;

		do {

			scelta = m.scelta(mese);

			switch (scelta) {
			case 1:
				double importoD = 0;
				while (importoD <= 0) {
					System.out.println("\nQuanti soldi vuoi importare?");
					String prelievoString = tastiera.nextLine();
					importoD = tools.convertiDouble(prelievoString);
				}
				double temp = b.deposito(importoD, p.getContoPortafoglio());
				if (temp != -1) {
					p.setContoPortafoglio(temp);
					System.out.println("Deposito avvenuto con successo\n\n\n");
				} else
					System.out.println("Deposito fallito! Fondi insufficienti\n\n\n");
				break;

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
