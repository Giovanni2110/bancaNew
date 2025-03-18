package bancaNuova;

public class Portafoglio {

	private double contoPortafoglio;

	public Portafoglio(double contoPortafoglio) {
		this.contoPortafoglio = contoPortafoglio;
	}

	public double getContoPortafoglio() {
		return contoPortafoglio;
	}

	public void setContoPortafoglio(double contoPortafoglio) {
		this.contoPortafoglio = contoPortafoglio;
	}

	public String toString(double contoPortafoglio) {
		String s = "Conto nel portafoglio: " + contoPortafoglio;
		return s;
	}
}
