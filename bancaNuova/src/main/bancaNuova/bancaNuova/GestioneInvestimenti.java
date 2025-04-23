package myBank;

import java.util.Vector;

public class GestioneInvestimenti {

	private Vector<Investimento> arrayInvestimenti = new Vector<Investimento>();
	
	public Vector<Investimento> getInvestimenti() {
        return arrayInvestimenti;
    }

	public Investimento investimento(double soldiInvestiti, int percentualeRischio, int durata, Utente utente) {
		if (arrayInvestimenti.size() > 10) {
			System.out.println("Non è possibile fare ulteriori investimenti");
		} else {
			utente.setContoBanca(utente.getContoBanca() - soldiInvestiti);
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

	public void gestisciInvestimenti(Utente utente) {

		for (int i = 0; i < arrayInvestimenti.size(); i++) {
			arrayInvestimenti.get(i).setDurata(arrayInvestimenti.get(i).getDurata() - 1);
			System.out.println("Hai un investimento in corso che terminera' tra " + arrayInvestimenti.get(i).getDurata()
					+ " mesi");
			if (arrayInvestimenti.get(i).getDurata() == 0) {
				System.out.println("Investimento terminato");
				if(arrayInvestimenti.get(i).getSoldi() >= 0) {
					System.out.println("Hai guadagnato: " +arrayInvestimenti.get(i).getSoldi() +"£");
					String operazione = "Hai guadagnato dall'investimento " +String.valueOf(arrayInvestimenti.get(i).getSoldi() +"£");
					utente.registraOperazione(operazione, utente.getNome());
				}// if
				else {
					System.out.println("Hai perso: " +arrayInvestimenti.get(i).getSoldi() +"£");
					String operazione = "Hai guadagnato dall'investimento " +String.valueOf(arrayInvestimenti.get(i).getSoldi() +"£");
					utente.registraOperazione(operazione, utente.getNome());
				}// if
				utente.setContoBanca(utente.getContoBanca() + arrayInvestimenti.get(i).getSoldi());
				utente.aggiorna(utente.getNome());
				arrayInvestimenti.remove(i);
			}
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

	public void aggiornaPortafoglio(Utente utente) {
		utente.setContoPortafoglio(utente.getContoPortafoglio() + 100);
	}

}
