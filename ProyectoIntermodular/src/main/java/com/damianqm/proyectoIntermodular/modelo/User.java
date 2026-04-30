package com.damianqm.proyectoIntermodular.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa a un usuario de la aplicación.
 * <p>
 * Se mapea a la tabla {@code user} de la base de datos. El campo
 * {@code email} actúa como identificador único de acceso (se usa como
 * nombre de usuario en el login).
 * </p>
 *
 * <p>Roles disponibles: {@code Admin}, {@code User}.</p>
 * <p>Géneros disponibles: {@code Male}, {@code Female}.</p>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * Identificador único generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    /** Nombre del usuario. */
    private String firstName;

    /** Apellido del usuario. */
    private String lastName;

    /** Fecha de nacimiento del usuario. */
    private LocalDate dob;

    /** Género del usuario ({@code Male} o {@code Female}). */
    private String gender;

    /** Rol del usuario en la aplicación ({@code Admin} o {@code User}). */
    private String role;

    /**
     * Correo electrónico del usuario. Actúa como identificador único de acceso.
     */
    @Column(unique = true)
    private String email;

    /** Contraseña del usuario. */
    private String password;

    /**
     * Devuelve el identificador único del usuario.
     *
     * @return el id del usuario
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param id el nuevo id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del usuario.
     *
     * @return el nombre
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param firstName el nuevo nombre
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Devuelve el apellido del usuario.
     *
     * @return el apellido
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param lastName el nuevo apellido
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Devuelve la fecha de nacimiento del usuario.
     *
     * @return la fecha de nacimiento
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     *
     * @param dob la nueva fecha de nacimiento
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Devuelve el género del usuario.
     *
     * @return {@code "Male"} o {@code "Female"}
     */
    public String getGender() {
        return gender;
    }

    /**
     * Establece el género del usuario.
     *
     * @param gender {@code "Male"} o {@code "Female"}
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Devuelve el rol del usuario.
     *
     * @return {@code "Admin"} o {@code "User"}
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param role {@code "Admin"} o {@code "User"}
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Devuelve el correo electrónico del usuario.
     *
     * @return el email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email el nuevo email (debe ser único en la base de datos)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la contraseña del usuario.
     *
     * @return la contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password la nueva contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Representación textual del usuario para logging y depuración.
     *
     * @return cadena con id, nombre, apellido, fecha de nacimiento y email
     */
    @Override
    public String toString() {
        return "User [id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", dob=" + dob
                + ", email=" + email + "]";
    }
}
