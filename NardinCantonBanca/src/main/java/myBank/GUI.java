package myBank;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI {
    private JFrame frame;
    private Utente utente;
    private GestioneUtenti gestioneU = new GestioneUtenti();
    private GestioneInvestimenti gestioneI = new GestioneInvestimenti();
    private JPanel loginPanel;
    private JPanel menuPanel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        EventQueue.invokeLater(() -> {
            try {
                GUI window = new GUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public GUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gestione Bancaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout()); // Usa CardLayout per gestire le schermate

        loginPanel = createLoginPanel();
        menuPanel = createMenuPanel();

        frame.add(loginPanel, "login");
        frame.add(menuPanel, "mainMenu");

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(createTitledBorder("Login Utente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsername, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        JButton btnLogin = new JButton("Accedi");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            try {
                utente = gestioneU.Accesso();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Errore durante l'accesso.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (utente != null) {
                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "mainMenu");
                frame.setTitle("Gestione Bancaria - Utente: " + utente.getNome());
            } else {
                JOptionPane.showMessageDialog(frame, "Login fallito! Verifica le credenziali.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // BorderLayout per organizzare titolo e bottoni
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Titolo "BANCA" in alto
        JLabel titleLabel = new JLabel("BANCA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Pannello per i bottoni in basso, organizzati in una griglia 3x2
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 righe, 3 colonne, spaziature
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JButton btnDeposito = createStyledButton("Deposita");
        JButton btnPrelievo = createStyledButton("Preleva");
        JButton btnConto = createStyledButton("Visualizza Conto");
        JButton btnPortafoglio = createStyledButton("Visualizza Portafoglio");
        JButton btnInvesti = createStyledButton("Investi");
        JButton btnSuccessivo = createStyledButton("Mese Successivo");

        buttonPanel.add(btnDeposito);
        buttonPanel.add(btnPrelievo);
        buttonPanel.add(btnConto);
        buttonPanel.add(btnPortafoglio);
        buttonPanel.add(btnInvesti);
        buttonPanel.add(btnSuccessivo);

        btnDeposito.addActionListener(e -> {
            double n = utente.getContoPortafoglio();
            deposito();
            utente.aggiorna(utente.getNome());
            String operazione = "Hai depositato " + String.format("%.2f", (n - utente.getContoPortafoglio())) + "€";
            utente.registraOperazione(operazione, utente.getNome());
        });

        btnPrelievo.addActionListener(e -> {
            double n = utente.getContoBanca();
            prelievo();
            utente.aggiorna(utente.getNome());
            String operazione = "Hai prelevato " + String.format("%.2f", (n - utente.getContoBanca())) + "€";
            utente.registraOperazione(operazione, utente.getNome());
        });

        btnConto.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Conto Banca: " + String.format("%.2f", utente.getContoBanca()) + "€", "Conto", JOptionPane.INFORMATION_MESSAGE));
        btnPortafoglio.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Portafoglio: " + String.format("%.2f", utente.getContoPortafoglio()) + "€", "Portafoglio", JOptionPane.INFORMATION_MESSAGE));
        btnInvesti.addActionListener(e -> investimento());
        btnSuccessivo.addActionListener(e -> {
            gestioneI.aggiornaPortafoglio(utente);
            meseSuccessivo();
            gestioneI.gestisciInvestimenti(utente, GUI.this);
            gestioneI.ordina();
        });

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    private TitledBorder createTitledBorder(String title) {
        Border etched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder titled = BorderFactory.createTitledBorder(etched, title);
        titled.setTitleJustification(TitledBorder.CENTER);
        titled.setTitleFont(new Font("Arial", Font.BOLD, 16));
        return titled;
    }

    private void deposito() {
        String importoString = JOptionPane.showInputDialog(frame, "Inserisci l'importo da depositare:", "Deposito", JOptionPane.PLAIN_MESSAGE);
        double importo = Tools.convertiDouble(importoString);
        if (importo > 0) {
            double saldoDopoDeposito = utente.deposito(importo);
            if (saldoDopoDeposito != -1) {
                JOptionPane.showMessageDialog(frame, "Deposito completato. Nuovo saldo: " + String.format("%.2f", saldoDopoDeposito) + "€", "Deposito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Errore durante il deposito. Fondi insufficienti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prelievo() {
        String importoString = JOptionPane.showInputDialog(frame, "Inserisci l'importo da prelevare:", "Prelievo", JOptionPane.PLAIN_MESSAGE);
        double importo = Tools.convertiDouble(importoString);
        if (importo > 0) {
            double saldoDopoPrelievo = utente.prelievo(importo, utente.getContoPortafoglio());
            if (saldoDopoPrelievo != -1) {
                JOptionPane.showMessageDialog(frame, "Prelievo completato. Nuovo saldo: " + String.format("%.2f", saldoDopoPrelievo) + "€", "Prelievo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Errore durante il prelievo. Fondi insufficienti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void investimento() {
        double importo = Tools.convertiDouble(JOptionPane.showInputDialog(frame, "Inserisci l'importo da investire:", "Investimento", JOptionPane.PLAIN_MESSAGE));
        if (importo > 0 && importo <= utente.getContoBanca()) {
            int durataMesi = scegliDurataInvestimento();
            if (durataMesi == 0) return; // L'utente ha annullato

            int rischioPercentuale = scegliRischioInvestimento(durataMesi);
            if (rischioPercentuale == -1) return; // L'utente ha annullato

            if (gestioneI.investimento(importo, rischioPercentuale, durataMesi, utente)) {
                JOptionPane.showMessageDialog(frame, "Investimento effettuato con successo.", "Investimento", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Impossibile effettuare l'investimento.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido o saldo insufficiente.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int scegliDurataInvestimento() {
        String[] durataOpzioni = {"Breve (3 mesi)", "Media (6 mesi)", "Lunga (12 mesi)"};
        int sceltaDurata = JOptionPane.showOptionDialog(frame,
                "Scegli la durata dell'investimento:",
                "Durata Investimento",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                durataOpzioni,
                durataOpzioni[0]);

        switch (sceltaDurata) {
            case 0:
                return 3;
            case 1:
                return 6;
            case 2:
                return 12;
            default:
                return 0; // L'utente ha chiuso la finestra
        }
    }

    private int scegliRischioInvestimento(int durataMesi) {
        String[] rischioOpzioni = {"Basso", "Medio", "Alto"};
        int sceltaRischio = JOptionPane.showOptionDialog(frame,
                "Scegli il livello di rischio:",
                "Rischio Investimento",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                rischioOpzioni,
                rischioOpzioni[0]);

        if (sceltaRischio == JOptionPane.CLOSED_OPTION) {
            return -1; // L'utente ha chiuso la finestra
        }

        int percentualeRischio = 0;
        switch (sceltaRischio) {
            case 0: // Basso
                percentualeRischio = 5;
                break;
            case 1: // Medio
                percentualeRischio = 10;
                break;
            case 2: // Alto
                percentualeRischio = 20;
                break;
        }

        if (durataMesi > 3) {
            percentualeRischio = Math.max(percentualeRischio, 15);
        }
        if (durataMesi > 6) {
            percentualeRischio = Math.max(percentualeRischio, 30);
        }

        return percentualeRischio;
    }

    public void mostraMessaggio(String titolo, String messaggio, int tipoMessaggio) {
        JOptionPane.showMessageDialog(frame, messaggio, titolo, tipoMessaggio);
    }

    private void meseSuccessivo() {
        JOptionPane.showMessageDialog(frame, "Avanzamento al mese successivo...", "Mese Successivo", JOptionPane.INFORMATION_MESSAGE);
    }
}