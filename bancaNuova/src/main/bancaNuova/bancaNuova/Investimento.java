package bancaNuova;

public class Investimento {

	private int durata;
	private int rischio;
	private double soldi;

	public Investimento(double soldi, int durata, int rischio) {
		this.soldi = soldi;
		this.durata = durata;
		this.rischio = rischio;
	}

	public double getSoldi() {
		return soldi;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public int getRischio() {
		return rischio;
	}

	public void setRischio(int rischio) {
		this.rischio = rischio;
	}

	public String toString(int durata, int rischio) {
		String s = "";
		s += "Durata: " + durata;
		s += "Rischio: " + rischio;
		s += "Soldi: " + soldi;
		return s;
	}
}
