package filosofo;

import java.util.concurrent.Semaphore;

public class Filosofo extends Thread{

	private String nome;

	private Garfo garfoDireito;
	private Garfo garfoEsquerdo;
	private Tijela tijela;

	private Semaphore sem;
	
	private int totalInicial;

	private long vezesComeu;
	private int vezesEsperou;
	private int vezesEsperouDireito ;
	private int vezesEsperouEsquerdo;

	public Filosofo(String nome, Garfo garfoDireito, Garfo garfoEsquerdo, Tijela tijela, Semaphore sem) {
		this.nome = nome;
		this.garfoDireito = garfoDireito;
		this.garfoEsquerdo = garfoEsquerdo;
		this.tijela = tijela;
		this.sem = sem;
		
		this.vezesComeu = 0;
		this.vezesEsperou = 0;
		this.vezesEsperouDireito = 0;
		this.vezesEsperouEsquerdo = 0;
		
		this.totalInicial = tijela.getQuantidade();

	}


	@Override
	public void run() {		

			while(tijela.getQuantidade() >1) {

				if(garfoDireito.getEstado() == false && garfoEsquerdo.getEstado() == false) {
					garfoDireito.pegar();
					garfoEsquerdo.pegar();

					System.out.println("O filosofo "+nome+ " pegou o garfo "+garfoDireito.getNumero() +
							"\nO filosofo "+nome+ " pegou o garfo "+garfoEsquerdo.getNumero() +"\n" );					
					
					comer();					

					

				}else if(garfoDireito.getEstado() == true && garfoEsquerdo.getEstado() == false) {	
					vezesEsperouDireito ++;
					System.out.println("O filosofo "+nome+ " esta esperando o garfo direito "+garfoDireito.getNumero()+"\n");
					try {sleep(100);} catch (InterruptedException e) {}

				}else if(garfoDireito.getEstado() == false && garfoEsquerdo.getEstado() == true) {
					vezesEsperouEsquerdo ++;
					System.out.println("O filosofo "+nome+ " esta esperando o garfo esquerdo "+garfoEsquerdo.getNumero()+"\n");
					try {sleep(100);} catch (InterruptedException e) {}

				}else if(garfoDireito.getEstado() == true && garfoEsquerdo.getEstado() == true) {
					System.out.println("O filosofo "+nome+ " esta esperando ambos os garfos "+garfoDireito.getNumero()+" e "+garfoEsquerdo.getNumero()+"\n");
					vezesEsperou ++;
					try {sleep(100);} catch (InterruptedException e) {}
				}			

			}	

			if(vezesComeu != 0) {
				double aux = ((double)vezesComeu*100.0)/(double)totalInicial;	
				System.out.println("Acabou a comida da tijela!\nO filosofo "+nome+" parou de comer e esta pensando... e comeu "+vezesComeu+" vezes, cerca de "+ aux  +" %!\n ");
			} else { 
				int aux = vezesEsperou + vezesEsperouDireito + vezesEsperouEsquerdo;
				System.out.println("Acabou a comida da tijela!\nO filosofo "+nome+" Morreu de fome. Esperou "+aux+" vezes!");		
				System.out.println("Esperou "+vezesEsperouDireito+" vezes pelo garfo direito!");	
				System.out.println("Esperou "+vezesEsperouEsquerdo+" vezes pelo garfo esquerdo!");	
				System.out.println("Esperou "+vezesEsperou+" vezes pelos dois garfoso!");	
			}

		
	}			

	private void comer() {		
		try {
			sem.acquire();
			vezesComeu++;
			tijela.setQuantidade(tijela.getQuantidade() -1);
			System.out.println("O filosofo "+nome+ " esta comendo!" +
					"\nRestam "+tijela.getQuantidade()+ " porções na tijela.\n");			
			sleep(5000);
			pensar();

		} catch (InterruptedException e) {System.out.println(e);}		
	}

	private void pensar() {

		garfoDireito.largar();
		garfoEsquerdo.largar();
		System.out.println("O filosofo "+nome+ " largou o garfo direito "+garfoDireito.getNumero() +
				"\nO filosofo "+nome+ " largou o garfo esquerdo "+garfoEsquerdo.getNumero()+
				"\nO filosofo "+nome+ " esta pensando!"+"\n");		
		sem.release();	
		try { sleep(2000); } catch (InterruptedException e) {}

	}

}