package com.alura.foro.service;

import com.alura.foro.infra.errores.ValidacionDeIntegridad;
import com.alura.foro.model.*;
import com.alura.foro.repository.AutorRepository;
import com.alura.foro.repository.CursoRepository;
import com.alura.foro.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DatosDetalleTopico registrarTopico(DatosRegistrarTopico datos) {
        var response = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if(response.isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un tópico con esos datos.");
        }

        var fecha = LocalDateTime.now();
        var autor = autorRepository.findById(datos.idAutor());
        if(autor.isEmpty()){
            throw new ValidacionDeIntegridad("El autor no existe");
        }

        var curso = cursoRepository.findById(datos.idCurso());
        if(curso.isEmpty()){
            throw new ValidacionDeIntegridad("El curso es inválido");
        }

        var topico = new Topico(datos.titulo(), datos.mensaje(), fecha, Status.ABIERTO, autor.get(), curso.get());

        topicoRepository.save(topico);

        return new DatosDetalleTopico(topico);
    }

    public List<DatosListadoAllTopicos> getAllTopicos() {
        return topicoRepository.findAll().stream().map(DatosListadoAllTopicos::new).toList();
    }

    public DatosListadoAllTopicos getTopicoById(Long id) {
        var response = topicoRepository.findById(id);
        if(response.isEmpty()){
            throw new ValidacionDeIntegridad("El tópico no existe");
        }

        return new DatosListadoAllTopicos(response.get());
    }

    public DatosDetalleTopico actualizarTopico(Long id, DatosRegistrarTopico datos) {
        var topico = topicoRepository.findById(id);
        if(topico.isEmpty()){
            throw new ValidacionDeIntegridad("El tópico no existe");
        }
        var response = topicoRepository.findByTituloAndMensajeActualizar(id, datos.titulo(), datos.mensaje());
        if(response.isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un tópico con esos datos.");
        }
        var curso = cursoRepository.findById(datos.idCurso());
        if(curso.isEmpty()){
            throw new ValidacionDeIntegridad("El curso es inválido");
        }
        Topico topicoActualizar = topico.get();

        topicoActualizar.actualizarDatos(datos.titulo(), datos.mensaje(), curso.get());

        return new DatosDetalleTopico(topicoActualizar);
    }

    public String eliminarTopico(Long id) {
        var topico = topicoRepository.findById(id);
        if(topico.isEmpty()){
            throw new ValidacionDeIntegridad("El tópico no existe");
        }

        topicoRepository.deleteById(id);
//        Topico topicoEliminar = topico.get();
//
//        topicoEliminar.eliminar();

        return "Tópico eliminado";
    }
}
