package com.alura.foro.repository;

import com.alura.foro.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    @Query("""
              SELECT t FROM topico t
              WHERE t.titulo= :titulo
              AND t.mensaje= :mensaje
            """)
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    @Query("""
              SELECT t FROM topico t
              WHERE t.titulo= :titulo
              AND t.mensaje= :mensaje
              AND t.id != :id
            """)
    Optional<Topico> findByTituloAndMensajeActualizar(Long id, String titulo, String mensaje);
}
