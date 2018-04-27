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
				       + "\n\n	Então, primeiro, defina a quantidade de comida na tigela principal: ");
		
		int quantidadeTigela = 0;
		quantidadeTigela = scanner.nextInt();
		
		System.out.println("\n	Agora defina quantos filosofos e garfos participarão do jantar:");		
		int n = 0;
		n= scanner.nextInt();
		
		scanner.close();
		
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
		
		final Tigela tigela = new Tigela(quantidadeTigela);
		
		
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
				filoArray[i] = new Filosofo(garfoArray[i], garfoArray[i+1], tigela, sem);			
			}else {
				filoArray[i] = new Filosofo(garfoArray[i], garfoArray[0], tigela, sem);	
			}		
		}
		
		
		for(int i = 0; i<filoArray.length; i++) {
			
			filoArray[i].start();		
			System.out.println("O filosofo "+filoArray[i].getName()+" começou a jantar!\n");
			
		}
		
	}

}
