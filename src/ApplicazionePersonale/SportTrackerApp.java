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
	    			
	    			System.out.println("Inserisci la descrizione dell'allenamento,altrimenti lasciare vuoto");
	    			String descrizione=scan.nextLine();
	    			
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
	    			
	    			RunningWorkout corsa=new RunningWorkout(descrizione,id,data,durata,distanza);
	    			manager.aggiungiWorkout(corsa);
	    			System.out.println("✓ Corsa aggiunta con successo!\n");
	    			break;
	    		
	    			//gestisco allenmaneto di forza(esercizi palestra o corpo libero)
	    		case 2:

	    		    scan.nextLine();

	    		    System.out.print("Inserisci l'id con cui memorizzare l'allenamento: ");
	    		    String id1 = scan.nextLine();
	    		    
	    		    System.out.println("Inserisci la descrizione dell'allenamento,altrimenti lasciare vuoto");
	    		    String descrizione1=scan.nextLine();

	    		    DateTimeFormatter formatoData1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    		    LocalDate data1;

	    		    while(true) {

	    		        System.out.print("Inserisci data (gg/mm/aaaa): ");

	    		        try {

	    		            data1 = LocalDate.parse(scan.nextLine(), formatoData1);

	    		            break;

	    		        } catch(DateTimeParseException e) {

	    		            System.out.println("Formato data non valido. Riprova!");

	    		        }

	    		    }

	    		    System.out.print("Inserisci la durata in minuti: ");

	    		    int durata1 = scan.nextInt();

	    		    scan.nextLine();

	    		    StrengtWorkout forza = new StrengtWorkout(descrizione1,id1, data1, durata1);

	    		    boolean continua = true;

	    		    while(continua) {

	    		        System.out.println("Inserisci nome esercizio");

	    		        String nomeEs = scan.nextLine();

	    		        Exercise esercizio = new Exercise(nomeEs);

	    		        System.out.println("Quante serie dell'esercizio vuoi inserire?");

	    		        int numeroSerie = scan.nextInt();

	    		        System.out.println("Come viene svolto l'esercizio?");

	    		        System.out.println("1) A ripetizioni");

	    		        System.out.println("2) A tempo");

	    		        System.out.println("3) A ripetizioni con tempo");

	    		        int sceltaTipo = scan.nextInt();

	    		        TipoSerie tipoSerie;

	    		        switch(sceltaTipo) {

	    		            case 1:

	    		                tipoSerie = TipoSerie.RIPETIZIONI;

	    		                break;

	    		            case 2:

	    		                tipoSerie = TipoSerie.TEMPO;

	    		                break;

	    		            case 3:

	    		                tipoSerie = TipoSerie.RIPETIZIONI_CON_TEMPO;

	    		                break;

	    		            default:

	    		                System.out.println("Scelta non valida.");

	    		                continue;
	    		        }

	    		        for(int i = 1; i <= numeroSerie; i++) {

	    		            System.out.println("Serie " + i);

	    		            int ripetizioni;
	    		            int durataSecondi;

	    		            if(tipoSerie == TipoSerie.RIPETIZIONI) {

	    		                System.out.print("Numero di ripetizioni: ");

	    		                ripetizioni = scan.nextInt();

	    		                durataSecondi = 0;

	    		            } else if(tipoSerie == TipoSerie.TEMPO) {

	    		                ripetizioni = 1;

	    		                System.out.print("Durata della serie in secondi: ");

	    		                durataSecondi = scan.nextInt();

	    		            } else {

	    		                System.out.print("Numero di ripetizioni: ");

	    		                ripetizioni = scan.nextInt();

	    		                System.out.print("Durata di ogni ripetizione in secondi: ");

	    		                durataSecondi = scan.nextInt();
	    		            }

	    		            System.out.print("Kg: ");

	    		            double kg = scan.nextDouble();

	    		            SerieWorkout serie = new SerieWorkout(
	    		                ripetizioni,
	    		                durataSecondi,
	    		                kg,
	    		                tipoSerie
	    		            );

	    		            esercizio.aggiungiSerie(serie);
	    		        }

	    		        forza.aggiungiEsercizio(esercizio);

	    		        // Chiedo se voglio aggiungere un altro esercizio
	    		        while(true) {

	    		            System.out.println("Vuoi aggiungere un altro esercizio?");

	    		            System.out.println("1) Si");

	    		            System.out.println("2) No");

	    		            int aggiungi = scan.nextInt();

	    		            scan.nextLine();

	    		            if(aggiungi == 1) {

	    		                break;

	    		            } else if(aggiungi == 2) {

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
	    	
	    		case 3: //case 3 dell'inseirmento esercizio
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
	    	            		
	    	            		boolean continuaStatsForza=true;
	    	            		
	    	            		while(continuaStatsForza) {
	    	            		System.out.println("===== STATISTICHE FORZA =====");
	    		    	        System.out.println("1. Statistiche generali");
	    		    	        System.out.println("2. Statistiche su un determinato esercizio");
	    		    	        System.out.println("3. Torna indietro\n");
	    		    	        
	    		    	        int sceltaStatsForza=scan.nextInt();
	    		    	        
	    		    	        switch(sceltaStatsForza) {
	    		    	        

	    		    	        case 1: //case 1 stats forza
	    		    	        	System.out.println("=====STATISTICHE GENERALI=====");
	    		    	        	System.out.println("Hai effettutato un numero di "+ manager.getAllenamentiTotali()+" allenamenti");
	    		    	        	System.out.printf("Ti sei allenato per un totale di: %d minuti, che corrispondono a %.2f ore%n", manager.getTempoTotaleForza(),(double) manager.getTempoTotaleForza() / 60+"\n");
	    		    	        	System.out.println("Hai svolto un totale di "+ manager.getNumeroEserciziForza()+ " esercizi");
	    		    	        	System.out.println("L'esercizio con più ripetizioni è: "+manager.getEsercizioPiuRep()+" con "+ manager.getMaxRipetizioni()+ " ripetizioni" );
	    		    	        	
	    		    	        	break;
	    		    	        
	    		    	        case 2: //case 2 stats forza
	    		    	        	scan.nextLine();
	    		    	        	boolean continuaACercare=true;
	    		    	        	while(continuaACercare) {
	    		    	        	System.out.println("=====STATISTICHE PER SINGOLO ESERCIZIO=====");
	    		    	        	System.out.println("Inserisci il nome dell'esercizio di cui vuoi vedere le statistiche");
	    		    	        	String nomeEsercizioUtente=scan.nextLine();
	    		    	        	if(manager.getNumeroSerieEsercizio(nomeEsercizioUtente)==0) {
	    		    	        		System.out.println("Non hai mai svolto questo esercizio o hai digitato male il nome,riprova");
	    		    	        	}else {
	    		    	        		
	    		    	        		System.out.println("Esercizio: "+ nomeEsercizioUtente);
	    		    	        		System.out.println("Numero di serie totali: " +manager.getNumeroSerieEsercizio(nomeEsercizioUtente));
	    		    	        		System.out.println("Numero di ripetizioni totali: " + manager.getNumeroRipetizioniEsercizio(nomeEsercizioUtente));
	    		    	        		
	    		    	        	}
	    		    	        	boolean sceltaValidaSiNo=false;
	    		    	        	while(!sceltaValidaSiNo) {
	    		    	        	System.out.println("Voui cercare un nuovo esercizio");
	    		    	        	System.out.println("1) Si");
	    		    	        	System.out.println("2) No");
	    		    	        	
	    		    	        	int sceltaUtenteCercaAncora=scan.nextInt();
	    		    	        	
	    		    	        	if(sceltaUtenteCercaAncora==1) {
	    		    	        		sceltaValidaSiNo=true;
	    		    	        		 scan.nextLine();
	    		    	        	}else if(sceltaUtenteCercaAncora==2) {
	    		    	        		continuaACercare = false;
	    		    	        		sceltaValidaSiNo=true;
	    		    	        	}else {
	    		    	        		System.out.println("Scelta non valida");
	    		    	        	   }
	    		    	        	  }
	    		    	        	 }
	    		    	        	
	    		    	        	break;
	    		    	        	
	    		    	        case 3: // case 3 di statistiche forza
	    		    	        	break;
	    		    	        	
	    		    	        default:
	    		    	        	System.out.println("Scelta non valida");
	    		    	          }
	    	            		}
	    		    	        
	    	            	}
	    	                break;

	    	            case 3: //case 3 inserimento esercizip
	    	                continuaStatistiche = false;
	    	                break;

	    	            default:
	    	                System.out.println("Scelta non valida.");
	    	        }
	    	    }
	    	    
	    		break;
	    	
	    		//vedi allenamenti
	    	case 3:
	    	
	    		if(manager.getAllenamentiTotali()==0) {
	    			System.out.println("Non sono presenti allenamenti");
	    		}else {
	    			
	    			for(Workout workout:manager.getWorkouts()) {
	    				System.out.println("=====ALLENAMENTO=====");
	    				if(workout.getDescrizione().isEmpty()) {
	    				System.out.println("Descrizione: non inserita");
	    				}
	    				else {
	    					System.out.println("Descrizione: "+ workout.getDescrizione());
	    				}
	    				System.out.println("Id: "+ workout.getId());
	    				System.out.println("Data: " +workout.getData());
	    				System.out.println("Durata: "+ workout.getDurata()+ " min");
	    				
	    				if(workout instanceof RunningWorkout) {
	    					System.out.println("KM Percorsi: " +((RunningWorkout)workout).getDistanza());
	    					System.out.println("Passo medio: "+((RunningWorkout)workout).getPassoMedio() );
	    				}else if(workout instanceof StrengtWorkout) {
	    					
	    					for(Exercise es:((StrengtWorkout)workout).getEsercizi()) {
	    						System.out.println("Esercizio: "+ es.getNome());
	    						
	    						for(SerieWorkout serie:es.getSerie()) {
	    							System.out.println(serie);
	    						}
	    					}
	    					
	    				}
	    			}
	    			
	    			
	    		}
	    		
	    		break;
	    		//esci dall'app
	    	case 4: 
	    		
	    		scan.nextLine();
	    		System.out.println("=====USCITA=====");
	    		System.out.println("Sei sicuro di voler chiudere l'applicazione?");
	    		System.out.println("1) No, continua a usare l'applicazione");
	    		System.out.println("2) Si, chiudi l'applicazione");
	    		
	    		int sceltaChiusura=scan.nextInt();
	    		
	    		while(sceltaChiusura != 1 && sceltaChiusura != 2) {
	    		    System.out.println("Scelta non valida, inserisci 1 oppure 2:");
	    		    sceltaChiusura = scan.nextInt();
	    		}

	    		if(sceltaChiusura == 1) {
	    		    scelta = 0;
	    		} else {
	    		    scelta = 4;
	    		}
	    		break;
	    		
	    	default:
	    		System.out.println("Scelta non valida, riprovare perfavore\n");
	    }
	    
	}	
   }
}
