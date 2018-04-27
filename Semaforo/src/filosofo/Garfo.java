package filosofo;

import java.util.concurrent.Semaphore;

public class Garfo {

	private int numero;
	private boolean estado; // true = ocupado
	private Semaphore sem;
	
	public Garfo(int numero, Semaphore sem) {
		this.numero = numero;		
		this.estado = false;
		this.sem = sem;
	}

	public  void pegar() {
		
		try { 			
			sem.acquire(); 
			this.estado = true;
			
		} catch (InterruptedException e) {}
	}

	public void largar() {
		
		this.estado = false;
		sem.release();
		
	}
	
	
	public boolean getEstado() {
		return estado;
	}
	
	public int getNumero() {
		return numero;
	}


}





