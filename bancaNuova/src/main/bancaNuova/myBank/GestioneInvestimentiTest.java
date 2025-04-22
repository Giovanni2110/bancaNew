package myBank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class GestioneInvestimentiTest {

    private GestioneInvestimenti gestioneInvestimenti;
    private Utente utente;

    @BeforeEach
    void setUp() {
        // Inizializzazione degli oggetti per ogni test
        gestioneInvestimenti = new GestioneInvestimenti();
        utente = new Utente("Marco", 1000.0, 500.0); // Utente con 1000 nella banca e 500 nel portafoglio
    }

 // Test per il metodo investimento: verificare che l'investimento venga creato correttamente
    @Test
    void testInvestimento_CreazioneInvestimento() {
        // Simuliamo un investimento di 100 con un rischio del 10% per 6 mesi
        Investimento investimento = gestioneInvestimenti.investimento(100.0, 10, 6, utente);
        
        // Verifica che l'investimento sia stato creato
        assertNotNull(investimento, "L'investimento non è stato creato correttamente");
        assertEquals(100.0, investimento.getSoldi(), "L'importo dell'investimento non è corretto");
        assertEquals(10, investimento.getRischio(), "Il rischio dell'investimento non è corretto");
        assertEquals(6, investimento.getDurata(), "La durata dell'investimento non è corretta");

        // Verifica che il saldo bancario sia stato aggiornato correttamente (l'utente ha investito 100)
        assertEquals(900.0, utente.getContoBanca(), "Il saldo bancario dell'utente non è stato aggiornato correttamente");
    }

    // Test per il metodo investimento: verificare che non sia possibile fare più di 10 investimenti
    @Test
    void testInvestimento_NumeroMassimoInvestimenti() {
        // Creiamo 10 investimenti
        for (int i = 0; i < 10; i++) {
            gestioneInvestimenti.investimento(100.0, 10, 6, utente);
        }

        // Il prossimo investimento dovrebbe restituire null, poiché sono già stati fatti 10 investimenti
        Investimento investimento = gestioneInvestimenti.investimento(100.0, 10, 6, utente);
        assertNull(investimento, "Non dovrebbe essere possibile fare più di 10 investimenti");
    }
    // Test per il metodo gestisciInvestimenti: verificare che gli investimenti vengano gestiti correttamente
    @Test
    void testGestisciInvestimenti_DurataInvestimento() {
        // Aggiungiamo un investimento con durata di 1 mese
        gestioneInvestimenti.investimento(100.0, 10, 1, utente);
        
        // Simuliamo la gestione degli investimenti
        gestioneInvestimenti.gestisciInvestimenti(utente);
        
        // Verifica che il saldo sia stato aggiornato correttamente (investimento concluso)
        assertTrue(utente.getContoBanca() != 1000.0, "Il saldo bancario dell'utente dovrebbe essere cambiato dopo la gestione dell'investimento");
    }

    // Test per il metodo ordina: verificare che gli investimenti vengano ordinati per durata
    @Test
    void testOrdinaInvestimenti() {
        // Aggiungiamo investimenti con durate diverse
        gestioneInvestimenti.investimento(100.0, 10, 3, utente);
        gestioneInvestimenti.investimento(200.0, 5, 5, utente);
        gestioneInvestimenti.investimento(300.0, 15, 1, utente);
        
        // Ordinamento degli investimenti
        gestioneInvestimenti.ordina();
        
		// Verifica che gli investimenti siano ordinati in base alla durata (
	}
}