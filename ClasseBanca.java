import java.util.Scanner;

public class ClasseBanca {
	static Scanner tastiera = new Scanner(System.in);

	public static void menu(int mese) {
		System.out.println("\n--------------MENU--------------   Mese n°" + mese);
		System.out.println("1 - Deposita");
		System.out.println("2 - Preleva");
		System.out.println("3 - Visualizza Conto");
		System.out.println("4 - Visualizza Portafoglio");
		System.out.println("5 - Investi");
		System.out.println("6 - Vai al mese successivo");
		System.out.println("0 - Esci");
	}

	public static void menuInvestimentiDurata() {
		System.out.println("\nScegli la durata del tuo investimento:");
		System.out.println("1 - Breve Durata");
		System.out.println("2 - Media Durata");
		System.out.println("3 - Lunga Durata");
	}

	public static void menuInvestimentiRischio() {
		System.out.println("\nInserisci il rischio/guadagno del tuo investimento");
		System.out.println("1 - Basso Rischio, Basso Guadagno");
		System.out.println("2 - Medio Rischio, Medio Guadagno");
		System.out.println("3 - Alto Rischio, Alto Guadagno");
	}

	public static int sceltaDurata() {

		int durata = 0;

		menuInvestimentiDurata();

		int sceltaDurata = 0;
		do {
			System.out.print("Inserisci scelta:");
			String sceltaDurataString = tastiera.nextLine();
			sceltaDurata = convertiInt(sceltaDurataString);
		} while (sceltaDurata < 1 || sceltaDurata > 3);

		switch (sceltaDurata) {
		case 1:
			durata = 3;
			break;
		case 2:
			durata = 6;
			break;
		case 3:
			durata = 12;
			break;
		}
		return durata;
	}

	public static int sceltaRischi(int durata) {

		menuInvestimentiRischio();
		int sceltaRischio = 0;
		int percentualeRischio = 0;
		if (durata > 3) {
			percentualeRischio = 15;
		}
		if (durata > 6) {
			percentualeRischio = 30;
		}

		do {
			System.out.print("Inserisci scelta:");
			String sceltaRischioString = tastiera.nextLine();
			sceltaRischio = convertiInt(sceltaRischioString);
		} while (sceltaRischio < 1 || sceltaRischio > 3);

		switch (sceltaRischio) {
		case 1:
			percentualeRischio += 10;
			break;

		case 2:
			percentualeRischio += 50;
			break;

		case 3:
			percentualeRischio += 100;
			break;
		}
		return percentualeRischio;
	}

	public static double investimento(double soldiInvestiti, int percentualeRischio) {

		double vincitaPerdita = Math.random() * 100 + 1;
		double soldiFinali;
		if (vincitaPerdita > 50) {
			double percentuale = soldiInvestiti * percentualeRischio / 100;
			soldiFinali = soldiInvestiti + percentuale;
		} else {
			double percentuale = soldiInvestiti * percentualeRischio / 100;
			soldiFinali = soldiInvestiti - percentuale;
		}
		return soldiFinali;
	}

	public static int convertiInt(String sceltaString) {
		int sceltaInt = -1;

		try {
			sceltaInt = Integer.parseInt(sceltaString);
		} catch (NumberFormatException e) {
			System.out.println("Scelta non valida!");
			return sceltaInt;
		}
		return sceltaInt;
	}

	public static double convertiDouble(String importoString) {
		double importoDouble = -1;

		try {
			importoDouble = Double.parseDouble(importoString);
		} catch (NumberFormatException e) {
			System.out.println("Scelta non valida!");
			return importoDouble;
		}
		return importoDouble;
	}

	public static double gestisciImporti(double conto, double portafoglio, boolean prelievoBool) {

		double importo = 0;
		while (importo <= 0) {
			System.out.println("\nQuanti soldi vuoi importare?");
			String prelievoString = tastiera.nextLine();
			importo = convertiDouble(prelievoString);
		}

		if (prelievoBool && importo > conto) {
			return -1;
		}

		if (!prelievoBool && importo > portafoglio) {
			return -1;
		}

		return importo;
	}

	public static void ordina(int durate[], double inv[], int n) {
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - 1 - i; j++) {
				if (durate[j] < durate[j + 1]) {
					int temp = durate[j];
					durate[j] = durate[j + 1];
					durate[j + 1] = temp;
					double temp1 = inv[j];
					inv[j] = inv[j + 1];
					inv[j + 1] = temp1;
				}
			}
		}
	}

	public static void main(String[] args) {

		int mese = 1;
		String sceltaString;
		int scelta;
		double portafoglio = 100, conto = 0;
		double[] arrayInvestimenti = new double[10];
		int[] arrayDurate = new int[10];
		int n = 0;

		do {

			menu(mese);

			do {
				System.out.print("Inserisci scelta:");
				sceltaString = tastiera.nextLine();
				scelta = convertiInt(sceltaString);
			} while (scelta < 0 || scelta > 6);

			switch (scelta) {
			case 1:
				double deposito = gestisciImporti(conto, portafoglio, false);
				if (deposito != -1) {
					conto += deposito;
					portafoglio -= deposito;
					System.out.println("Deposito avvenuto con successo\n\n\n");
				} else
					System.out.println("Deposito fallito! Fondi insufficienti\n\n\n");
				break;

			case 2:
				double prelievo = gestisciImporti(conto, portafoglio, true);
				if (prelievo != -1) {
					conto -= prelievo;
					portafoglio += prelievo;
					System.out.println("Prelievo avvenuto con successo\n\n\n");
				} else
					System.out.println("Prelievo fallito! Fondi insufficienti\n\n\n");
				break;

			case 3:
				System.out.println("\nConto: " + conto + " euro\n\n\n");
				break;

			case 4:
				System.out.println("\nPortafoglio: " + portafoglio + " euro\n\n\n");
				break;

			case 5:

				n++;
				if (n > 10) {
					System.out.println("Non è possibile fare ulteriori investimenti");
					break;
				}

				String investitiString;
				double investiti = 0;
				do {
					System.out.println("Quanti soldi vuoi investire?");
					investitiString = tastiera.next();
					investiti = convertiDouble(investitiString);
				} while (investiti > conto || investiti <= 0);
				conto -= investiti;
				int durata = sceltaDurata();
				arrayDurate[n - 1] = durata;
				int rischio = sceltaRischi(durata);
				arrayInvestimenti[n - 1] = investimento(investiti, rischio);

				break;

			case 6:
				mese += 1;
				portafoglio += 100;

				ordina(arrayDurate, arrayInvestimenti, n);

				for (int i = 0; i < n; i++) {
					arrayDurate[i] -= 1;
					System.out.println("Hai un investimento in corso che terminera' tra " + arrayDurate[i] + " mesi");
					if (arrayDurate[i] == 0) {
						System.out.println("Investimento terminato");
						n -= 1;
						conto += arrayInvestimenti[i];
					}
				}

				System.out.println("\n\n");

				break;
			}
		} while (scelta != 0);
		tastiera.close();
	}
}
