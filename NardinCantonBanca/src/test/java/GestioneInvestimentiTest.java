package myBank.test;

import myBank.GUI;
import myBank.GestioneInvestimenti;
import myBank.Investimento;
import myBank.Utente;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GestioneInvestimentiJUnit4Test {

    private GestioneInvestimenti gestioneInvestimenti;
    private Utente mockUtente;
    private GUI mockGui;

    @Before
    public void setUp() {
        gestioneInvestimenti = new GestioneInvestimenti();
        gestioneInvestimenti.arrayInvestimenti = new ArrayList<>();
        mockUtente = Mockito.mock(Utente.class);
        mockGui = Mockito.mock(GUI.class);
        when(mockUtente.getNome()).thenReturn("testuser");
        when(mockUtente.getContoBanca()).thenReturn(1000.0);
    }

    // --- Test per la funzione investimento ---

    @Test
    public void investimento_limiteInvestimentiRaggiunto() {
        for (int i = 0; i < 10; i++) {
            gestioneInvestimenti.arrayInvestimenti.add(new Investimento(100, 1, 10, 100));
        }
        assertFalse(gestioneInvestimenti.investimento(100, 10, 1, mockUtente));
    }

    @Test
    public void investimento_datiNonValidi() {
        assertFalse(gestioneInvestimenti.investimento(0, 10, 1, mockUtente));
        assertFalse(gestioneInvestimenti.investimento(-50, 10, 1, mockUtente));
        assertFalse(gestioneInvestimenti.investimento(100, 10, 0, mockUtente));
        assertFalse(gestioneInvestimenti.investimento(100, 10, -1, mockUtente));
        assertFalse(gestioneInvestimenti.investimento(100, 0, 1, mockUtente));
        assertFalse(gestioneInvestimenti.investimento(100, -5, 1, mockUtente));
        assertFalse(gestioneInvestimenti.investimento(100, 101, 1, mockUtente));
    }

    @Test
    public void investimento_fondiInsufficienti() {
        when(mockUtente.getContoBanca()).thenReturn(50.0);
        assertFalse(gestioneInvestimenti.investimento(100, 10, 1, mockUtente));
    }

    @Test
    public void investimento_successo() {
        assertTrue(gestioneInvestimenti.investimento(100, 50, 1, mockUtente));
        verify(mockUtente, times(1)).setContoBanca(eq(900.0));
        assertEquals(1, gestioneInvestimenti.arrayInvestimenti.size());
        assertEquals(100.0, gestioneInvestimenti.arrayInvestimenti.get(0).getSoldiInvestitiIniziali(), 0.001);
        assertEquals(1, gestioneInvestimenti.arrayInvestimenti.get(0).getDurata());
        assertEquals(50, gestioneInvestimenti.arrayInvestimenti.get(0).getPercentualeRischio());
    }

    // --- Test per la funzione gestisciInvestimenti ---

    @Test
    public void gestisciInvestimenti_nessunInvestimento() {
        gestioneInvestimenti.gestisciInvestimenti(mockUtente, mockGui);
        verifyNoInteractions(mockGui);
        verifyNoInteractions(mockUtente);
        assertTrue(gestioneInvestimenti.arrayInvestimenti.isEmpty());
    }

    @Test
    public void gestisciInvestimenti_investimentoInCorso() {
        Investimento investimento = new Investimento(110.0, 3, 10, 100.0);
        gestioneInvestimenti.arrayInvestimenti.add(investimento);

        gestioneInvestimenti.gestisciInvestimenti(mockUtente, mockGui);

        assertEquals(2, investimento.getDurata());
        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento in corso"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verifyNoMoreInteractions(mockGui);
        verifyNoInteractions(mockUtente);
        assertEquals(1, gestioneInvestimenti.arrayInvestimenti.size());
    }

    @Test
    public void gestisciInvestimenti_investimentoTerminatoConGuadagno() {
        Investimento investimento = new Investimento(120.0, 1, 10, 100.0);
        gestioneInvestimenti.arrayInvestimenti.add(investimento);

        gestioneInvestimenti.gestisciInvestimenti(mockUtente, mockGui);

        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento in corso"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento terminato"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockGui, times(1)).mostraMessaggio(eq("Guadagno"), eq("Hai guadagnato: 20.00€"), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockUtente, times(1)).setContoBanca(eq(1120.0));
        verify(mockUtente, times(1)).registraOperazione(eq("Guadagno da investimento: +20.00€"), eq("testuser"));
        verify(mockUtente, times(1)).aggiorna(eq("testuser"));
        assertTrue(gestioneInvestimenti.arrayInvestimenti.isEmpty());
    }

    @Test
    public void gestisciInvestimenti_investimentoTerminatoConPerdita() {
        Investimento investimento = new Investimento(90.0, 1, 10, 100.0);
        gestioneInvestimenti.arrayInvestimenti.add(investimento);

        gestioneInvestimenti.gestisciInvestimenti(mockUtente, mockGui);

        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento in corso"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento terminato"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockGui, times(1)).mostraMessaggio(eq("Perdita"), eq("Hai perso: 10.00€"), eq(JOptionPane.WARNING_MESSAGE));
        verify(mockUtente, times(1)).setContoBanca(eq(1090.0));
        verify(mockUtente, times(1)).registraOperazione(eq("Perdita da investimento: -10.00€"), eq("testuser"));
        verify(mockUtente, times(1)).aggiorna(eq("testuser"));
        assertTrue(gestioneInvestimenti.arrayInvestimenti.isEmpty());
    }

    @Test
    public void gestisciInvestimenti_piuInvestimenti_alcuniInCorsoAlcuniTerminati() {
        Investimento inCorso1 = new Investimento(110.0, 2, 10, 100.0);
        Investimento terminatoGuadagno = new Investimento(150.0, 1, 20, 100.0);
        Investimento inCorso2 = new Investimento(80.0, 3, 5, 100.0);
        gestioneInvestimenti.arrayInvestimenti.add(inCorso1);
        gestioneInvestimenti.arrayInvestimenti.add(terminatoGuadagno);
        gestioneInvestimenti.arrayInvestimenti.add(inCorso2);

        gestioneInvestimenti.gestisciInvestimenti(mockUtente, mockGui);

        assertEquals(1, inCorso1.getDurata());
        assertEquals(2, inCorso2.getDurata());
        assertEquals(2, gestioneInvestimenti.arrayInvestimenti.size());

        verify(mockGui, times(3)).mostraMessaggio(eq("Investimento in corso"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento terminato"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockGui, times(1)).mostraMessaggio(eq("Guadagno"), eq("Hai guadagnato: 50.00€"), eq(JOptionPane.INFORMATION_MESSAGE));

        verify(mockUtente, times(1)).setContoBanca(eq(1150.0));
        verify(mockUtente, times(1)).registraOperazione(eq("Guadagno da investimento: +50.00€"), eq("testuser"));
        verify(mockUtente, times(1)).aggiorna(eq("testuser"));
    }

    @Test
    public void gestisciInvestimenti_investimentoConZeroGuadagnoPerdita() {
        Investimento investimento = new Investimento(100.0, 1, 10, 100.0);
        gestioneInvestimenti.arrayInvestimenti.add(investimento);

        gestioneInvestimenti.gestisciInvestimenti(mockUtente, mockGui);

        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento in corso"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockGui, times(1)).mostraMessaggio(eq("Investimento terminato"), anyString(), eq(JOptionPane.INFORMATION_MESSAGE));
        verify(mockUtente, times(1)).setContoBanca(eq(1100.0));
        verify(mockUtente, times(1)).registraOperazione(eq("Guadagno da investimento: +0.00€"), eq("testuser"));
        verify(mockUtente, times(1)).aggiorna(eq("testuser"));
        assertTrue(gestioneInvestimenti.arrayInvestimenti.isEmpty());
    }
}