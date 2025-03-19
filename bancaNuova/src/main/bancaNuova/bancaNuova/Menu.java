
import java.util.Scanner;

public class Menu {

	private int n;
	private Scanner tastiera = new Scanner(System.in);

	public Menu(int n) {
		this.n = n;
	}

	private void stampa(int mese) {
		System.out.println("\n--------------MENU--------------   Mese n°" + mese);
		System.out.println("1 - Deposita");
		System.out.println("2 - Preleva");
		System.out.println("3 - Visualizza Conto");
		System.out.println("4 - Visualizza Portafoglio");
		System.out.println("5 - Investi");
		System.out.println("6 - Vai al mese successivo");
		System.out.println("0 - Esci");
	}

	private void menuInvestimentiDurata() {
		System.out.println("\nScegli la durata del tuo investimento:");
		System.out.println("1 - Breve Durata");
		System.out.println("2 - Media Durata");
		System.out.println("3 - Lunga Durata");
	}

	private void menuInvestimentiRischio() {
		System.out.println("\nInserisci il rischio/guadagno del tuo investimento");
		System.out.println("1 - Basso Rischio, Basso Guadagno");
		System.out.println("2 - Medio Rischio, Medio Guadagno");
		System.out.println("3 - Alto Rischio, Alto Guadagno");
	}

	public int scelta(int mese) {
		stampa(mese);
		System.out.print("\nSCEGLI --> ");
		int scelta = Tools.inserisciIntero();

		while (scelta > n || scelta < 0) {
			System.out.println("\nERRORE! ");
			System.out.print("SCEGLI --> ");
			scelta = tastiera.nextInt();
		}
		return scelta;
	}

	public int scelta1() {
		menuInvestimentiDurata();
		System.out.print("\nSCEGLI --> ");
		int scelta = Tools.inserisciIntero();

		while (scelta > n || scelta < 0) {
			System.out.println("\nERRORE! ");
			System.out.print("SCEGLI --> ");
			scelta = tastiera.nextInt();
		}
		return scelta;
	}

	public int scelta2() {
		menuInvestimentiRischio();
		System.out.print("\nSCEGLI --> ");
		int scelta = Tools.inserisciIntero();
		while (scelta > n || scelta < 0) {
			System.out.println("\nERRORE! ");
			System.out.print("SCEGLI --> ");
			scelta = tastiera.nextInt();
		}
		return scelta;
	}

	public int sceltaDurata() {

		int durata = 0;

		menuInvestimentiDurata();

		int sceltaDurata = 0;
		do {
			System.out.print("Inserisci scelta:");
			String sceltaDurataString = tastiera.nextLine();
			sceltaDurata = Tools.convertiInt(sceltaDurataString);
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

	public int sceltaRischi(int durata) {

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
			sceltaRischio = Tools.convertiInt(sceltaRischioString);
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

}
