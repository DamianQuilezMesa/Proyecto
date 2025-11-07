package modelo;

public class Sesion {

	private Perfiles perfil;
	private Credenciales credenciales;

	public Sesion() {
	}

	public Sesion(Perfiles perfil, Credenciales credenciales) {
		this.perfil = perfil;
		this.credenciales = credenciales;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}

	public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}

	@Override
	public String toString() {
		return "Sesion [perfil=" + perfil + ", credenciales=" + credenciales + "]";
	}

	
}
