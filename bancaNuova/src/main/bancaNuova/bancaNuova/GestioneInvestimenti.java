
import java.util.Vector;

public class GestioneInvestimenti {

	private Vector<Investimento> arrayInvestimenti = new Vector<Investimento>();

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
				getContoBanca() += arrayInvestimenti.get(i).getSoldi();
				arrayInvestimenti.remove(i);
			}
		}
	}

}
