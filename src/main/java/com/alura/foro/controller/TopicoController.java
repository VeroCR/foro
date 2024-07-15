package com.alura.foro.controller;

import com.alura.foro.model.DatosDetalleTopico;
import com.alura.foro.model.DatosListadoAllTopicos;
import com.alura.foro.model.DatosRegistrarTopico;
import com.alura.foro.model.Topico;
import com.alura.foro.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder){
        var response = service.registrarTopico(datos);
//        return ResponseEntity.ok(response);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.idTopico()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DatosListadoAllTopicos>> getAllTopicos(){
        var response = service.getAllTopicos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoAllTopicos> getTopicoById(@PathVariable Long id){
        var response = service.getTopicoById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosRegistrarTopico datos){
        var response = service.actualizarTopico(id, datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        var response = service.eliminarTopico(id);
        return ResponseEntity.ok(response);
    }

}
