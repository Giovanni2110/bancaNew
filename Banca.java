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

public static int convertiInt(String sceltaString) {
int sceltaInt=-1;

	try{
		sceltaInt = Integer.parseInt(sceltaString);
	}catch(NumberFormatException e) {
		System.out.println("Scelta non valida");
		return sceltaInt;
	}
return sceltaInt;
}

public static double convertiDouble(String importoString) {
double importoDouble=-1;

	try{
		importoDouble = Double.parseDouble(importoString);
	}catch(NumberFormatException e) {
		System.out.println("Non puoi depositare/prelevare questo valore!!!");
		return importoDouble;
	}
return importoDouble;
}

public static double gestisciImporti(double conto, double portafoglio, boolean prelievoBool) {
	Scanner tastiera = new Scanner(System.in);
	
	double importo=0;
	while(importo<=0) {
		System.out.println("\nQuanti soldi vuoi importare?");
		String prelievoString = tastiera.nextLine();
		importo = convertiDouble(prelievoString);
	}
	
		if(prelievoBool && importo>conto) {return -1;}
		
		if(!prelievoBool && importo>portafoglio) {return -1;}
			
	return importo;
}

public static void main(String[] args) {
Scanner tastiera = new Scanner(System.in);

String sceltaString;
int scelta;
double portafoglio=500, conto=0;

do {
	
	menu();
	
do {
	System.out.print("Inserisci scelta:");
	sceltaString = tastiera.nextLine();
	scelta = convertiInt(sceltaString);
}while(scelta<0 || scelta>6);


switch (scelta) {
	case 1:
		double deposito =gestisciImporti(conto, portafoglio, false);
		if(deposito != -1) {	
			conto+=deposito;
			portafoglio-=deposito;
			System.out.println("Prelievo avvenuto con successo\n\n\n");
		} else System.out.println("Prelievo fallito! Fondi insufficienti\n\n\n");
		break;

	case 2:
		double prelievo =gestisciImporti(conto, portafoglio, true);
		if(prelievo != -1) {
			conto-=prelievo;
			portafoglio+=prelievo;
			System.out.println("Prelievo avvenuto con successo\n\n\n");
		} else System.out.println("Prelievo fallito! Fondi insufficienti\n\n\n");
	break;
	
	case 3:System.out.println("\n"+conto+" euro\n\n\n");break;
	
	case 4:System.out.println("\n"+portafoglio+" euro\n\n\n");break;
	
	case 5:break;
	case 6:break;
}
}while(scelta !=0);

}
}

