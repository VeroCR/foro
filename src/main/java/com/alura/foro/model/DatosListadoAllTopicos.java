package com.alura.foro.model;

import java.time.LocalDateTime;

public record DatosListadoAllTopicos(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String nombreAutor,
        String nombreCurso,
        Status status
){
        public DatosListadoAllTopicos(Topico topico) {
                this(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getAutor().getNombre(), topico.getCurso().getNombre(), topico.getStatus());
        }
}
