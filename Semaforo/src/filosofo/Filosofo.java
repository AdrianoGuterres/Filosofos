package filosofo;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Filosofo extends Thread{

	private Garfo garfoDireito;
	private Garfo garfoEsquerdo;
	private Tigela tigela;

	private Semaphore sem;

	private int totalInicial;

	private long vezesComeu;
	private int vezesEsperou;
	private int vezesEsperouDireito ;
	private int vezesEsperouEsquerdo;

	public Filosofo(Garfo garfoDireito, Garfo garfoEsquerdo, Tigela tigela, Semaphore sem) {		
		this.garfoDireito = garfoDireito;
		this.garfoEsquerdo = garfoEsquerdo;
		this.tigela = tigela;
		this.sem = sem;

		this.vezesComeu = 0;
		this.vezesEsperou = 0;
		this.vezesEsperouDireito = 0;
		this.vezesEsperouEsquerdo = 0;

		this.totalInicial = tigela.getQuantidade();

	}


	@Override
	public void run() {		
		Random ran = new Random();

		while(tigela.getQuantidade() >0) {
			try {
				sem.acquire();

				if((garfoDireito.getEstado() == false) && (garfoEsquerdo.getEstado() == false) && tigela.getQuantidade() >0) {
					garfoDireito.pegar();
					garfoEsquerdo.pegar();

					System.out.println("O filosofo "+getName()+ " pegou o garfo "+garfoDireito.getNumero() +
							"\nO filosofo "+getName()+ " pegou o garfo "+garfoEsquerdo.getNumero() +"\n" );					


					vezesComeu++;
					tigela.setQuantidade(tigela.getQuantidade() -1);
					System.out.println("O filosofo "+getName()+ " esta comendo!" +
							"\nRestam "+tigela.getQuantidade()+ " porções na tigela.\n");			
					sleep(2000);

					garfoDireito.largar();
					garfoEsquerdo.largar();
					System.out.println("O filosofo "+getName()+ " largou o garfo direito "+garfoDireito.getNumero() +
							"\nO filosofo "+getName()+ " largou o garfo esquerdo "+garfoEsquerdo.getNumero()+
							"\nO filosofo "+getName()+ " esta pensando!"+"\n");		
					sem.release();	
					try { sleep(5000); } catch (InterruptedException e) {}


				}else if(garfoDireito.getEstado() == true && garfoEsquerdo.getEstado() == false && tigela.getQuantidade() >0) {	
					System.out.println("O filosofo "+getName()+ " esta esperando o garfo direito "+garfoDireito.getNumero()+"\n");	
					garfoEsquerdo.largar();
					vezesEsperouDireito ++;					
					sem.release();
					try {sleep(ran.nextInt(3000));} catch (InterruptedException e) {}

				}else if(garfoDireito.getEstado() == false && garfoEsquerdo.getEstado() == true && tigela.getQuantidade() >0) {
					System.out.println("O filosofo "+getName()+ " esta esperando o garfo esquerdo "+garfoEsquerdo.getNumero()+"\n");
					garfoDireito.largar();
					vezesEsperouEsquerdo ++;					
					sem.release();
					try {sleep(ran.nextInt(3000));} catch (InterruptedException e) {}

				}else if(tigela.getQuantidade()>0){
					System.out.println("O filosofo "+getName()+ " esta esperando ambos os garfos "+garfoDireito.getNumero()+" e "+garfoEsquerdo.getNumero()+"\n");
					vezesEsperou ++;
					sem.release();										
					try {sleep(ran.nextInt(3000));} catch (InterruptedException e) {}
				}		
				
				sem.release();
			} catch (InterruptedException e) {System.out.println(e);}	


		}	



		if(vezesComeu != 0) {
			double aux = ((double)vezesComeu*100.0)/(double)totalInicial;	
			System.out.println("Acabou a comida da tigela!");
			System.out.println("O filosofo "+getName()+" parou de comer e esta pensando... e comeu "+vezesComeu+" vezes, cerca de "+ aux  +" %!");
			System.out.println("Esperou "+(vezesEsperou + vezesEsperouDireito + vezesEsperouEsquerdo)+" vezes!");		
			System.out.println("Esperou "+vezesEsperouDireito+" vezes pelo garfo direito!");	
			System.out.println("Esperou "+vezesEsperouEsquerdo+" vezes pelo garfo esquerdo!");	
			System.out.println("Esperou "+vezesEsperou+" vezes pelos dois garfoso!\n");
			
		} else { 
			int aux = vezesEsperou + vezesEsperouDireito + vezesEsperouEsquerdo;
			System.out.println("Acabou a comida da tigela! O filosofo "+getName()+" Morreu de fome! Esperou "+aux+" vezes!");		
			System.out.println("Esperou "+vezesEsperouDireito+" vezes pelo garfo direito!");	
			System.out.println("Esperou "+vezesEsperouEsquerdo+" vezes pelo garfo esquerdo!");	
			System.out.println("Esperou "+vezesEsperou+" vezes pelos dois garfoso!\n");	
		}



	}			

}