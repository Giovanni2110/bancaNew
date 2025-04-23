package myBank.test;

import myBank.Utente;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtenteGestioneImportiJUnit4Test {

    private Utente utente;

    @Before
    public void setUp() {
        utente = new Utente("testuser", "password", 1000.0, 500.0);
    }

    @Test
    public void gestisciImporti_prelievo_fondiSufficienti() {
        double importo = utente.gestisciImporti(200.0, true);
        assertEquals(200.0, importo, 0.001);
    }

    @Test
    public void gestisciImporti_prelievo_fondiInsufficienti() {
        double importo = utente.gestisciImporti(1500.0, true);
        assertEquals(-1.0, importo, 0.001);
    }

    @Test
    public void gestisciImporti_deposito_fondiSufficienti() {
        double importo = utente.gestisciImporti(100.0, false);
        assertEquals(100.0, importo, 0.001);
    }

    @Test
    public void gestisciImporti_deposito_fondiInsufficienti() {
        double importo = utente.gestisciImporti(600.0, false);
        assertEquals(-1.0, importo, 0.001);
    }

    @Test
    public void deposito_successo() {
        double nuovoSaldoPortafoglio = utente.deposito(300.0);
        assertEquals(200.0, nuovoSaldoPortafoglio, 0.001);
        assertEquals(1300.0, utente.getContoBanca(), 0.001);
        assertEquals(200.0, utente.getContoPortafoglio(), 0.001);
    }

    @Test
    public void deposito_fallimento_fondiInsufficientiPortafoglio() {
        double nuovoSaldoPortafoglio = utente.deposito(600.0);
        assertEquals(-1.0, nuovoSaldoPortafoglio, 0.001);
        assertEquals(1000.0, utente.getContoBanca(), 0.001);
        assertEquals(500.0, utente.getContoPortafoglio(), 0.001);
    }

    @Test
    public void prelievo_successo() {
        double nuovoSaldoPortafoglio = utente.prelievo(400.0, utente.getContoPortafoglio());
        assertEquals(900.0, nuovoSaldoPortafoglio, 0.001);
        assertEquals(600.0, utente.getContoBanca(), 0.001);
        assertEquals(900.0, utente.getContoPortafoglio(), 0.001);
    }

    @Test
    public void prelievo_fallimento_fondiInsufficientiBanca() {
        double nuovoSaldoPortafoglio = utente.prelievo(1200.0, utente.getContoPortafoglio());
        assertEquals(-1.0, nuovoSaldoPortafoglio, 0.001);
        assertEquals(1000.0, utente.getContoBanca(), 0.001);
        assertEquals(500.0, utente.getContoPortafoglio(), 0.001);
    }
}