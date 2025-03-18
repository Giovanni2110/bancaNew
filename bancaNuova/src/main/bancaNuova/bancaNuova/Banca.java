package bancaNuova;

import java.util.Vector;

public class Banca {

	private double contoBanca;
	private Vector<Investimento> arrayInvestimenti = new Vector<Investimento>();

	public Banca(double contoBanca) {
		this.contoBanca = contoBanca;
	}

	public double getContoBanca() {
		return contoBanca;
	}

	public void setContoBanca(double contoBanca) {
		this.contoBanca = contoBanca;
	}

	public Investimento investimento(double soldiInvestiti, int percentualeRischio, int durata) {

		if (arrayInvestimenti.size() > 10) {
			System.out.println("Non è possibile fare ulteriori investimenti");
		} else {
			contoBanca -= soldiInvestiti;
			double vincitaPerdita = Math.random() * 100 + 1;
			double soldiFinali;
			if (vincitaPerdita > 50) {
				double percentuale = soldiInvestiti * percentualeRischio / 100;
				soldiFinali = soldiInvestiti + percentuale;
			} else {
				double percentuale = soldiInvestiti * percentualeRischio / 100;
				soldiFinali = soldiInvestiti - percentuale;
			}
			Investimento i = new Investimento(soldiFinali, durata, percentualeRischio);
			arrayInvestimenti.add(i);
			return i;
		}
		return null;
	}

	public void gestisciInvestimenti() {

		for (int i = 0; i < arrayInvestimenti.size(); i++) {
			arrayInvestimenti.get(i).setDurata(arrayInvestimenti.get(i).getDurata() - 1);
			System.out.println("Hai un investimento in corso che terminera' tra " + arrayInvestimenti.get(i).getDurata()
					+ " mesi");
			if (arrayInvestimenti.get(i).getDurata() == 0) {
				System.out.println("Investimento terminato");
				contoBanca += arrayInvestimenti.get(i).getSoldi();
				arrayInvestimenti.remove(i);
			}
		}
	}

	public double gestisciImporti(double importo, double portafoglio, boolean prelievoBool) {

		if (prelievoBool && importo > contoBanca) {
			return -1;
		}

		if (!prelievoBool && importo > portafoglio) {
			return -1;
		}

		return importo;
	}

	public double deposito(double importo, double contoPortafoglio) {
		double deposito = gestisciImporti(importo, contoPortafoglio, false);
		if (deposito != -1) {
			contoBanca += deposito;
			contoPortafoglio -= deposito;
			return contoPortafoglio;
		} else {
			return -1;
		}

	}

	public double prelievo(double importo, double contoPortafoglio) {
		double prelievo = gestisciImporti(importo, contoPortafoglio, true);
		if (prelievo != -1) {
			contoBanca -= prelievo;
			contoPortafoglio += prelievo;
			return contoPortafoglio;
		} else {
			return -1;
		}

	}

	public void ordina() {

		for (int i = 0; i < arrayInvestimenti.size() - 1; i++) {
			for (int j = 0; j < arrayInvestimenti.size() - 1 - i; j++) {
				if (arrayInvestimenti.get(j).getDurata() < arrayInvestimenti.get(j + 1).getDurata()) {
					Investimento temp = arrayInvestimenti.get(j);
					Investimento temp1 = arrayInvestimenti.get(j + 1);
					arrayInvestimenti.set((j + 1), temp);
					arrayInvestimenti.set((j), temp1);
				}

			}
		}

	}

	public String toString(double contoPortafoglio) {
		String s = "Conto nella banca: " + contoBanca;
		return s;
	}

}
