package filosofo;

import java.util.concurrent.Semaphore;

public class Filosofo extends Thread{

	private String nome;

	private Garfo garfoDireito;
	private Garfo garfoEsquerdo;
	private Tijela tijela;

	private Semaphore sem;

	public Filosofo(String nome, Garfo garfoDireito, Garfo garfoEsquerdo, Tijela tijela, Semaphore sem) {
		this.nome = nome;
		this.garfoDireito = garfoDireito;
		this.garfoEsquerdo = garfoEsquerdo;
		this.tijela = tijela;
		this.sem = sem;
	}


	public void comer() {

		while(tijela.getQuantidade() >1) {
			
		
			if (garfoDireito.getEstado() == false) {
				garfoDireito.pegar();
				System.out.println("O filosofo "+nome+ " pegou o garfo "+garfoDireito.getNumero()+"\n");

				if( garfoEsquerdo.getEstado() == false ) {						
					garfoEsquerdo.pegar();
					System.out.println("O filosofo "+nome+ " pegou o garfo "+garfoEsquerdo.getNumero()+"\n");

					try {
						sem.acquire();
						tijela.setQuantidade(tijela.getQuantidade() -5);


						System.out.println("O filosofo "+nome+ " esta comendo!" +
								"\nRestam "+tijela.getQuantidade()+ " porções na tijela.\n");
						
						
						garfoDireito.largar();
						garfoEsquerdo.largar();
						System.out.println("O filosofo "+nome+ " largou o garfo "+garfoDireito.getNumero() +
								"\nO filosofo "+nome+ " largou o garfo "+garfoEsquerdo.getNumero()+"\n");


						sem.release();
						esperar();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				} else {
					garfoDireito.largar();		
					System.out.println("O filosofo "+nome+ " largou o garfo "+garfoDireito.getNumero()+"\n");

					sem.release();
					esperar();
				}


			} else {
				garfoEsquerdo.largar();
				System.out.println("O filosofo "+nome+ " largou o garfo "+garfoDireito.getNumero()+"\n" );

				sem.release();
				esperar();
			}

		}		

		System.out.println("Acabou a comida da tijela!\nO filosofo "+nome+" parou de comer e esta pensando...\n ");

	}

	

	public void esperar() {
		try {
			sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void run() {		
		comer();			
	}

}