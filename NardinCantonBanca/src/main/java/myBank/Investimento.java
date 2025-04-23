package myBank;

public class Investimento {

    private double soldi;
    private int durata;
    private int rischio;
    private double soldiInvestitiIniziali;

    public Investimento(double soldi, int durata, int rischio) {
        this.soldi = soldi;
        this.durata = durata;
        this.rischio = rischio;
        this.soldiInvestitiIniziali = 0;
    }

    public Investimento(double soldi, int durata, int rischio, double soldiInvestitiIniziali) {
        this.soldi = soldi;
        this.durata = durata;
        this.rischio = rischio;
        this.soldiInvestitiIniziali = soldiInvestitiIniziali;
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

    public void decrementaDurata() {
        this.durata--;
    }

    public int getRischio() {
        return rischio;
    }

    public double getSoldiInvestitiIniziali() {
        return soldiInvestitiIniziali;
    }
}