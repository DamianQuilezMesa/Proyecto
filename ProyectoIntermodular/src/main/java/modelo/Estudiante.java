package modelo;

public class Estudiante {
	private Long idEstudiante;
	private String centro;
	private int curso;
	private String dni;
	private Ciclos ciclo;

	public Estudiante() {
		super();
	}

	public Estudiante(Long idEstudiante, String centro, int curso, String dni, Ciclos ciclo) {
		super();
		this.idEstudiante = idEstudiante;
		this.centro = centro;
		this.curso = curso;
		this.dni = dni;
		this.ciclo = ciclo;
	}

	public Long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Ciclos getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclos ciclo) {
		this.ciclo = ciclo;
	}

	@Override
	public String toString() {
		return "Estudiante [idEstudiante=" + idEstudiante + ", centro=" + centro + ", curso=" + curso + ", dni=" + dni
				+ ", ciclo=" + ciclo + "]";
	}

}
