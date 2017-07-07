package com.allisson.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allisson.portal.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long>{

}
