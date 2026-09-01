package ApplicazionePersonale;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class SportTrackerApp {
	
	public static void main(String[] args) {
		
	Scanner scan = new Scanner(System.in);
		//creo il WorkoutManager
	
	WorkoutManager manager= new WorkoutManager();
	
		//creo il menu iniziale
	int scelta = 0;
	while (scelta != 4) {
			
	    System.out.println("========================");
	    System.out.println("      SPORT TRACKER");
	    System.out.println("========================");

	    System.out.println("1. Inserisci allenamento");
	    System.out.println("2. Visualizza statistiche");
	    System.out.println("3. Visualizza allenamenti");
	    System.out.println("4. Esci");

	    scelta = scan.nextInt();
	   
	    		//gestisco la scelta di inserimento dell'allenamento 
	    switch(scelta) {
	    	case 1:
	    		
	    		System.out.println("Selezionare l'allenamento da inserire:\n");
	    		System.out.println("1) Running workout");
	    		System.out.println("2) Allenamneto di forza");
	    		System.out.println("3) Annulla");
	    		
	    		int tipoWorkout= scan.nextInt();
	    		switch(tipoWorkout) {
	    			
	    		// gestisco l'inserimento della corsa
	    		case 1:
	    			scan.nextLine();
	    			System.out.print("Inserisci l'id con cui memorizzare l'allenamento");
	    			String id=scan.nextLine();
	    			
	    			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    			System.out.print("Inserisci data (gg/mm/aaaa): ");
	    			LocalDate data = LocalDate.parse(scan.nextLine(), formatoData);
	    			
	    			System.out.print("Inserisci la durata in minuti");
	    			int durata=scan.nextInt();
	    			
	    			System.out.print("Inserisci i km corsi");
	    			double distanza=scan.nextDouble();
	    			
	    			RunningWorkout corsa=new RunningWorkout(id,data,durata,distanza);
	    			manager.aggiungiWorkout(corsa);
	    			System.out.println("✓ Corsa aggiunta con successo!\n");
	    			break;
	    		
	    		case 2:
	    			System.out.println("Hai scelto forza");
	    			break;
	    			
	    		case 3:
	    			System.out.println("Operazione annullata");
	    			break;
	    			
	    		default:
		    		System.out.println("Scelta non valida, riprovare perfavore\n");
	    		}
	    		
	    		break;
	    		
	    	case 2:
	    		//vedi statistichde
	    		break;
	    	
	    	case 3:
	    		// vedi allenamenti
	    		break;
	    	
	    	case 4: 
	    		//esci dal menu
	    		break;
	    		
	    	default:
	    		System.out.println("Scelta non valida, riprovare perfavore\n");
	    }
	    
	}	
   }
}
