package com.alura.foro.model;

import java.time.LocalDateTime;

public record DatosDetalleTopico (
    Long idTopico,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    String nombreAutor,
    String nombreCurso,
    String categoria,
    Status status
){
    public DatosDetalleTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor().getNombre(), topico.getCurso().getNombre(), topico.getCurso().getCategoria(), topico.getStatus());
    }
}
