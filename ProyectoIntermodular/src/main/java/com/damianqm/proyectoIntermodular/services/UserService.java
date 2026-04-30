package com.damianqm.proyectoIntermodular.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damianqm.proyectoIntermodular.modelo.User;
import com.damianqm.proyectoIntermodular.repositorios.UserRepository;

/**
 * Capa de servicio para la gestión de usuarios.
 * <p>
 * Proporciona las operaciones CRUD sobre la entidad {@link User} y la
 * lógica de autenticación. Actúa como intermediario entre los controladores
 * de la UI y el repositorio de persistencia.
 * </p>
 *
 * @author Damián Quilez Mesa
 * @version 1.0
 * @since 2024
 * @see UserRepository
 * @see User
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Persiste un nuevo usuario en la base de datos.
     *
     * @param entity el usuario a guardar (sin id asignado)
     * @return el usuario guardado con el id generado por la base de datos
     */
    public User save(User entity) {
        return userRepository.save(entity);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param entity el usuario con los datos modificados (debe tener un id válido)
     * @return el usuario actualizado
     */
    public User update(User entity) {
        return userRepository.save(entity);
    }

    /**
     * Elimina un usuario por su entidad.
     *
     * @param entity el usuario a eliminar
     */
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id el id del usuario a eliminar
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Busca y devuelve un usuario por su identificador.
     *
     * @param id el id del usuario
     * @return el usuario encontrado
     * @throws java.util.NoSuchElementException si no existe un usuario con ese id
     */
    public User find(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * Devuelve la lista completa de usuarios registrados en la base de datos.
     *
     * @return lista (nunca {@code null}) de todos los usuarios
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Verifica las credenciales de acceso de un usuario.
     * <p>
     * Busca al usuario por email y compara la contraseña proporcionada
     * con la almacenada en base de datos.
     * </p>
     *
     * @param email    el correo electrónico usado como nombre de usuario
     * @param password la contraseña a comprobar
     * @return {@code true} si las credenciales son correctas; {@code false} en caso contrario
     */
    public boolean authenticate(String email, String password) {
        User user = findByEmail(email);
        if (user == null) {
            return false;
        }
        return password.equals(user.getPassword());
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email el email a buscar
     * @return el usuario encontrado, o {@code null} si no existe
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Elimina en bloque una lista de usuarios de la base de datos.
     *
     * @param users la lista de usuarios a eliminar
     */
    public void deleteInBatch(List<User> users) {
        userRepository.deleteAll(users);
    }
}
