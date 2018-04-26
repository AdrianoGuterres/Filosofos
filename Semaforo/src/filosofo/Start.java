package filosofo;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Start {
	
	
	

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("\n	              Bem vindo ao jantar dos filosofos!"
				          +"\n	A proposta é simples, N filosofos disputam N garfos para poder comer,"
				          +"\n	o que alternam com a ação de pensar. Para tanto cada um leva 5 segundos comendo "
				          +"\n	e 2 segundos pensando. Para uma nova tentativa é esperado uma pausa entre 0 e 3 segundos!"
				       + "\n\n	Então, primeiro, defina a quantidade de comida na tijela principal: ");
		
		int quantidadeTijela = 0;
		quantidadeTijela = scanner.nextInt();
		
		System.out.println("\n	Agora defina quantos filosofos e garfos participarão do jantar:");
		
		int n = 0;
		n= scanner.nextInt();
		System.out.println("\n	O jantar iniciara em:\n");
		
		Thread t = new Thread() {
			public void run(){				
				for(int i = 5; i>=0; i--) {
					System.out.println("		"+i+ " segundos...\n");	
					try {sleep(1000); } catch (InterruptedException e) {}					
				}				
			}
		};
		
		t.run();
		
		final Tijela tijela = new Tijela(quantidadeTijela);
		
		
		Semaphore [] semArray = new Semaphore[n];
		
		for(int i = 0 ; i < semArray.length; i++) {
			semArray[i] = new Semaphore(1);			
		}
		
		Garfo [] garfoArray = new Garfo[n];
		for(int i = 0 ; i < garfoArray.length; i++) {
			garfoArray[i] = new Garfo(i+1, semArray[i]);			
		}
		
		
		Filosofo [] filoArray = new Filosofo[n];
		
		Semaphore sem = new Semaphore(1);
		
		for(int i = 0; i < filoArray.length; i++) {
			if(i < filoArray.length-1) {
				filoArray[i] = new Filosofo(""+(i+1), garfoArray[i], garfoArray[i+1], tijela, sem);			
			}else {
				filoArray[i] = new Filosofo(""+(i+1), garfoArray[i], garfoArray[0], tijela, sem);	
			}		
		}
		
		
		for(int i = 0; i<filoArray.length; i++) {
			
			filoArray[i].start();		
			System.out.println("O filosofo "+filoArray[i].getNome()+" começou a jantar!");
			
		}
		
		
		
		
		
		
		/*
		
		Semaphore semG1 = new Semaphore(1);
		Semaphore semG2 = new Semaphore(1);
		Semaphore semG3 = new Semaphore(1);
		Semaphore semG4 = new Semaphore(1);
		Semaphore semG5 = new Semaphore(1);

		 Garfo garfo01 = new Garfo(1, semG1);
		 Garfo garfo02 = new Garfo(2, semG2);
		 Garfo garfo03 = new Garfo(3, semG3);
		 Garfo garfo04 = new Garfo(4, semG4);
		 Garfo garfo05 = new Garfo(5, semG5);
		
		Filosofo fil01 = new Filosofo("Marx",       garfo01, garfo02, tijela, sem);
		Filosofo fil02 = new Filosofo("Engels",     garfo02, garfo03, tijela, sem);
		Filosofo fil03 = new Filosofo("Gramsci",    garfo03, garfo04, tijela, sem);
		Filosofo fil04 = new Filosofo("Voltair",    garfo04, garfo05, tijela, sem);
		Filosofo fil05 = new Filosofo("Rousseau",   garfo05, garfo01, tijela, sem);
		
		fil01.start();
		fil02.start();
		fil03.start();
		fil04.start();
		fil05.start();
		
		*/
	}

}
