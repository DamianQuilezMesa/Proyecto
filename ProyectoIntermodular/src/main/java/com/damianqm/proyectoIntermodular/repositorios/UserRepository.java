package com.damianqm.proyectoIntermodular.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damianqm.proyectoIntermodular.modelo.User;

/**
 * Repositorio Spring Data JPA para la entidad {@link User}.
 * <p>
 * Hereda las operaciones CRUD estándar de {@link JpaRepository} y añade
 * una consulta personalizada para buscar usuarios por su correo electrónico,
 * necesaria para el proceso de autenticación.
 * </p>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * <p>
     * Spring Data JPA genera la consulta {@code SELECT * FROM user WHERE email = ?}
     * automáticamente a partir del nombre del método.
     * </p>
     *
     * @param email el email a buscar
     * @return el usuario cuyo email coincide, o {@code null} si no existe
     */
    User findByEmail(String email);
}
