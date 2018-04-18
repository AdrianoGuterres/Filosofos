package filosofo;

public class Garfo {

	private int numero;
	private boolean estado; // true = ocupado
	
	public Garfo(int numero) {
		this.numero = numero;		
		this.estado = false;
	}

	public synchronized void pegar() {
		this.estado = true;
	}

	public synchronized void largar() {

		this.estado = false;
	}
	
	
	public boolean getEstado() {
		return estado;
	}
	
	public int getNumero() {
		return numero;
	}


}





