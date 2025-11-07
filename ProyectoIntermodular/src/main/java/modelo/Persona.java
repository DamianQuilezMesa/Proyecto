package modelo;

public class Persona {
	private Credenciales credenciales;
	private String dni;
	
	public Persona() {
	}
	
	public Persona(Credenciales credenciales, String dni) {
		this.credenciales = credenciales;
		this.dni = dni;
	}

	public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Persona [credenciales=" + credenciales + ", dni=" + dni + "]";
	}
	
	
	

}
