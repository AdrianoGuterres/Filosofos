package filosofo;

public class Garfo {

	private int numero;
	private boolean estado; // true = ocupado
	
	public Garfo(int numero) {
		this.numero = numero;		
		this.estado = false;
	}

	public  void pegar() {
		this.estado = true;
	}

	public void largar() {

		this.estado = false;
	}
	
	
	public boolean getEstado() {
		return estado;
	}
	
	public int getNumero() {
		return numero;
	}


}





