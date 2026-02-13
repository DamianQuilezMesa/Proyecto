package com.damianqm.proyectoIntermodular.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damianqm.proyectoIntermodular.modelo.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
