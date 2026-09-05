package ApplicazionePersonale;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
	    		System.out.println("2) Allenamento di forza");
	    		System.out.println("3) Annulla");
	    		
	    		int tipoWorkout= scan.nextInt();
	    		switch(tipoWorkout) {
	    			
	    		// gestisco l'inserimento della corsa
	    		case 1:
	    			scan.nextLine();
	    			System.out.print("Inserisci l'id con cui memorizzare l'allenamento");
	    			String id=scan.nextLine();
	    			
	    			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    			LocalDate data;
	    			while(true) {
	    			System.out.print("Inserisci data (gg/mm/aaaa): ");
	    			try {
	    			data = LocalDate.parse(scan.nextLine(), formatoData);
	    			break;
	    			}catch(DateTimeParseException e) {
	    				System.out.println("Formato data non valido. Riprova!");
	    			}
	    			}
	    			
	    			System.out.print("Inserisci la durata in minuti");
	    			int durata=scan.nextInt();
	    			
	    			System.out.print("Inserisci i km corsi");
	    			double distanza=scan.nextDouble();
	    			
	    			RunningWorkout corsa=new RunningWorkout(id,data,durata,distanza);
	    			manager.aggiungiWorkout(corsa);
	    			System.out.println("✓ Corsa aggiunta con successo!\n");
	    			break;
	    		
	    			//gestisco allenmaneto di forza(esercizi palestra o corpo libero)
	    		case 2:
	    			
	    			scan.nextLine();
	    			System.out.print("Inserisci l'id con cui memorizzare l'allenamento: ");
	    			String id1=scan.nextLine();
	    			
	    			DateTimeFormatter formatoData1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    			LocalDate data1;
	    			while(true) {
	    			System.out.print("Inserisci data (gg/mm/aaaa): ");
	    			try {
	    			data1 = LocalDate.parse(scan.nextLine(), formatoData1);
	    			break;
	    			}catch(DateTimeParseException e) {
	    				System.out.println("Formato data non valido. Riprova!");
	    			}
	    			}
	    			
	    			System.out.print("Inserisci la durata in minuti: ");
	    			int durata1=scan.nextInt();
	    			scan.nextLine();
	    			
	    			StrengtWorkout forza = new StrengtWorkout(id1, data1, durata1);
	    			boolean continua=true;
	    		
	    			while(continua) {
	    				System.out.println("Inserisci nome esercizio");
	    				String nomeEs= scan.nextLine();
	    				
	    				Exercise esercizio = new Exercise(nomeEs);
	    				System.out.println("Quante serie dell'esercizio vuoi inserire?");
	    				int numeroSerie= scan.nextInt();
	    				
	    				for(int i=1; i<=numeroSerie; i++) {
	    					
	    					System.out.println("Serie "+ i);
	    					
	    					System.out.print("Ripetizioni "+i+": ");
	    					int ripetizioni= scan.nextInt();
	    					
	    					System.out.print("Kg:");
	    					double kg=scan.nextDouble();
	    					
	    					SerieWorkout serie = new SerieWorkout(ripetizioni, kg);
	    					esercizio.aggiungiSerie(serie);
	    				}
	    				//chiedo se voglio aggiungere altro esericizio
	    				
	    				while(true) {
	    					System.out.println("Vuoi aggiungere un altro esercizio?");
	    		            System.out.println("1) Si");
	    		            System.out.println("2) No");

	    		            int aggiungi = scan.nextInt();
	    		            scan.nextLine();
	    		            
	    		            if(aggiungi==1) {
	    		            	break;
	    		            }else if(aggiungi == 2) {

	    		                continua = false;
	    		                break;

	    		            } else {
	    		                System.out.println("Scelta non valida.");
	    		            }
	    				}
	    			}
	    			
	    			manager.aggiungiWorkout(forza);
	    			System.out.println("Allenamento di forza aggiunto con successo!");
	    			
	    			break;
	    			
	    		case 3:
	    			System.out.println("Operazione annullata\n");
	    			break;
	    			
	    		default:
		    		System.out.println("Scelta non valida, riprovare perfavore\n");
	    		}
	    		
	    		break;
	    		
	    		//gestione sstatistiche
	    	case 2:
	    		
	    	    boolean continuaStatistiche = true;

	    	    while(continuaStatistiche) {

	    	        System.out.println();
	    	        System.out.println("===== STATISTICHE =====");
	    	        System.out.println("1. Statistiche corsa");
	    	        System.out.println("2. Statistiche forza");
	    	        System.out.println("3. Torna al menu principale\n");

	    	        int sceltaStatistiche = scan.nextInt();

	    	        switch(sceltaStatistiche) {

	    	        	//stats corsa
	    	            case 1:
	    	            	if(manager.getNumeroCorse()==0) {
	    	            		System.out.println("Attualmente non sono presenti allenamenti di corsa.");
	    	            	}else {
	    	            	System.out.println("Hai effettuato: "+ manager.getNumeroCorse()+ " corse");
	    	            	System.out.println("Hai corso un totale di: "+ manager.getKmTotali()+ " km");
	    	            	System.out.printf("Hai corso un totale di: %d minuti, che corrispondono a %.2f ore%n", manager.getTempoTotaleCorsa(),(double) manager.getTempoTotaleCorsa() / 60+"\n");
	    	            	System.out.println("Il tuo passo medio di corsa è: "+ manager.getPassoMedioTotale()+ " min/km");
	    	            	}
	    	                break;

	    	            // statistiche forza
	    	            case 2:
	    	                
	    	            	if(manager.getNumeroAllenamentiForza()==0) {
	    	            		System.out.println("Non sono presenti allenamenti");
	    	            	}else {
	    	            		
	    	            		System.out.println("===== STATISTICHE FORZA =====");
	    		    	        System.out.println("1. Statistiche generali");
	    		    	        System.out.println("2. Statistiche su un determinato esercizio");
	    		    	        System.out.println("3. Torna indietro\n");
	    	            		
	    	            	}
	    	                break;

	    	            case 3:
	    	                continuaStatistiche = false;
	    	                break;

	    	            default:
	    	                System.out.println("Scelta non valida.");
	    	        }
	    	    }
	    	    
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
