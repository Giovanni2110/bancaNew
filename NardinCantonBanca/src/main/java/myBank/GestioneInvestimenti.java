package myBank;

import javax.swing.JOptionPane;
import java.util.Comparator;
import java.util.Vector;

public class GestioneInvestimenti {

    private Vector<Investimento> arrayInvestimenti = new Vector<>();

    public Vector<Investimento> getInvestimenti() {
        return arrayInvestimenti;
    }

    public boolean investimento(double soldiInvestiti, int percentualeRischio, int durataMesi, Utente utente) {
        if (arrayInvestimenti.size() >= 10) {
            JOptionPane.showMessageDialog(null, "Non è possibile fare ulteriori investimenti", "Limite Investimenti", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (soldiInvestiti <= 0 || durataMesi <= 0 || percentualeRischio <= 0 || percentualeRischio > 100) {
            JOptionPane.showMessageDialog(null, "Dati di investimento non validi.", "Errore Investimento", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (utente.getContoBanca() < soldiInvestiti) {
            JOptionPane.showMessageDialog(null, "Fondi insufficienti per l'investimento.", "Errore Investimento", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            utente.setContoBanca(utente.getContoBanca() - soldiInvestiti);
            double fattoreRischio = percentualeRischio / 100.0;
            // Simula un risultato dell'investimento basato sul rischio
            double moltiplicatore = 1.0;
            if (Math.random() < fattoreRischio) {
                // Possibilità di guadagno
                moltiplicatore += Math.random() * fattoreRischio * 2; // Guadagno fino a 2 volte la percentuale di rischio
            } else {
                // Possibilità di perdita
                moltiplicatore -= Math.random() * fattoreRischio; // Perdita fino alla percentuale di rischio
                if (moltiplicatore < 0.5) moltiplicatore = 0.5; // Evita perdite troppo elevate
            }
            double soldiFinali = soldiInvestiti * moltiplicatore;

            Investimento i = new Investimento(soldiFinali, durataMesi, percentualeRischio, soldiInvestiti);
            arrayInvestimenti.add(i);
            return true;
        }
    }

    public void gestisciInvestimenti(Utente utente, GUI gui) {
        for (int i = 0; i < arrayInvestimenti.size(); ) {
            Investimento investimento = arrayInvestimenti.get(i);
            investimento.decrementaDurata();

            gui.mostraMessaggio("Investimento in corso",
                    "Investimento di " + String.format("%.2f", investimento.getSoldiInvestitiIniziali()) + "€, durata residua: " + investimento.getDurata() + " mesi",
                    JOptionPane.INFORMATION_MESSAGE);

            if (investimento.getDurata() == 0) {
                gui.mostraMessaggio("Investimento terminato", "L'investimento di " + String.format("%.2f", investimento.getSoldiInvestitiIniziali()) + "€ è scaduto.", JOptionPane.INFORMATION_MESSAGE);
                double guadagnoPerdita = investimento.getSoldi() - investimento.getSoldiInvestitiIniziali();
                if (guadagnoPerdita >= 0) {
                    gui.mostraMessaggio("Guadagno", "Hai guadagnato: " + String.format("%.2f", guadagnoPerdita) + "€", JOptionPane.INFORMATION_MESSAGE);
                    String operazione = "Guadagno da investimento: +" + String.format("%.2f", guadagnoPerdita) + "€";
                    utente.registraOperazione(operazione, utente.getNome());
                } else {
                    gui.mostraMessaggio("Perdita", "Hai perso: " + String.format("%.2f", Math.abs(guadagnoPerdita)) + "€", JOptionPane.WARNING_MESSAGE);
                    String operazione = "Perdita da investimento: -" + String.format("%.2f", Math.abs(guadagnoPerdita)) + "€";
                    utente.registraOperazione(operazione, utente.getNome());
                }
                utente.setContoBanca(utente.getContoBanca() + investimento.getSoldi());
                utente.aggiorna(utente.getNome());
                arrayInvestimenti.remove(i);
            } else {
                i++;
            }
        }
    }

    public void ordina() {
        arrayInvestimenti.sort(Comparator.comparingInt(Investimento::getDurata));
    }

    public void aggiornaPortafoglio(Utente utente) {
        utente.setContoPortafoglio(utente.getContoPortafoglio() + 100);
    }
}