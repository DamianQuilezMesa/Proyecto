package modelo;

public class Empresa {
	
	private String nombre;
	private String localidad;
	private String direccion;
	
	public Empresa() {

	}

	public Empresa(String nombre, String localidad, String direccion) {
		this.nombre = nombre;
		this.localidad = localidad;
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Empresa [nombre=" + nombre + ", localidad=" + localidad + ", direccion=" + direccion + "]";
	}
	
	
	
	

}
