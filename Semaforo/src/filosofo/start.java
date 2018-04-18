package filosofo;

import java.util.concurrent.Semaphore;

public class start {
	

	public static void main(String[] args) {
		
		final Tijela tijela = new Tijela(50);
		
		Semaphore sem = new Semaphore(2);

		Garfo garfo01 = new Garfo(01);
		Garfo garfo02 = new Garfo(02);
		Garfo garfo03 = new Garfo(03);
		Garfo garfo04 = new Garfo(04);
		Garfo garfo05 = new Garfo(05);
		
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
