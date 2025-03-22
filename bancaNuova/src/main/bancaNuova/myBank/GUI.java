package myBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI {
    private JFrame frame;
    private Utente utente;
    private GestioneUtenti gestioneU = new GestioneUtenti();
    private GestioneInvestimenti gestioneI = new GestioneInvestimenti();
    
    public static void main(String[] args) {
        // Avvia la GUI nell'Event Dispatch Thread
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gestione Bancaria");

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        frame.getContentPane().add(mainPanel);

        // Login Panel
        JPanel loginPanel = new JPanel();
        mainPanel.add(loginPanel, "Login");
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JLabel lblUsername = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    utente = gestioneU.Accesso();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (utente != null) {
                    cardLayout.show(mainPanel, "MainMenu");
                } else {
                    JOptionPane.showMessageDialog(frame, "Login fallito! Verifica credenziali.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(lblUsername);
        loginPanel.add(usernameField);
        loginPanel.add(lblPassword);
        loginPanel.add(passwordField);
        loginPanel.add(btnLogin);

        // Main Menu Panel
        JPanel menuPanel = new JPanel();
        mainPanel.add(menuPanel, "MainMenu");
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JButton btnDeposito = new JButton("Deposita");
        JButton btnPrelievo = new JButton("Preleva");
        JButton btnConto = new JButton("Visualizza Conto");
        JButton btnPortafoglio = new JButton("Visualizza Portafoglio");
        JButton btnInvesti = new JButton("Investi");
        JButton btnSuccessivo = new JButton("Mese Successivo");

        btnDeposito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	double n = utente.getContoPortafoglio();
                deposito();
                utente.aggiorna(utente.getNome());
				String operazione = "Hai depositato " +String.valueOf(n-utente.getContoPortafoglio()) +"£";
				utente.registraOperazione(operazione, utente.getNome());
            }
        });

        btnPrelievo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	double n = utente.getContoBanca();
                prelievo();
                utente.aggiorna(utente.getNome());
				String operazione = "Hai prelevato " +String.valueOf(n-utente.getContoBanca()) +"£";
				utente.registraOperazione(operazione, utente.getNome());
            }
            
        });

        btnConto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Conto Banca: " + utente.getContoBanca() + "€", "Conto", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnPortafoglio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Portafoglio: " + utente.getContoPortafoglio() + "€", "Portafoglio", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnInvesti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                investimento();
            }
        });

        btnSuccessivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				gestioneI.aggiornaPortafoglio(utente);
                meseSuccessivo();
                gestisciInvestimenti();
                gestioneI.ordina();
            }

			
        });

        menuPanel.add(btnDeposito);
        menuPanel.add(btnPrelievo);
        menuPanel.add(btnConto);
        menuPanel.add(btnPortafoglio);
        menuPanel.add(btnInvesti);
        menuPanel.add(btnSuccessivo);
    }

    // Implementa i metodi per deposito, prelievo, investimento, meseSuccessivo
    private void deposito() {
        String importoString = JOptionPane.showInputDialog(frame, "Quanto vuoi depositare?", "Deposito", JOptionPane.PLAIN_MESSAGE);
        double importo = Tools.convertiDouble(importoString);
        if (importo > 0) {
            double saldoDopoDeposito = utente.deposito(importo);
            if (saldoDopoDeposito != -1) {
                JOptionPane.showMessageDialog(frame, "Deposito avvenuto con successo. Saldo aggiornato.", "Deposito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Deposito fallito. Fondi insufficienti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prelievo() {
        String importoString = JOptionPane.showInputDialog(frame, "Quanto vuoi prelevare?", "Prelievo", JOptionPane.PLAIN_MESSAGE);
        double importo = Tools.convertiDouble(importoString);
        if (importo > 0) {
            double saldoDopoPrelievo = utente.prelievo(importo, utente.getContoPortafoglio());
            if (saldoDopoPrelievo != -1) {
                JOptionPane.showMessageDialog(frame, "Prelievo avvenuto con successo. Saldo aggiornato.", "Prelievo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Prelievo fallito. Fondi insufficienti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void investimento() {
        double importo = Double.parseDouble(JOptionPane.showInputDialog(frame, "Quanto vuoi investire?", "Investimento", JOptionPane.PLAIN_MESSAGE));
        if (importo > 0 && importo <= utente.getContoBanca()) {
            int durata = Integer.parseInt(JOptionPane.showInputDialog(frame, "Scegli la durata dell'investimento (1: Breve, 2: Media, 3: Lunga)", "Durata", JOptionPane.PLAIN_MESSAGE));
            int rischio = Integer.parseInt(JOptionPane.showInputDialog(frame, "Scegli il rischio dell'investimento (1: Basso, 2: Medio, 3: Alto)", "Rischio", JOptionPane.PLAIN_MESSAGE));
            if (gestioneI.investimento(importo, rischio, durata, utente) != null) {
                JOptionPane.showMessageDialog(frame, "Investimento effettuato con successo.", "Investimento", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Investimento fallito.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido o saldo insufficiente.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gestisciInvestimenti() {
        Vector<Investimento> investimentiUtente = gestioneI.getInvestimenti(); // Ottieni gli investimenti dell'utente

        for (int i = 0; i < investimentiUtente.size(); i++) {
            Investimento investimento = investimentiUtente.get(i);
            investimento.setDurata(investimento.getDurata() - 1);
            
            // Mostra il messaggio di stato
            JOptionPane.showMessageDialog(frame,
                "Hai un investimento in corso che terminerà tra " + investimento.getDurata() + " mesi",
                "Investimento in corso",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            if (investimento.getDurata() == 0) {
                // Investimento terminato
                JOptionPane.showMessageDialog(frame, "Investimento terminato", "Investimento", JOptionPane.INFORMATION_MESSAGE);
                
                if (investimento.getSoldi() >= 0) {
                    String guadagno = "Hai guadagnato: " + investimento.getSoldi() + "£";
                    JOptionPane.showMessageDialog(frame, guadagno, "Guadagno", JOptionPane.INFORMATION_MESSAGE);
                    
                    String operazione = "Hai guadagnato dall'investimento " + investimento.getSoldi() + "£";
                    utente.registraOperazione(operazione, utente.getNome());
                } else {
                    String perdita = "Hai perso: " + investimento.getSoldi() + "£";
                    JOptionPane.showMessageDialog(frame, perdita, "Perdita", JOptionPane.WARNING_MESSAGE);
                    
                    String operazione = "Hai perso dall'investimento " + investimento.getSoldi() + "£";
                    utente.registraOperazione(operazione, utente.getNome());
                }

                // Aggiorna il conto e rimuovi l'investimento
                utente.setContoBanca(utente.getContoBanca() + investimento.getSoldi());
                utente.aggiorna(utente.getNome());
                
                investimentiUtente.remove(i);  // Rimuovi l'investimento dalla lista
                i--;  // Per compensare l'indice dopo la rimozione
            }
        }
    }
    
   private void meseSuccessivo() {
        JOptionPane.showMessageDialog(frame, "Avanzamento al mese successivo...", "Mese Successivo", JOptionPane.INFORMATION_MESSAGE);
    }
}

/*package myBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI {

    private JFrame frame;
    private Utente utente;
    private GestioneUtenti gestioneU = new GestioneUtenti();
    private GestioneInvestimenti gestioneI = new GestioneInvestimenti();
    private Scanner tastiera = new Scanner(System.in);
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        frame.getContentPane().add(mainPanel);

        // Login Panel
        JPanel loginPanel = new JPanel();
        mainPanel.add(loginPanel, "Login");
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JLabel lblUsername = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //String nomeUtente = usernameField.getText();
                //String password = new String(passwordField.getPassword());
                // Implementare controllo password in GestioneUtenti
                try {
					utente = gestioneU.Accesso();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                if (utente != null) {
                    cardLayout.show(mainPanel, "MainMenu");
                } else {
                    JOptionPane.showMessageDialog(frame, "Login fallito! Verifica credenziali.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(lblUsername);
        loginPanel.add(usernameField);
        loginPanel.add(lblPassword);
        loginPanel.add(passwordField);
        loginPanel.add(btnLogin);

        // Main Menu Panel
        JPanel menuPanel = new JPanel();
        mainPanel.add(menuPanel, "MainMenu");
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JButton btnDeposito = new JButton("Deposita");
        JButton btnPrelievo = new JButton("Preleva");
        JButton btnConto = new JButton("Visualizza Conto");
        JButton btnPortafoglio = new JButton("Visualizza Portafoglio");
        JButton btnInvesti = new JButton("Investi");
        JButton btnSuccessivo = new JButton("Mese Successivo");

        btnDeposito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposito();
            }
        });

        btnPrelievo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prelievo();
            }
        });

        btnConto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Conto Banca: " + utente.getContoBanca() + "€", "Conto", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnPortafoglio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Portafoglio: " + utente.getContoPortafoglio() + "€", "Portafoglio", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnInvesti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                investimento();
            }
        });

        btnSuccessivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                meseSuccessivo();
            }
        });

        menuPanel.add(btnDeposito);
        menuPanel.add(btnPrelievo);
        menuPanel.add(btnConto);
        menuPanel.add(btnPortafoglio);
        menuPanel.add(btnInvesti);
        menuPanel.add(btnSuccessivo);

    }

    private void deposito() {
        String importoString = JOptionPane.showInputDialog(frame, "Quanto vuoi depositare?", "Deposito", JOptionPane.PLAIN_MESSAGE);
        double importo = Tools.convertiDouble(importoString);
        if (importo > 0) {
            double saldoDopoDeposito = utente.deposito(importo);
            if (saldoDopoDeposito != -1) {
                JOptionPane.showMessageDialog(frame, "Deposito avvenuto con successo. Saldo aggiornato.", "Deposito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Deposito fallito. Fondi insufficienti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prelievo() {
        String importoString = JOptionPane.showInputDialog(frame, "Quanto vuoi prelevare?", "Prelievo", JOptionPane.PLAIN_MESSAGE);
        double importo = Tools.convertiDouble(importoString);
        if (importo > 0) {
            double saldoDopoPrelievo = utente.prelievo(importo, utente.getContoPortafoglio());
            if (saldoDopoPrelievo != -1) {
                JOptionPane.showMessageDialog(frame, "Prelievo avvenuto con successo. Saldo aggiornato.", "Prelievo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Prelievo fallito. Fondi insufficienti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void investimento() {
        double importo = Double.parseDouble(JOptionPane.showInputDialog(frame, "Quanto vuoi investire?", "Investimento", JOptionPane.PLAIN_MESSAGE));
        if (importo > 0 && importo <= utente.getContoBanca()) {
            int durata = Integer.parseInt(JOptionPane.showInputDialog(frame, "Scegli la durata dell'investimento (1: Breve, 2: Media, 3: Lunga)", "Durata", JOptionPane.PLAIN_MESSAGE));
            int rischio = Integer.parseInt(JOptionPane.showInputDialog(frame, "Scegli il rischio dell'investimento (1: Basso, 2: Medio, 3: Alto)", "Rischio", JOptionPane.PLAIN_MESSAGE));
            if (gestioneI.investimento(importo, rischio, durata, utente) != null) {
                JOptionPane.showMessageDialog(frame, "Investimento effettuato con successo.", "Investimento", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Investimento fallito.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Importo non valido o saldo insufficiente.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void meseSuccessivo() {
        JOptionPane.showMessageDialog(frame, "Avanzamento al mese successivo...", "Mese Successivo", JOptionPane.INFORMATION_MESSAGE);
        // Aggiungi logica per avanzamento mese e aggiornamento degli investimenti
    }
}
*/
