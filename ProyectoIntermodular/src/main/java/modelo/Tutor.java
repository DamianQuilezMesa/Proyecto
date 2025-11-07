package modelo;

public class Tutor {
	private Long idTutor;
	private String dni;
	private Empresa empresa;

	public Tutor() {
		super();
	}

	public Tutor(Long idTutor, String dni, Empresa empresa) {
		super();
		this.idTutor = idTutor;
		this.dni = dni;
		this.empresa = empresa;
	}

	public Long getIdTutor() {
		return idTutor;
	}

	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Tutor [idTutor=" + idTutor + ", dni=" + dni + ", empresa=" + empresa + "]";
	}

}
