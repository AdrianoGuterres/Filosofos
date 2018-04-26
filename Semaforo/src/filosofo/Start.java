package filosofo;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Start {
	
	
	

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("\n              Bem vindo ao jantar dos filosofos!"
				          +"\nA proposta é simples, N filosofos disputam N garfos para poder comer,"
				          +"\no que alternam com a ação de pensar. Para tanto cada um leva 5 segundos comendo "
				          +"\ne 2 segundos pensando. Para uma nova tentativa é esperado uma pausa entre 0 e 3 segundos!"
				       + "\n\nEntão, primeiro, defina a quantidade de comida na tijela principal: ");
		
		int quantidadeTijela = scanner.nextInt();
		
		System.out.println("\n\nAgora defina quantos filosofos e garfos participarão do jantar:");
		
		int n = scanner.nextInt();
		
		System.out.println("\n			O jantar iniciara em:");
		
		Thread t = new Thread();
		
		for(int i = 5; i>=0; i--) {
			System.out.println("\n		"+i+ " segundos...");	
			try {t.sleep(1000); } catch (InterruptedException e) {}
			
		}
		
		
		
		final Tijela tijela = new Tijela(100);
		
		Semaphore sem = new Semaphore(1);
		
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
	}

}
