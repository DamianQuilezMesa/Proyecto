package modelo;

import java.util.Arrays;

public class Profesor {
	private Long idProfesor;
	private Materias[] materias;
	private String dni;

	public Profesor() {
		super();
	}

	public Profesor(Long idProfesor, Materias[] materias, String dni) {
		super();
		this.idProfesor = idProfesor;
		this.materias = materias;
		this.dni = dni;
	}

	public Long getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(Long idProfesor) {
		this.idProfesor = idProfesor;
	}

	public Materias[] getMaterias() {
		return materias;
	}

	public void setMaterias(Materias[] materias) {
		this.materias = materias;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Profesor [idProfesor=" + idProfesor + ", materias=" + Arrays.toString(materias) + ", dni=" + dni + "]";
	}

}
