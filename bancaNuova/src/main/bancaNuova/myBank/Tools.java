package myBank;

import java.util.Scanner;

public class Tools {

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

	public static int inserisciIntero() {
		Scanner sc = new Scanner(System.in);

		int intero = 0;
		boolean ok;

		do {

			ok = true;
			try {

				String input = sc.nextLine().trim();
				intero = Integer.parseInt(input);

			} catch (NumberFormatException e) {
				System.out.print("\nErrore inserisci un numero valido.  --> ");
				ok = false;
			}
		} while (!ok);

		return intero;
	}

	public static String rimuoviSpazi(String s) {
		if (s == null) {
			return "";
		}

		String risultato = "";
		boolean spazioPrima = false;

		for (int i = 0; i <= s.length() - 1; i++) {
			char c = s.charAt(i);

			if (c == ' ') {
				if (!spazioPrima) {
					risultato += ' ';
					spazioPrima = true;
				}
			} else {
				risultato += c;
				spazioPrima = false;
			}
		}

		return risultato;
	}

}
