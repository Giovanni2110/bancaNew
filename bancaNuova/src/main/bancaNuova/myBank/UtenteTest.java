package myBank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

	private Utente utente;

	@BeforeEach
	void setUp() {
		// Inizializziamo un utente con un conto portafoglio di 500 e un conto banca di
		// 1000
		utente = new Utente("Marco", 500, 1000);
	}

	// Test per il metodo gestisciImporti (Deposito)
	@Test
	void testGestisciImporti_DepositoSufficiente() {
		double importo = 400;
		double risultato = utente.gestisciImporti(importo, false);
		assertEquals(400, risultato, "Il deposito dovrebbe essere valido");
	}

	@Test
	void testGestisciImporti_DepositoInsufficiente() {
		double importo = 600;
		double risultato = utente.gestisciImporti(importo, false);
		assertEquals(-1, risultato,
				"Il deposito non dovrebbe essere valido, importo maggiore del saldo del portafoglio");
	}

	// Test per il metodo gestisciImporti (Prelievo)
	@Test
	void testGestisciImporti_PrelievoSufficiente() {
		double importo = 200;
		double risultato = utente.gestisciImporti(importo, true);
		assertEquals(200, risultato, "Il prelievo dovrebbe essere valido");
	}

	@Test
	void testGestisciImporti_PrelievoInsufficiente() {
		double importo = 1500;
		double risultato = utente.gestisciImporti(importo, true);
		assertEquals(-1, risultato,
				"Il prelievo non dovrebbe essere valido, importo maggiore del saldo del conto banca");
	}

	// Test per il metodo deposito
	@Test
	void testDeposito_Valido() {
		double importo = 300;
		double saldoFinalePortafoglio = utente.deposito(importo);
		assertEquals(200, saldoFinalePortafoglio, "Il saldo del portafoglio dovrebbe diminuire dopo il deposito");
		assertEquals(1300, utente.getContoBanca(), "Il saldo del conto banca dovrebbe aumentare dopo il deposito");
	}

	@Test
	void testDeposito_Insufficiente() {
		double importo = 600;
		double saldoFinalePortafoglio = utente.deposito(importo);
		assertEquals(-1, saldoFinalePortafoglio,
				"Il deposito non dovrebbe essere effettuato se l'importo è maggiore del saldo del portafoglio");
	}

	// Test per il metodo prelievo
	@Test
	void testPrelievo_Valido() {
		double importo = 300;
		double saldoFinalePortafoglio = utente.prelievo(importo, utente.getContoPortafoglio());
		assertEquals(800, saldoFinalePortafoglio, "Il saldo del portafoglio dovrebbe aumentare dopo il prelievo");
		assertEquals(700, utente.getContoBanca(), "Il saldo del conto banca dovrebbe diminuire dopo il prelievo");
	}

	@Test
	void testPrelievo_Insufficiente() {
		double importo = 1500;
		double saldoFinalePortafoglio = utente.prelievo(importo, utente.getContoPortafoglio());
		assertEquals(-1, saldoFinalePortafoglio,
				"Il prelievo non dovrebbe essere effettuato se l'importo è maggiore del saldo del conto banca");
	}
}
