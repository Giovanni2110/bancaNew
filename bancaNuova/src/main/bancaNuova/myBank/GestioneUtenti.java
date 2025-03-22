package myBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GestioneUtenti {
	private JFrame frame;

    public GestioneUtenti(){
        frame = new JFrame("Gestione Utente"); // Crea la finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Imposta la dimensione della finestra
        frame.setLocationRelativeTo(null); // Centra la finestra
    }

	Scanner tastiera = new Scanner(System.in);
	private static final File fileUtenti = new File("datiUtenti.txt");
	public Utente Accesso() throws IOException {
	    String filePath = "datiUtenti.txt";
	    String s = JOptionPane.showInputDialog(frame, "Vuoi accedere o registrarti? (a per accedere, r per registrarsi)");

	    while (s.length() > 1) {
	        JOptionPane.showMessageDialog(frame, "Non puoi inserire più di un carattere!", "Errore", JOptionPane.ERROR_MESSAGE);
	        s = JOptionPane.showInputDialog(frame, "Vuoi accedere o registrarti? (a per accedere, r per registrarsi)");
	    }

	    while (s.charAt(0) != 'a' && s.charAt(0) != 'r') {
	        JOptionPane.showMessageDialog(frame, "Errore, reinserisci la scelta!", "Errore", JOptionPane.ERROR_MESSAGE);
	        s = JOptionPane.showInputDialog(frame, "Vuoi accedere o registrarti? (a per accedere, r per registrarsi)");
	    }

	    if (s.charAt(0) == 'a') {
	        // Login: Verifica se l'utente esiste nel file
	        boolean nomeUtenteTrovato = false;
	        String nomeUtente = "";
	        while (!nomeUtenteTrovato) {
	            nomeUtente = JOptionPane.showInputDialog(frame, "Inserisci il nome utente:");
	            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	                String linea;
	                while ((linea = reader.readLine()) != null) {
	                    if (linea.equals(nomeUtente)) {
	                        nomeUtenteTrovato = true;
	                        break;
	                    }
	                }
	                if (nomeUtenteTrovato) {
	                    break;
	                } else {
	                    JOptionPane.showMessageDialog(frame, "Nome utente non trovato! Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(frame, "Errore nella lettura del file: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
	            }
	        }

	        // Verifica la password
	        boolean passwordTrovata = false;
	        while (!passwordTrovata) {
	            String password = JOptionPane.showInputDialog(frame, "Inserisci la password:");
	            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	                String linea;
	                while ((linea = reader.readLine()) != null) {
	                    if (linea.equals(password)) {
	                        passwordTrovata = true;
	                        break;
	                    }
	                }
	                if (passwordTrovata) {
	                    JOptionPane.showMessageDialog(frame, "Accesso avvenuto con successo!", "Accesso", JOptionPane.INFORMATION_MESSAGE);
	                    Utente utente = Utente.leggiUtenteDaFile(nomeUtente);
	                    return utente;
	                } else {
	                    JOptionPane.showMessageDialog(frame, "Password errata! Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(frame, "Errore nella lettura del file: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
	            }
	        }

	    } else if (s.charAt(0) == 'r') {
	        // Registrazione
	        String nomeUtente = JOptionPane.showInputDialog(frame, "Inserisci il nome utente:");
	        String password = JOptionPane.showInputDialog(frame, "Inserisci la password:");

	        String conferma = JOptionPane.showInputDialog(frame, "Sei sicuro delle credenziali? (s per sì, n per no):");
	        while (conferma.length() > 1) {
	            JOptionPane.showMessageDialog(frame, "Non puoi inserire più di un carattere!", "Errore", JOptionPane.ERROR_MESSAGE);
	            conferma = JOptionPane.showInputDialog(frame, "Sei sicuro delle credenziali? (s per sì, n per no):");
	        }
	        
	        while (conferma.charAt(0) != 's' && conferma.charAt(0) != 'n') {
	            JOptionPane.showMessageDialog(frame, "Errore, reinserisci la scelta!", "Errore", JOptionPane.ERROR_MESSAGE);
	            conferma = JOptionPane.showInputDialog(frame, "Sei sicuro delle credenziali? (s per sì, n per no):");
	        }

	        if (conferma.charAt(0) == 's') {
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
	                writer.write(nomeUtente);
	                writer.newLine();
	                writer.write(password);
	                writer.newLine();
	                JOptionPane.showMessageDialog(frame, "Registrazione completata con successo!", "Registrazione", JOptionPane.INFORMATION_MESSAGE);
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(frame, "Errore nel salvataggio dei dati: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
	            }

	            Utente utente = new Utente(nomeUtente, 100, 0);  // Crea un utente con saldo iniziale
	            utente.creazione(nomeUtente);  // Metodo per inizializzare l'utente
	            return utente;
	        } else {
	            return null;  // Ritorna null se l'utente annulla la registrazione
	        }
	    }

	    return null;
	}


	/*public Utente Accesso() throws IOException {

		String filePath = "datiUtenti.txt";

		System.out.print("Ciao, vuoi accedere o registrarti?(a o r):");
		String s = tastiera.nextLine();
		while (s.length() > 1) {
			System.out.println("Non puoi inserire piu' di un carattere!");
			System.out.print("Vuoi accedere o registrarti?(a o r):");
			s = tastiera.nextLine();
		} // while
		while (s.charAt(0) != 'a' && s.charAt(0) != 'r') {
			System.out.print("Errore, reinserisci scelta!");
			System.out.print("Vuoi accedere o registrarti?(a o r):");
			s = tastiera.nextLine();
		} // while
		if (s.charAt(0) == 'a') {
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String linea = reader.readLine();
				if (linea == null) {
					s = "r";
				} // if
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // catch

			if (s.charAt(0) == 'r') {
				System.out.println("Non c'è nessun account presente!");
				System.out.println("Ti faremo fare la registrazione :)");
			} // if
			else {
				boolean nomeUtenteTrovato = false;
				String nomeUtente = "";
				while (!nomeUtenteTrovato) {
					System.out.print("Inserisci nome utente: ");
					nomeUtente = tastiera.nextLine();
					try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath))) {
						String linea1;
						while ((linea1 = reader1.readLine()) != null) { // legge ogni riga del file
							if (linea1.equals(nomeUtente)) {
								System.out.println("Nome utente corretto!");
								nomeUtenteTrovato = true;
								break;
							} // if
						} // while
						if (nomeUtenteTrovato) {

						} // if
						else {
							System.err.println("Nome utente sbagliato! Riprova");
						} // else
					} // try
					catch (IOException e) {
						System.err.println("Errore nella lettura del file: " + e.getMessage());
					} // catch
				} // while
				Boolean passwordTrovata = false;
				while (!passwordTrovata) {
					System.out.print("Inserisci password: ");
					String password = tastiera.nextLine();
					try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath))) {
						String linea1;
						while ((linea1 = reader1.readLine()) != null) { // legge ogni riga del file
							if (linea1.equals(password)) {
								System.out.println("Password corretta!");
								passwordTrovata = true;
								break;
							} // if
						} // while
						if (passwordTrovata) {

						} // if
						else {
							System.err.println("Password sbagliata! Riprova");
						} // else
					} // try
					catch (IOException e) {
						System.err.println("Errore nella lettura del file: " + e.getMessage());
					} // catch
				} // while
				Utente utente = Utente.leggiUtenteDaFile(nomeUtente);
				return utente;
			} // else
		} // if
		if (s.charAt(0) == 'r') {
			System.out.print("Inserisci nome utente: ");
			String nomeUtente = tastiera.nextLine();
			System.out.print("Inserisci password: ");
			String password = tastiera.nextLine();

			System.out.print("Sei sicuro delle credenziali?(s o n)");
			s = tastiera.nextLine();
			while (s.length() > 1) {
				System.out.println("Non puoi inserire piu' di un carattere!");
				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
			} // while
			while (s.charAt(0) != 's' && s.charAt(0) != 'n') {
				System.out.print("Errore, reinserisci scelta!");
				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
			} // while

			if (s.charAt(0) == 's') {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {// true per non
					// sovrascrivere
					writer.write(nomeUtente);
					writer.newLine();
					writer.write(password);
					writer.newLine();
					System.out.println("Dati salvati con successo!");
				} // try
				catch (IOException e) {
					e.printStackTrace();
				} // catch
				Utente utente = new Utente(nomeUtente, 100, 0);
				utente.creazione(nomeUtente);
				return utente;
			} // if
			while (s.charAt(0) == 'n') {
				System.out.print("Inserisci nome utente: ");
				nomeUtente = tastiera.nextLine();
				System.out.print("Inserisci password: ");
				password = tastiera.nextLine();

				System.out.print("Sei sicuro delle credenziali?(s o n)");
				s = tastiera.nextLine();
				while (s.charAt(0) != 's' && s.charAt(0) != 'n') {
					System.out.print("Errore, reinserisci scelta!");
					System.out.print("Sei sicuro delle credenziali?(s o n)");
					s = tastiera.nextLine();
				} // while
			} // while
			Utente utente = new Utente(nomeUtente, 100, 0);
			return utente;
		} // if
		return null;
	}
	*/
}
