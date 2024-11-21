import java.util.Scanner;

public class ClasseBanca {

	public static void menu() {
		System.out.println("--------------MENU--------------");
		System.out.println("1 - Deposita");
		System.out.println("2 - Preleva");
		System.out.println("3 - Visualizza Conto");
		System.out.println("4 - Visualizza Portafoglio");
		System.out.println("5 - Investi");
		System.out.println("6 - Vai al mese successivo");
		System.out.println("0 - Esci");
	}
		
	public static void menuInvestimentiDurata() {
		System.out.println("Scegli la durata del tuo investimento:");
		System.out.println("1 - Breve Durata");
		System.out.println("2 - Media Durata");
		System.out.println("3 - Lunga Durata");
		System.out.println("0 - Indietro");
	}
		
	public static void menuInvestimentiRischio() {
		System.out.println("Inserisci il rischio/guadagno del tuo investimento");
		System.out.println("1 - Basso Rischio, Basso Guadagno");
		System.out.println("2 - Medio Rischio, Medio Guadagno");
		System.out.println("3 - Alto Rischio, Alto Guadagno");
		System.out.println("0 - Indietro");
	}
		
	public static void main(String[] args) {
		Scanner tastiera = new Scanner(System.in);
		String scelta;
		
		System.out.print("Inserisci scelta:");
		scelta = tastiera.nextLine();

	}
}
