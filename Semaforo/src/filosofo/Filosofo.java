package filosofo;

import java.util.concurrent.Semaphore;

public class Filosofo extends Thread{

	private String nome;

	private Garfo garfoDireito;
	private Garfo garfoEsquerdo;
	private Tijela tijela;

	private Semaphore sem;
	
	private long vezes;

	public Filosofo(String nome, Garfo garfoDireito, Garfo garfoEsquerdo, Tijela tijela, Semaphore sem) {
		this.nome = nome;
		this.garfoDireito = garfoDireito;
		this.garfoEsquerdo = garfoEsquerdo;
		this.tijela = tijela;
		this.sem = sem;
		this.vezes = 0;
	}


	@Override
	public void run() {		
		while(tijela.getQuantidade() >1) {


			if(garfoDireito.getEstado() == false && garfoEsquerdo.getEstado() == false) {
				garfoDireito.pegar();
				garfoEsquerdo.pegar();


				try {
					sem.acquire();
					
					System.out.println("O filosofo "+nome+ " pegou o garfo "+garfoDireito.getNumero() +
							"\nO filosofo "+nome+ " pegou o garfo "+garfoEsquerdo.getNumero() +
							"\nO filosofo "+nome+ " esta comendo!" +
							"\nRestam "+tijela.getQuantidade()+ " porções na tijela.\n");
					
					tijela.setQuantidade(tijela.getQuantidade() -1);
					
					vezes++;

					garfoDireito.largar();
					garfoEsquerdo.largar();
					System.out.println("O filosofo "+nome+ " largou o garfo direito "+garfoDireito.getNumero() +
							"\nO filosofo "+nome+ " largou o garfo esquerdo "+garfoEsquerdo.getNumero()+"\n");		

					sem.release();
					sleep(500);

				} catch (InterruptedException e) {}	

			}else if(garfoDireito.getEstado() == true && garfoEsquerdo.getEstado() == false) {
			
				
				System.out.println("O filosofo "+nome+ " esta esperando o garfo direito "+garfoDireito.getNumero()+"\n");	
				try { sleep(500); } catch (InterruptedException e) {}

			}else if(garfoDireito.getEstado() == false && garfoEsquerdo.getEstado() == true) {
			
				System.out.println("O filosofo "+nome+ " esta esperando o garfo esquerdo "+garfoEsquerdo.getNumero()+"\n");
				try { sleep(500); } catch (InterruptedException e) {}

			}else if(garfoDireito.getEstado() == true && garfoEsquerdo.getEstado() == true) {
				System.out.println("O filosofo "+nome+ " esta esperando ambos os garfos "+garfoDireito.getNumero()+" e "+garfoEsquerdo.getNumero()+"\n");
				try { sleep(500); } catch (InterruptedException e) {}
			}


		}		
		
		double aux = (vezes*100)/tijela.getQuantidade();

		System.out.println("Acabou a comida da tijela!\nO filosofo "+nome+" parou de comer e esta pensando... e comeu "+vezes+" vezes, cerca de "+ aux  +" %!\n ");
	}				

}